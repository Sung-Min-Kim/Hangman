

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

//package InputOutputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//test
public class Paint {
	public Graphics g;
	
	/* Constructor */
	public Paint(Graphics g) {
        this.g = g;
    }
	
	//mouse관련메소
	public void mouseOverHandler(boolean mouseOver) {
		if (mouseOver)
	       g.setColor(Color.white);
	    else
	       g.setColor(new Color(0x00eeeeee));
	}
	
	//버튼 
	public void makeNewgameButton() {
		g.fillRect(50, 60, 100, 30);
	    g.setColor(new Color(0x00aaaaaa));
	    g.drawRect(49, 59, 102, 32);
	    g.setColor(Color.black);
	    g.setFont(new Font("Helvetica", Font.BOLD, 16));
	    g.drawString("new word", 64, 80);
	    g.setFont(new Font("Helvetica", Font.BOLD, 32));
	}
	

	
	
	//행맨 그리는 함수 
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
	
	
	//행맨 애니메이션 
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
	
	
	// 맞춰야 되는 단어 맞추면 보여주는 함수 
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
		
		// 유저가 입력한 단어 보여주는 함수 
		public void showGuessWord(String guessList) {
			g.setColor(Color.black);
		      for(int i=0; i<guessList.length(); i++)
		      {
		         g.drawString(""+guessList.charAt(i),50+i*28,40);
		      }
		}
	
	// 이기면 메시지 보여주기 
	public void showWin(boolean win, boolean gameOver, String hiddenWord) {
		if (win == true)
	      {
	         g.setColor(new Color(0x00009900));
	         g.setColor(Color.CYAN);
	         g.drawString("You Win!",600,50);
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
	
	// 지면 메시지 보여주기 
	public void showLose(int missCount, int maxMisses, boolean gameOver, String hiddenWord) {
		if (missCount == maxMisses)
	      {
	         g.setColor(Color.red);
	         g.drawString("You Lose!",600,50);
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
	
	// 몇번 실수했는지 보여주기 
	public void showMisses(int missCount) {
		g.setColor(Color.black);
	      g.setFont(new Font("Helvetica", Font.BOLD, 16));
	      g.drawString("Misses: "+missCount,155,168);
	}
	
	
}