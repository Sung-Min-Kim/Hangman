import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class hangman extends Applet implements MouseListener, MouseMotionListener, KeyListener,ItemListener{
  // guessWord는 유저가 한 번에 단어를 맞췄을 때 넣어주는 String이고1,
  // guessList는 유저가 지금까지 어떤 단어들을 추측했는지 보여주는 String이다.
public static String hiddenWord="", guessWord, guessList;
// 잘못된 단어를 몇 번 했는가 count하는 변수.
public static int missCount;
// 몇 번까지 실수가 허용되는지를 담고있는 변수.
public static int maxMisses;
// mouseOver은 newgame 버튼 위에 있는지 없는지의 정보를 담고있는 변수.
// win과 gameOver는 게임을 이겼는지 졌는지의 정보를 담고있는 변수.
public static boolean win, gameOver, mouseOver = true;
// 게임이 끝났는지 아닌지 확인해서 끝났으면 hanged가 true가 된다.
public static boolean hanged;
// Random하게 나온 단어들과 같은 길이의 배열이다. 유저가 어떤 단어들을 알고있는지의 정보를 담고있다.
public static boolean[] knownChars;
// 배경색깔이 어떤 색인지 담고있는 변수.
public static Color bgColor = new Color(0x00dddddd);
public Choice level = null;
//랭킹에 등록하기 위한 점수를 저장해두는 변수.
public static int userScore;
//새로운 게임이 얼마나 걸렸는지 시간을 재주기 위한 변수 -> 점수계산목적 
public static long startTime = 0, endTime = 0;
//몇 개를 맞췄는지 저장해놓은 변수 -> 점수계산목적
public static int rightCount = 0;
//레벨 별 점수 계산을 위한 변수 
public static int levelChoice;
   //처음에 initialize해주는 메소드 고
   public void init()
   {
       this.addMouseListener(this);
       this.addMouseMotionListener(this);
       this.addKeyListener(this);
      
       // 화면의 크기를 조정하고 배경색을 정한다.
      setSize(1000,625);
      setBackground(bgColor);
      // 유저에게 메시지를 띄워서 정보를 주는 Dialog
      JOptionPane.showMessageDialog(this, "Use your keyboard to guess each letter.", "Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
      JOptionPane.showMessageDialog(this, "Click on the screen to activate. Have fun!", "Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
      // newGame 메소드 실행
      newGame();
   }
   //새로운 게임을 실행하는 메소드
   
   
   public void newGame()
   {	
//	   System.out.println("Start Time Calculated!");
	   startTime = System.currentTimeMillis();
	   //level combobox 만들
	   String[] items = { "level1","level2","level3" };
	    JComboBox cb = new JComboBox(items);
	    final JPanel mainPanel = new JPanel();
	      mainPanel.setPreferredSize(new Dimension(250, 100));
	      mainPanel.add(new JButton(new AbstractAction("Level") {
	          @Override
	          public void actionPerformed(ActionEvent arg0) {
	        	 cb.setVisible(!cb.isVisible());
	             mainPanel.revalidate();
	             mainPanel.repaint();
	          }
	       }));
	       mainPanel.add(cb);

	       JOptionPane.showMessageDialog(null, mainPanel);
	       
	       Level level1 = new Level1();
		   Level level2 = new Level2();
		   Level level3 = new Level3();
		   
		   //level에 맞게 단어 가져오
		   if(cb.getSelectedItem() =="level1"){
			   hiddenWord = level1.getHiddenWord();
			   levelChoice = 1;
		   }	
		   if(cb.getSelectedItem() =="level2"){
			   hiddenWord = level2.getHiddenWord();
			   levelChoice = 2;
		   }	
		   if(cb.getSelectedItem() =="level3"){
			   hiddenWord = level3.getHiddenWord();
			   levelChoice = 3;
		   }		   
	   guessList = "";
       guessWord = "";
       // 새로운 hiddenWord길이 만큼 knownChars도 선언해준다.
       knownChars = new boolean[hiddenWord.length()];
       // knownChars를 초기화해주는과정, if-else문이 의미하는 바는
       // hiddenWord에 space가 포함이 되어 있을 때가 있는데,
       // space가 있는 공간은 추측을 할 필요가 없으므로 그냥 True로 둔다는 의미.        for (int i=0; i<hiddenWord.length(); i++)
      
       // 변수들 초기화
       win = false;
       missCount = 0;
       maxMisses = 7;
       gameOver = false;
       hanged = false;
       
      //level에 맞게 hint를 줌
      //level1 - 첫단어와 마지막 단어 알려줌
      //level2 - 첫단어 알려줌
      //level3 - 안알랴줌
       
		   if(cb.getSelectedItem() =="level1"){
			   level1.setHintStrategy(new showTwoLetter());
			   knownChars = level1.hint(hiddenWord,knownChars);
		   }	 
		
		   if(cb.getSelectedItem() =="level2"){
			   level2.setHintStrategy(new showOneLetter());	
			   knownChars = level2.hint(hiddenWord,knownChars);
		   }	  
   }
   
   // HangMan을 그리는 함수
   public void paint(Graphics g)
   {
	   Paint painter = new Paint(g);
     // newword버튼 위에 마우스가 올라가 있다면 흰색으로 배경색이 바뀌고
     // 아니라면 회색으로 변하게 한다.
	   painter.mouseOverHandler(mouseOver);
	  
      // newword 버튼을 좌표를 지정해줘서 만들어주는 과정
	   painter.makeNewgameButton();
      
	
      // 단두대를 그려주는 과정 -> 싱글톤으로 구현
	   Hanger hanger = new Hanger();
	   hanger = hanger.getHangerObject();
	   hanger.setGraphic(g);
	   hanger.makeHanger();
      
      // hiddenWord를 이쁘게 화면에 나타나게 하기 위해서 단어마다 공간을 주려고
      // hiddenWord의 길이-1의 2배를 해서 각 글자마다 공간을 줘서
      // 각 단어를 '_'로 화면에 표시해주는 부분
	   painter.showHiddenWord(hiddenWord, knownChars);
      // 행맨 그리기
	   painter.drawHangman(missCount, hanged, bgColor);
      // 유저가 추측한 단어들을 화면에 보여주는 부분
	   painter.showGuessWord(guessList);
      // 유저가 전체 단어를 주어진 목숨안에 맞춘경우 맞춘 단어를 보여주고 이겼다는 화면을 보여준다.
	   painter.showWin(win, gameOver, hiddenWord);
      // 유저가 전체 단어를 주어진 목숨안에 못맞춘경우 맞춘 단어를 보여주고 졌다는 화면을 보여준다.
	   painter.showLose(missCount, maxMisses, gameOver, hiddenWord);
      // 실수를 몇번 했는지 보여주는 부분
	   painter.showMisses(missCount);
	   for(int i=0; i<hiddenWord.length(); i++){
		   System.out.println("knowchar:"+knownChars[i]); 
	   }
	   
	   
	   /* 점수계산 Section */
	   //시간별 점수  
	   if(win == true) {
//		   System.out.println("End Time Calculated!");
		   endTime = System.currentTimeMillis();
		   float timeTaken = (float)(endTime - startTime) / 1000;
		   System.out.println("걸린 시간: " + timeTaken + " 초");
		   int second = (int)timeTaken;
		   System.out.println("Second: " + second);
		   
		   switch(second) {
		   		case 0: userScore += 250; break;
		   		case 1: userScore += 200; break;
		   		case 2: userScore += 150; break;
		   		case 3: userScore += 100; break; 
		   		case 4: userScore += 50; break;
		   		default: break;
		   }
	   }
	   
	   //맞춘개수 점수 
	   if(win == true) {
		   rightCount = 7 - missCount; // 맞춘 개수
//		   System.out.println("Right Count: " + rightCount);
		   userScore = rightCount * 100; // 맞춘 개수 * 100점  
	   }
	   
	   //난이도 점수  
	   if(win == true) {
		   switch(levelChoice) {
		   		case 1: userScore += 100; break;
		   		case 2: userScore += 200; break;
		   		case 3: userScore += 300; break;
		   }
	   }
	   
	   
	   
	// 랭킹보여주기 - 싱글톤 적용
	   Ranking rank = new Ranking();
	   rank = rank.getRankingObject();
	   rank.setGraphic(g);
	   rank.showRanking(win, missCount, maxMisses, userScore);	 
	   
	   
	   rank.enterUserName(win, userScore);
	   rank.showMap();
	   
	   
   }

  
   

   
   public void keyPressed( KeyEvent e ) { 
	   enterAlphabet enAl = new enterAlphabet(e);
	   enterWord enWord= new enterWord(e);
	   
       char keyChar = e.getKeyChar();
       if (!gameOver)
        {
         boolean rightGuess = false;
        // Cast the "key pressed" to a character
        if (keyChar != ' ')
        {
        	enAl.guessWord(keyChar,rightGuess);
        }
        else {
        	enWord.guessWord(keyChar,rightGuess);
        }
        repaint();
        }
     }
  

   // 한 번에 문자를 입력했을 때 맞았는지 아닌지 확인해주는 메소드
   public boolean validateGuess(String guess)
   {
      if (guess.equalsIgnoreCase(hiddenWord))
         return true;
      else
         return false;
   }

   public void keyReleased( KeyEvent e ) { }
   public void keyTyped( KeyEvent e ) {}


    public void mouseMoved(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void mousePressed (MouseEvent mouseEvent) {
     if ((mouseEvent.getX() >50 && mouseEvent.getX() <150) && (mouseEvent.getY() >60 && mouseEvent.getY() <90))
      {
         newGame();
         repaint();
      }
     }

     public void mouseClicked (MouseEvent mouseEvent) {
      // otherwise it would repaint needlessly
      boolean mouseToogle = mouseOver;
      if ((mouseEvent.getX()>50 && mouseEvent.getX()<150) && (mouseEvent.getY()>60 && mouseEvent.getY()<90))
      {
         mouseOver = true;
      }
      else
      {
         mouseOver = false;
      }
      // if mouse comes into or exits button, then repaint() with or w/o highlights
      // except: void this is the animation will reoccur as a result of repainting
      if (mouseToogle != mouseOver && !hanged)
         repaint();
   }


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
}