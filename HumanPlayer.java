import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player {
	static Scanner sc = new Scanner(System.in);
	public HumanPlayer(Board board, Wheel wheel, String name) {
		super(board, wheel, name);
	}
	public PlayResult play() {
		int actionChosen = 0;
		while(true) {
			System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers this round\n");
			
			while(true) {
				System.out.println("What action do you want to preform? (1: Spin Wheel, 2: Buy a Vowel, 3: Guess a Phrase)");
				try {
					actionChosen = sc.nextInt();
					if (actionChosen!=1 && actionChosen!=2 && actionChosen!=3) {
						System.out.println("Please choose a valid action number between 1 and 3.");
						sc.nextLine();
					} else {
						break;
					}
				} catch(InputMismatchException e) {
					System.out.println("Please choose a valid action number between 1 and 3.");
					sc.nextLine();
				}
			}
			
			if(actionChosen == 1) {
				PlayResult playResult = spinWheel();
				if(playResult != PlayResult.GAIN_MONEY && 
						playResult != PlayResult.TRY_AGAIN) {
					return playResult;
				} 
			} else if(actionChosen == 2) {
				System.out.println();
				System.out.println("What vowel do you want to buy?");
				String userVowel = sc.nextLine();
				BuyVowelResult buyVowelResult = buyVowel(userVowel);
				if(buyVowelResult == BuyVowelResult.NOT_IN_PHRASE || 
						buyVowelResult == BuyVowelResult.NOT_ENOUGH_MONEY) {
					return PlayResult.LOSE_TURN;
				} else {
					System.out.println(mBoard.getPhraseString());
				}
			} else if(actionChosen == 3){
				System.out.println();
				System.out.println("What phrase do you want to guess?");
				String userString = sc.nextLine();
				if(completeSentence(userString)) {
					System.out.println(mName + " guessed the phrase");
					System.out.println();
					return PlayResult.COMPLETE_SENTENCE;
				} else {
					System.out.println(mName +" guess is wrong and you lose your turn");
					System.out.println();
					return PlayResult.LOSE_TURN;
				}
			} else {
				// code should never reach here
				System.out.println("Invalid action: " + actionChosen + " is selected");
			}
		}
	}
}
