import java.awt.Color;
import java.awt.Graphics;

public class AnimationFacade {
	public Graphics g;
	
	public AnimationFacade(Graphics g) {
		this.g = g;
	}
	
	public void animateHang(boolean hanged, Color bgColor)
	   {
	      DrawArm arm = new DrawArm(g);
	      DrawLeg leg = new DrawLeg(g);
	      DrawBody body = new DrawBody(g);
	      DrawFace face = new DrawFace(g);
	      hanged = true;

	      g.setColor(Color.black);
	      // 팔
	      arm.draw();
	      // 다리
	      leg.draw();
	      // 몸통
	      body.draw();
	      // 얼굴
	      face.draw();
	      // 얼굴 애니메이션
	      face.changeFaceColor();
	      g.setColor(bgColor);
	      // 팔
	      arm.draw();
	      // 다리
	      leg.draw();
	      
	      //얼굴 색깔변화  
	      g.setColor(Color.black);
	      arm.eraseArmLeg();
	      arm.eraseLeft();
	      arm.eraseRight();
	      leg.eraseLeft();
	      leg.eraseRight();
	   }
}
