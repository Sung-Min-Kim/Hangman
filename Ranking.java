import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;

public class Ranking {
	private static volatile Ranking rank = new Ranking();
	public Graphics g;
	private static final TreeMap<Float, String> map = new TreeMap<>();

	public int userCount = 0;
	public String userName;
	
	public Ranking() {
		
	}
	
	public static Ranking getRankingObject() {
		return rank;
	}
	
	public void setGraphic(Graphics g) {
		this.g = g;
	} 
	
	public void showRanking(boolean win, int missCount, int maxMisses, int userScore) {
		if ((win == true) || (missCount == maxMisses))
	      {
	         g.setColor(Color.BLACK);
	         g.setFont(new Font("Helvetica", Font.BOLD, 32));
	         g.drawString("Ranking",600,200);
	         
	         // 점수와 사용자의 이름을 받아서 랭킹에 저장해야됨 
	  	      try{
	              //파일 객체 생성
	              File file = new File("/Users/sungminkim/git/Hangman/Ranking.txt");
	              int count = 0;
	              String str;
	              
	              //입력 스트림 생성
	              FileReader filereader = new FileReader(file);
	              //입력 버퍼 생성
	              BufferedReader bufReader = new BufferedReader(filereader);
	              String line = "";
	              while((line = bufReader.readLine()) != null){
	            	  g.setColor(Color.BLACK);
	                  g.drawString(line, 520, 300 + count);
	            	  count += 50;
	              }
	              //.readLine()은 끝에 개행문자를 읽지 않는다.            
	              bufReader.close();
	          }catch (FileNotFoundException e) {
	              // TODO: handle exception
	          }catch(IOException e){
	              System.out.println(e);
	          }
	  	      
	  	      System.out.println("User Score: " + userScore);
	      }
	}
	
	//guessWord = JOptionPane.showInputDialog(null, "Guess the entire word:");
	public void enterUserName(boolean win, float userScore) {
		if(win == true) {
			userName = JOptionPane.showInputDialog(null, "Enter User Name:");
			map.put(userScore, userName);
		}
	}
	
	public void showMap() {
		Set<Entry<Float, String>> entries = map.entrySet();
//        map = {1=one, 2=two, A=a, B=b, a=A, b=B, 가=ㄱ, 나=ㄴ}
        System.out.println("map = " + map);
		System.out.println("Start!");
        for(Map.Entry<Float, String> tempEntry: entries){
            System.out.println(tempEntry.getValue() + " = " + tempEntry.getKey());
        }
        System.out.println("End!");
        try {
            ////////////////////////////////////////////////////////////////
        	System.out.println("File is about to be made");
            BufferedWriter out = new BufferedWriter(new FileWriter("out.txt",true));
            String s = entries.toString();

            out.write(s);

            out.close();
            ////////////////////////////////////////////////////////////////
          } catch (IOException e) {
              System.err.println(e); // 에러가 있다면 메시지 출력
              System.exit(1);
          }

	}
}