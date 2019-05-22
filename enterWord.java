import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;



public class enterWord extends hangman implements enterAnswerStrategy, MouseListener, MouseMotionListener, KeyListener,ItemListener{
	public static KeyEvent e;
	public enterWord(KeyEvent e) {
		super();
		this.e = e;
	}


	@Override
	public void guessWord(char keyChar, boolean rightGuess) {
		// TODO Auto-generated method stub
		guessTheWord(missCount, maxMisses,win,guessWord);
	}
	
	public void guessTheWord(int missCount, int maxMisses,  boolean win,String guessWord) 
	   {
	      guessWord = JOptionPane.showInputDialog(null, "Guess the entire word:");
	      if (validateGuess(guessWord))
	      {
	         win = true;
	      }
	      else
	      {
	         // you lose
	         missCount = maxMisses;
	      }
	   }
}
