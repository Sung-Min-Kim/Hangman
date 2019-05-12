
public class GetHiddenWord {
	public String hiddenWord = "";
	

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
}