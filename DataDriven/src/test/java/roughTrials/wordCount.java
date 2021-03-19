package roughTrials;

import java.util.StringTokenizer;

public class wordCount {

	public static void main(String[] args) {

		//1st way to count words
		String myString= "I love you!";
		String words[]=myString.split(" ");
		System.out.println(words.length);

		// Other way to count words
		String word="my Name is Mahilet";
		StringTokenizer tokens=new StringTokenizer(word);
		int count= tokens.countTokens();
		System.out.println(count);

		//
	}

}
