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
        //새로운 단어를 hiddenWord변수로 받는다.
        hiddenWord = getHiddenWord();
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

   // HangMan을 그리는 함수 test
   public void paint(Graphics g)
   {
     // newword버튼 위에 마우스가 올라가 있다면 흰색으로 배경색이 바뀌고
     // 아니라면 회색으로 변하게 한다.
      if (mouseOver)
         g.setColor(Color.white);
      else
         g.setColor(new Color(0x00eeeeee));
      // newword 버튼을 좌표를 지정해줘서 만들어주는 과정
      g.fillRect(50, 60, 100, 30);
      g.setColor(new Color(0x00aaaaaa));
      g.drawRect(49, 59, 102, 32);
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica", Font.BOLD, 16));
      g.drawString("new word", 64, 80);
      g.setFont(new Font("Helvetica", Font.BOLD, 32));

      // 단두대를 그려주는 과정
      g.drawLine(50,550,375,550);
      g.drawLine(150,550,150,150);
      g.drawLine(150,150,375,150);
      g.drawLine(375,150,375,199);

      // hiddenWord를 이쁘게 화면에 나타나게 하기 위해서 단어마다 공간을 주려고
      // hiddenWord의 길이-1의 2배를 해서 각 글자마다 공간을 줘서
      // 각 단어를 '_'로 화면에 표시해주는 부분
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
      // 행맨 그리기
      switch (missCount)
      {
      // missCount가 7이 되었을 때 HangMan이 목을 조르는 애니메이션
      case 7: animateHang(g); break;
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


      // 유저가 추측한 단어들을 화면에 보여주는 부분
      g.setColor(Color.black);
      for(int i=0; i<guessList.length(); i++)
      {
         g.drawString(""+guessList.charAt(i),50+i*28,40);
      }

      // 유저가 전체 단어를 주어진 목숨안에 맞춘경우 맞춘 단어를 보여주고 이겼다는 화면을 보여준다.
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
      // 유저가 전체 단어를 주어진 목숨안에 못맞춘경우 맞춘 단어를 보여주고 졌다는 화면을 보여준다.
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

      // 실수를 몇번 했는지 보여주는 부분
      g.setColor(Color.black);
      g.setFont(new Font("Helvetica", Font.BOLD, 16));
      g.drawString("Misses: "+missCount,155,168);

   }

   public String getHiddenWord() {
     // 나올 수 있는 문제들 리스트
      String[] wordList = {
         "apple",
         // "banana",
         // "grape,",
         // "strawberry",
         // "orange",
         //    "radio",
         // "computer",
         //    "car",
         // "java",
         //    "hello world",
         // "hangman",
         // "oodp",
         //    "design pattern",
         // "strategy method",
         "I wanna go home"
            };
            // 하나를 고르고 lowercase로 변환해준다.
      hiddenWord = wordList[(int)(Math.random()*(wordList.length+1))];
      hiddenWord = hiddenWord.toLowerCase();

      return hiddenWord;
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
            if(!Character.isLetter(keyChar)){
               JOptionPane.showMessageDialog(this, "please only alphabet ('a'-'z')","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
                rightGuess = true;
            }
            if (keyChar == guessList.charAt(i) || keyChar == guessList.toUpperCase().charAt(i)){
               JOptionPane.showMessageDialog(this, "you already pressed the word ","Java Hangman - by OODP B_team", JOptionPane.INFORMATION_MESSAGE);
                rightGuess = true;
            }
         }
         // concatenate the "key pressed" to the list of all chars pressed
         guessList = guessList+keyChar;
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
      repaint();
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

   //HangMan이 자기 목을 잡는 애니메이션이다. 얼굴색이 빨간색에서 파란색, 보라색을 바뀐다.
   public void animateHang(Graphics g)
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
         c = i/1000;
         Color faceHue = new Color(255-c,0,c);
         g.setColor(faceHue);
         // 현재 색깔로 얼굴을 그려라
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

}
