

public class Level2 extends Level{
public String hiddenWord = "";
	
	public String getHiddenWord() {
	     // 나올 수 있는 문제들 리스트
	      String[] wordList = {
	          "design pattern",
	          "strategy method",
	          "I wanna go home"
	            };
	            // 하나를 고르고 lowercase로 변환해준다.
	      hiddenWord = wordList[(int)(Math.random()*(wordList.length))];
	      hiddenWord = hiddenWord.toLowerCase();    
	      return hiddenWord;
	   }
}