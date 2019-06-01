import java.awt.Graphics;

public class DrawLeg implements Drawing {
	Graphics g;
	public DrawLeg(Graphics g) {
		this.g = g;
	}
	
	public void draw() {
		g.drawLine(375,400,350,375);
	    g.drawLine(375,400,400,375);
	    g.drawLine(350,375,350,400);
	    g.drawLine(400,375,400,400);
	}
	
	public void eraseLeft() {
		g.drawLine(375,400,370,460);
	}
	
	public void eraseRight() {
		g.drawLine(375,400,380,460);
	}
}
