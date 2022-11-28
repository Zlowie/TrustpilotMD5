import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TrustpilotMD5 extends Thread{
	private static String anagram = "poultry outwits ants";
	private static byte[] anagramChars = new byte[26];
	private static int anagramLength = anagram.length();
	private static ArrayList<String> wordList = new ArrayList<String>();
	private static long startTime;
	
	private static int threadCount;
	private int threadNr;
	
	private static String hash = "e4820b45d2277f3844eac66c903e84be";
	//private static String hash = "23170acc097c24edb98fc5488ab033fe";
	
	private TrustpilotMD5() {
	}
	
	public TrustpilotMD5(int n) {
		startTime = System.nanoTime();
		threadCount = n;
		anagramToArray();
		readWordList();
		
		for (int i=0; i<n ; i++) {
			TrustpilotMD5 tempThread = new TrustpilotMD5();
			tempThread.threadNr = i;
			tempThread.start();
		}
	}
	
	public void run() {
		genrateAnagrams();
	}
	
	public static void anagramToArray() {
		for (int i=0; i<anagram.length() ; i++) {
			char tempChar = anagram.charAt(i);
			if (tempChar >= 'a' && tempChar <= 'z') {
				anagramChars[tempChar-'a']++;
			}

		}
		
	}
	
	public static boolean isAnagram(String s) {
		if (s.length() != anagramLength) return false;
		
		byte[] tempArray = new byte[26];
		
		for (int i=0; i<anagramLength ; i++) {
			char tempChar = s.charAt(i);
			if (tempChar != ' ') {
				tempArray[tempChar-'a']++;
			}
		}	
		
		for (int i=0; i<26 ; i++) {
			if (tempArray[i] != anagramChars[i]) return false;
		}
		return true;
		
	}
	
	public static boolean isAnagram(String s1, String s2) {
		byte[] tempArray1 = new byte[26];
		byte[] tempArray2 = new byte[26];
		int length;


		length = s1.length();
		for (int i=0; i<length ; i++) {
			char tempChar1 = s1.charAt(i);
			if (tempChar1 != ' ') {
				tempArray1[tempChar1-'a']++;
			}
		}
		length = s2.length();
		for (int i=0; i<length ; i++) {
			char tempChar2 = s2.charAt(i);
			if (tempChar2 != ' ') {
				tempArray2[tempChar2-'a']++;
			}
		}
		
		for (int i=0; i<26 ; i++) {
			if (tempArray1[i] != tempArray2[i]) return false;
		}
		return true;
	}
	
	public static boolean lettersAvailable(String s) {
		byte[] tempArray = new byte[26];
		
		int length = s.length();
		for (int i=0; i<length ; i++) {
			char tempChar = s.charAt(i);
			if (tempChar >= 'a' && tempChar <= 'z') {
				tempArray[tempChar-'a']++;
			} else {
				return false;
			}
		}	
		
		for (int i=0; i<26 ; i++) {
			if (tempArray[i] > anagramChars[i]) return false;
		}
		return true;
	}
	
	public static void readWordList() {
		try {
			File myFile = new File("C:\\Temp\\wordlist.txt");
			FileReader myFileReader = new FileReader(myFile);
			BufferedReader myReader = new BufferedReader(myFileReader);
			
			String word;
			String lastWord = "";
			
			while((word = myReader.readLine()) != null) {
				if (lettersAvailable(word)) {
					if (!word.equals(lastWord)) {
						if(word.length()>1) {
							wordList.add(word);
							lastWord = word;
						}
						
					}
				} 
			}
			wordList.add("a");
			wordList.add("i");
			wordList.add("o");
			
			System.out.println("Words added = " + wordList.size());
			myReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void genrateAnagrams() {
		int length = wordList.size();
		String testPhrase;
		
		for (int i=0+threadNr ; i<length ; i=i+threadCount) {
			for (int j=0 ; j<length ; j++) {
				if (lettersAvailable(wordList.get(i) + wordList.get(j))) {
					for (int k=0 ; k<length ; k++) {
						testPhrase = wordList.get(i) + " " + wordList.get(j) + " " + wordList.get(k);
						if (isAnagram(testPhrase)) {
							//System.out.println(testPhrase);
							if (hash.equals(Hashing.MD5Hash(testPhrase))) {
								System.out.println("BINGO! " + testPhrase);
								System.out.println("Time = "+((System.nanoTime()-startTime)/1000000)+" ms.");
								System.exit(0);
							}
						}
					}
				}	
			}
		}
	}
}
