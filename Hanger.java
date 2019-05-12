

import java.awt.Graphics;

public class Hanger {
	private static volatile Hanger hanger = new Hanger();
	public Graphics g;
	
	public Hanger() {
		
	}
	
	public static Hanger getHangerObject() {
		return hanger;
	}
	
	public void setGraphic(Graphics g) {
		this.g = g;
	}
	
	public void makeHanger() {
		g.drawLine(50,550,375,550);
	      g.drawLine(150,550,150,150);
	      g.drawLine(150,150,375,150);
	      g.drawLine(375,150,375,199);
	}
	
}
