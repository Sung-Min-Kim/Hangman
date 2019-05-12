import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//test
public class Paint {
	public Graphics g;
	
	/* Constructor */
	public Paint(Graphics g) {
        this.g = g;
    }
	
	public void mouseOverHandler(boolean mouseOver) {
		if (mouseOver)
	       g.setColor(Color.white);
	    else
	       g.setColor(new Color(0x00eeeeee));
	}
	
	public void makeNewgameButton() {
		g.fillRect(50, 60, 100, 30);
	    g.setColor(new Color(0x00aaaaaa));
	    g.drawRect(49, 59, 102, 32);
	    g.setColor(Color.black);
	    g.setFont(new Font("Helvetica", Font.BOLD, 16));
	    g.drawString("new word", 64, 80);
	    g.setFont(new Font("Helvetica", Font.BOLD, 32));
	}
	
	public void makeHanger() {
		  g.drawLine(50,550,375,550);
	      g.drawLine(150,550,150,150);
	      g.drawLine(150,150,375,150);
	      g.drawLine(375,150,375,199);
	}
	
	public void showHiddenWord(String hiddenWord, boolean[] knownChars) {
		for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
	      {
	         if (i%2 == 0)
	         {
	            if (hiddenWord.charAt(i/2) != ' ')
	               g.drawLine(i*15+225,100,(i+1)*15+225,100);
	            if (knownChars[i/2] == true)
	               g.drawString(""+hiddenWord.charAt(i/2), i*15+224, 95);
	         }
	      }
	}
	
	public void drawHangman(int missCount, boolean hanged, Color bgColor) {
		switch (missCount)
	      {
	      // missCount가 7이 되었을 때 HangMan이 목을 조르는 애니메이션
	      case 7: animateHang(hanged, bgColor); break;
	      // 왼쪽 팔
	      case 6: g.drawLine(375,270,300,280);
	      // 오른쪽 팔
	      case 5: g.drawLine(375,270,450,280);
	      // 왼쪽 다리
	      case 4: g.drawLine(375,400,325,450);
	      // 오른쪽 다리
	      case 3: g.drawLine(375,400,425,450);
	      // 몸통
	      case 2: g.drawLine(375,250,375,400);
	      // 얼굴
	      case 1: {g.drawOval(349,199,51,51); g.setColor(new Color(0x00ffcc99)); g.fillOval(350,200,50,50);}
	      }
	}
	
	
	
	public void animateHang(boolean hanged, Color bgColor)
	   {
	      hanged = true;

	      g.setColor(Color.black);
	      // 팔
	      g.drawLine(375,270,335,280);
	      g.drawLine(375,270,415,280);
	      g.drawLine(335,280,375,250);
	      g.drawLine(415,280,375,250);
	      // 다리
	      g.drawLine(375,400,350,375);
	      g.drawLine(375,400,400,375);
	      g.drawLine(350,375,350,400);
	      g.drawLine(400,375,400,400);
	      // 몸통
	      g.drawLine(375,250,375,400);
	      // 얼굴
	      g.drawOval(349,199,51,51);
	      // 얼굴 애니메이션
	      int c;
	      for (int i=0; i<22000; i++)
	      {
	         // every thousand to slow down
	         c = i/100;
	         Color faceHue = new Color(255-c,0,c);
	         g.setColor(faceHue);
	         // draw the face with the current color
	         g.fillOval(350,200,50,50);
	      }
	      g.setColor(bgColor);
	      // 팔
	      g.drawLine(375,270,335,280);
	      g.drawLine(375,270,415,280);
	      g.drawLine(335,280,375,250);
	      g.drawLine(415,280,375,250);
	      // 다리
	      g.drawLine(375,400,350,375);
	      g.drawLine(375,400,400,375);
	      g.drawLine(350,375,350,400);
	      g.drawLine(400,375,400,400);


	      g.setColor(Color.black);
	      g.drawLine(375,250,375,400);
	      // 팔과 다리를 지운다.
	      // 왼쪽 팔
	      g.drawLine(375,270,370,330);
	      // 오른쪽 팔
	      g.drawLine(375,270,380,330);
	      // 왼다리
	      g.drawLine(375,400,370,460);
	      // 오른다리
	      g.drawLine(375,400,380,460);

	   }
	
	public void showGuessWord(String guessList) {
		g.setColor(Color.black);
	      for(int i=0; i<guessList.length(); i++)
	      {
	         g.drawString(""+guessList.charAt(i),50+i*28,40);
	      }
	}
	
	public void showWin(boolean win, boolean gameOver, String hiddenWord) {
		if (win == true)
	      {
	         g.setColor(new Color(0x00009900));
	         g.drawString("You Win!",600,200);
	         gameOver = true;
	         for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
	         {
	            if (i%2 == 0)
	            {
	               g.drawString(""+hiddenWord.charAt(i/2), i*15+224, 95);
	            }
	         }
	      }
	}
	
	public void showLose(int missCount, int maxMisses, boolean gameOver, String hiddenWord) {
		if (missCount == maxMisses)
	      {
	         g.setColor(Color.red);
	         g.drawString("You Lose!",600,200);
	         gameOver = true;
	         for(int i=0; i<=(hiddenWord.length()-1)*2; i++)
	         {
	            if (i%2 == 0)
	            {
	               g.drawString(""+hiddenWord.charAt(i/2), i*15+224, 95);
	            }
	         }
	      }
	}
	
	public void showMisses(int missCount) {
		g.setColor(Color.black);
	      g.setFont(new Font("Helvetica", Font.BOLD, 16));
	      g.drawString("Misses: "+missCount,155,168);
	}
	
	
}