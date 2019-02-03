import java.util.Scanner;

public class HumanPlayer extends Player {
	static Scanner sc = new Scanner(System.in);
	public HumanPlayer(Board board, Wheel wheel, String name) {
		super(board, wheel, name);
	}
	public PlayResult play() {
		while(true) {
			System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers");
			System.out.println("What action do you want to preform? (Enter Spin Wheel, Buy a Vowel, or Guess a Phrase)");
			String userInput = sc.nextLine();
			if(userInput.equalsIgnoreCase("Spin Wheel")) {
				PlayResult playResult = spinWheel();
				if(playResult != PlayResult.GAIN_MONEY && 
						playResult != PlayResult.TRY_AGAIN) {
					return playResult;
				} 
			} else if(userInput.equalsIgnoreCase("Buy a Vowel")) {
				System.out.println("What vowel do you want to buy?");
				String userVowel = sc.nextLine();
				BuyVowelResult buyVowelResult = buyVowel(userVowel);
				if(buyVowelResult != BuyVowelResult.IN_PHRASE || 
						buyVowelResult != BuyVowelResult.NOT_ENOUGH_MONEY) {
					return PlayResult.LOSE_TURN;
				}
			} else if(userInput.equalsIgnoreCase("Guess a Phrase")){
				System.out.println("What phrase do you want to guess?");
				String userString = sc.nextLine();
				if(completeSentence(userString)) {
					System.out.println(mName + " guessed the phrase");
					return PlayResult.COMPLETE_SENTENCE;
				} else {
					System.out.println(mName +" guess is wrong and you lose your turn");
					return PlayResult.LOSE_TURN;
				}
			} else {
				System.out.println(mName + " choose one of the three options");
			}
		}
	}
}
