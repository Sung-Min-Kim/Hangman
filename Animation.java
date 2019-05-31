import java.awt.Color;
import java.awt.Graphics;

public class Animation {
	public Graphics g;
	
	public Animation(Graphics g) {
		this.g = g;
	}
	
	public void animateHang(boolean hanged, Color bgColor)
	   {
	      hanged = true;

	      g.setColor(Color.black);
	      // 팔
	      DrawArm arm = new DrawArm(g);
	      arm.draw();

	      // 다리
	      DrawLeg leg = new DrawLeg(g);
	      leg.draw();

	      // 몸통
	      DrawBody body = new DrawBody(g);
	      body.draw();
	      // 얼굴
	      DrawFace face = new DrawFace(g);
	      face.draw();
	      // 얼굴 애니메이션
	      face.changeFaceColor();
	      g.setColor(bgColor);
	      // 팔
	      arm.draw();
	      // 다리
	      leg.draw();
	      

	      g.setColor(Color.black);
	      arm.eraseArmLeg();
	      arm.eraseLeft();
	      arm.eraseRight();
	      leg.eraseLeft();
	      leg.eraseRight();
	   }
}
