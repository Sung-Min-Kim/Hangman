import java.awt.Graphics; 
import java.awt.Color;
public class showTwoLetter implements  HintStrategy{
	public Graphics g;   
	public boolean[] knownChars;
    
	public void setGraphic(Graphics g) {
		this.g = g;
	}
	@Override
	public  boolean[] hint(String hiddenWord, boolean[] knownChars) {
		 knownChars = new boolean[hiddenWord.length()];
		   for (int i=0; i<hiddenWord.length(); i++)
	       {
	          if (hiddenWord.charAt(i) == ' ')
	             knownChars[i] = true;
	          else
	             knownChars[i] = false;
	       }
		 //첫단어 알려줌
		 knownChars[0]=true;
		 //마지막단어 알려줌
		 knownChars[hiddenWord.length()-1]=true;
		 
		 System.out.println("showTwoLetter:"+knownChars[0]); 
		 
//		 knownChars[0]=true;
//		 knownChars[hiddenWord.length()-1]=true;
//		 g.drawString(""+hiddenWord.charAt(0), 0*15+224, 95);
//		 g.drawString(""+hiddenWord.charAt(hiddenWord.length()-1), 0*15+224, 95);
		return knownChars;
	}
		
	}