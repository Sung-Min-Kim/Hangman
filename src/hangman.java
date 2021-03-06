import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class hangman extends Applet implements MouseListener, MouseMotionListener, KeyListener{
  // guessWord는 유저가 한 번에 단어를 맞췄을 때 넣어주는 String이고,
  // guessList는 유저가 지금까지 어떤 단어들을 추측했는지 보여주는 String이다.
String hiddenWord="", guessWord, guessList;
// 잘못된 단어를 몇 번 했는가 count하는 변수.
int missCount;
// 몇 번까지 실수가 허용되는지를 담고있는 변수.
int maxMisses;
// mouseOver은 newgame 버튼 위에 있는지 없는지의 정보를 담고있는 변수.
// win과 gameOver는 게임을 이겼는지 졌는지의 정보를 담고있는 변수.
boolean win, gameOver, mouseOver = true;
// 게임이 끝났는지 아닌지 확인해서 끝났으면 hanged가 true가 된다.
boolean hanged;
// Random하게 나온 단어들과 같은 길이의 배열이다. 유저가 어떤 단어들을 알고있는지의 정보를 담고있다.
boolean[] knownChars;
// 배경색깔이 어떤 색인지 담고있는 변수.
Color bgColor = new Color(0x00dddddd);

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
	   GetHiddenWord hiddenWordGetter = new GetHiddenWord();
        //새로운 단어를 hiddenWord변수로 받는다.
        hiddenWord = hiddenWordGetter.getHiddenWord();
        guessList = "";
        guessWord = "";
        // 새로운 hiddenWord길이 만큼 knownChars도 선언해준다.
        knownChars = new boolean[hiddenWord.length()];
        // knownChars를 초기화해주는과정, if-else문이 의미하는 바는
        // hiddenWord에 space가 포함이 되어 있을 때가 있는데,
        // space가 있는 공간은 추측을 할 필요가 없으므로 그냥 True로 둔다는 의미.        for (int i=0; i<hiddenWord.length(); i++)
        for (int i=0; i<hiddenWord.length(); i++)
        {
           if (hiddenWord.charAt(i) == ' ')
              knownChars[i] = true;
           else
              knownChars[i] = false;
        }
        // 변수들 초기화
        win = false;
        missCount = 0;
        maxMisses = 7;
        gameOver = false;
        hanged = false;
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
   }

   

   // 한 번에 문자를 입력했을 때 맞았는지 아닌지 확인해주는 메소드
   public boolean validateGuess(String guess)
   {
      if (guess.equalsIgnoreCase(hiddenWord))
         return true;
      else
         return false;
   }


   
   public void keyPressed( KeyEvent e ) { 
       char keyChar = e.getKeyChar();
       if (!gameOver)
        {
        boolean rightGuess = false;
        // Cast the "key pressed" to a character
        if (keyChar != ' ')
        {
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
        else {
        	guessTheWord();
        }
        repaint();
        }
     }
   
   public void guessTheWord() 
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


   public void keyReleased( KeyEvent e ) { }
   public void keyTyped( KeyEvent e ) {}

   // 맞았는지 틀렸는지 확인하는 메소드
   public boolean Checker(Event e, int k)
   {
      if (!gameOver)
      {
      boolean rightGuess = false;

      // 어떤 키보드가 눌려졌는지 저장해둔다.
      char keyChar = (char) k;
      if (keyChar != ' ')
      {
        // 이전에 이미 추측한 단어라면 Checker에서 빠져나간다.
         for(int i=0; i<guessList.length(); i++)
         {
           // 입력한 것이 알파벳이 아니라면 경고메시지
            if(!Character.isLetter(keyChar)){
               JOptionPane.showMessageDialog(this, "please only alphabet ('a'-'z')","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
               return false;
            }
            if (keyChar == guessList.charAt(i) || keyChar == guessList.toUpperCase().charAt(i)){
               JOptionPane.showMessageDialog(this, "you already pressed the word ","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
               return false;
            }
         }
         // 눌러졌던 글자를 추측한 리스트에다가 저장해준다.
         guessList = guessList+keyChar;
         // 키보드에서 눌린 글자가 hiddenWord에 있는 글자라면 Known chars 배열에 known(true)로 바꿔준다.
         for(int i=0; i<hiddenWord.length(); i++)
         {
            if (keyChar == hiddenWord.charAt(i) || keyChar == hiddenWord.toUpperCase().charAt(i))
            {
               rightGuess = true;
               knownChars[i] = true;
            }
         }
         // 일단 승리를 true로 한다음에 loop에서 unknownChar 중에 false가 있다면 win을 false로 바꿔줘서 게임이 끝나지 않게한다.
         win = true;
         for(int i=0; i<hiddenWord.length(); i++)
         {
            if (knownChars[i] == false)
            {
               win = false;
            }
         }
         // 추측한 글자가 맞지 않다면 Miss count를 1증가시킨다.
         if (rightGuess == false)
            missCount++;
      }
      repaint();
      }
      return true;
   }
    public void mouseMoved(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseDragged(MouseEvent e){}
    public void mousePressed (MouseEvent mouseEvent) {
    //  mouseX = mouseEvent.getX();
    //  mouseY = mouseEvent.getY();
     if ((mouseEvent.getX() >50 && mouseEvent.getX() <150) && (mouseEvent.getY() >60 && mouseEvent.getY() <90))
      {
         newGame();
         repaint();
      }
     }
     public void mouseClicked (MouseEvent mouseEvent) {
    //  mouseX = mouseEvent.getX();
    //  mouseY = mouseEvent.getY();
      //  to detect crosses over the boundaries of the button
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

   

}