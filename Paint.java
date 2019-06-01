

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
		
		DrawArm arm = new DrawArm(g);
		DrawBody body =  new DrawBody(g);
		DrawFace face =  new DrawFace(g);
		DrawLeg leg = new DrawLeg(g);		
		
		AnimationFacade animation = new AnimationFacade(g);
		switch (missCount)
	      {
	      // missCount가 7이 되었을 때 HangMan이 목을 조르는 애니메이션
	      case 7: animation.animateHang(hanged, bgColor); break;
	      // 왼쪽 팔
	      case 6: arm.eraseLeft();
	      // 오른쪽 팔
	      case 5: arm.eraseRight();
	      // 왼쪽 다리
	      case 4: leg.eraseLeft();
	      // 오른쪽 다리
	      case 3: leg.eraseRight();
	      // 몸통
	      case 2: body.draw();
	      // 얼굴
	      case 1:face.draw(); 	
	      }
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
//			 hangman.stop_Main_Sound();
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
