import java.awt.Graphics;

public class DrawArm implements Drawing{
	Graphics g;
	public DrawArm(Graphics g) {
		this.g = g;
	}
	
	public void draw() {
		g.drawLine(375,270,335,280);
	    g.drawLine(375,270,415,280);
	    g.drawLine(335,280,375,250);
	    g.drawLine(415,280,375,250);
	}
	
	public void eraseLeft() {
		g.drawLine(375,270,370,330);
	}
	
	public void eraseRight() {
		g.drawLine(375,270,380,330);
	}
	
	public void eraseArmLeg() {
		g.drawLine(375,250,375,400);
	}
}
