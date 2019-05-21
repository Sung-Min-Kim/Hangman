import java.awt.*; 
import java.applet.*;
import javax.swing.*;  
import java.awt.event.*;
 
public abstract class Level implements HintStrategy{
	public HintStrategy hintStrategy;
	abstract String getHiddenWord();
	public boolean[] hint(String hiddenWord, boolean[] knownChars){ return hintStrategy.hint(hiddenWord,knownChars); }
	public void setHintStrategy(HintStrategy hintStrategy){
		this.hintStrategy = hintStrategy;
	}
  
	
}
