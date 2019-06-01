import java.awt.Graphics;

public class DrawBody implements Drawing {
	Graphics g;
	public DrawBody(Graphics g) {
		this.g = g;
	}
	
	public void draw() {
		g.drawLine(375,250,375,400);
	}
}
