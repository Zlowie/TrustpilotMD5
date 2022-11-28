import java.awt.*;
import java.awt.geom.*;



public class Cloud {
	
	public Cloud() {
		
	}
	
	public void drawCloud(Graphics2D g2d) {
		Ellipse2D.Double e = new Ellipse2D.Double(200,75,100,100);
		Ellipse2D.Double e2 = new Ellipse2D.Double(280,75,100,100);
		Ellipse2D.Double e3 = new Ellipse2D.Double(220,50,80,80);
		Ellipse2D.Double e4 = new Ellipse2D.Double(280,60,50,50);
		Ellipse2D.Double e5 = new Ellipse2D.Double(235,90,100,100);
		g2d.setColor(Color.BLUE);
		g2d.fill(e);
		g2d.fill(e2);
		g2d.fill(e3);
		g2d.fill(e4);
		g2d.fill(e5);
		
	}

}
