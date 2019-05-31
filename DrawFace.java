import java.awt.Color;
import java.awt.Graphics;

public class DrawFace {
	Graphics g;
	public DrawFace(Graphics g) {
		this.g = g;
	}
	
	public void draw() {
		g.drawOval(349,199,51,51);
	}
	
	public void drawWithCurrentColor() {
		g.fillOval(350,200,50,50);
	}
	
	public void changeFaceColor() {
	    int c;
        for (int i=0; i<22000; i++)
        {
           // every thousand to slow down
           c = i/100;
           Color faceHue = new Color(255-c,0,c);
           g.setColor(faceHue);
           // draw the face with the current color
           this.drawWithCurrentColor();
        }
	}
}
