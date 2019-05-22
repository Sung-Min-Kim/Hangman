import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JOptionPane;


public class enterAlphabet extends hangman implements enterAnswerStrategy,MouseListener, MouseMotionListener, KeyListener,ItemListener {
	public KeyEvent e;
	

	public enterAlphabet(KeyEvent e) {
		super();
		this.e = e;
	}


	@Override
	public void guessWord(char keyChar, boolean rightGuess) {
		// TODO Auto-generated method stub
		// if character has previously been choosen, then get out of this method
        for(int i=0; i<guessList.length(); i++)
        {
           // if character is not alphabet, alert the message
           if(!Character.isAlphabetic(keyChar)){
             JOptionPane.showMessageDialog(this, "please only alphabet ('a'-'z')","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
              rightGuess=true; break;
           }
           if (keyChar == guessList.charAt(i) || keyChar == guessList.toUpperCase().charAt(i)){
              JOptionPane.showMessageDialog(this, "you already pressed the word ","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
              rightGuess=true; break;
           }
        }
        // concatenate the "key pressed" to the list of all chars pressed
        if(Character.isAlphabetic(keyChar)&&(guessList.indexOf(keyChar)<0)){
     	   guessList = guessList+keyChar;
        }
        // is the "key pressed" is one of the chars in the hidden word, then define it as known
        // in the knownChars array
        for(int i=0; i<hiddenWord.length(); i++)
        {
           if (keyChar == hiddenWord.charAt(i) || keyChar == hiddenWord.toUpperCase().charAt(i))
           {
              rightGuess = true;
              knownChars[i] = true;
           }            
        }
        // this loop makes "win = false" if there are any unknown characters
        // otherwise "win = true"
        win = true;
        for(int i=0; i<hiddenWord.length(); i++)
        {
           if (knownChars[i] == false)
           {
              win = false;
           }
        }
        if (rightGuess == false) 
           missCount++;
        
	}

	
}
