import java.awt.Graphics; 
   
public class showOneLetter implements HintStrategy {
	public Graphics g;
	@Override
	public boolean[] hint(String hiddenWord, boolean[] knownChars) {
		 knownChars = new boolean[hiddenWord.length()];
		  for (int i=0; i<hiddenWord.length(); i++)
	       {
	          if (hiddenWord.charAt(i) == ' ')
	             knownChars[i] = true;
	          else
	             knownChars[i] = false;
	       }
		 knownChars[0]=true;
		 System.out.println("showTwoLetter:"+knownChars[0]); 
		 
//		 knownChars[0]=true;
//		 knownChars[hiddenWord.length()-1]=true;
//		 g.drawString(""+hiddenWord.charAt(0), 0*15+224, 95);
//		 g.drawString(""+hiddenWord.charAt(hiddenWord.length()-1), 0*15+224, 95);
		return knownChars;
	}
}