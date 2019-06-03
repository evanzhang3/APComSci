import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer extends Player {
	static Scanner sc = new Scanner(System.in);
	//constructor 
	public HumanPlayer(Board board, Wheel wheel, String name) {
		super(board, wheel, name);
	}
	public PlayResult play() {
		//pre: takes in nothing
		//post: returns if the player guessed the phrase, losted their turn or is bankrupt 
		int actionChosen = 0;
		while(true) {
			System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers this round");
			
			while(true) {
				//asks user for what they want to preform and checks for valid input and reprompt if invalid input is given 
				System.out.println("What action do you want to preform? (1: Spin Wheel, 2: Buy a Vowel, 3: Guess a Phrase)");
				try {
					actionChosen = sc.nextInt();
					sc.nextLine(); 
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
			//if human player want to spin the wheel 
			if(actionChosen == 1) {
				PlayResult playResult = spinWheel(); // calls spin wheel method from parent class 
				//checks if the player loses their turn 
				if(playResult != PlayResult.GAIN_MONEY && 
						playResult != PlayResult.TRY_AGAIN) {
					return playResult;
				} 
			} else if(actionChosen == 2) {
				//if human player wants to buy a vowel 
				System.out.println();
				System.out.println("What vowel do you want to buy?");
				String userVowel = sc.nextLine();
				BuyVowelResult buyVowelResult = buyVowel(userVowel); //calls buy vowel method from parent class 
				//checks if the user losted their tirn 
				if(buyVowelResult == BuyVowelResult.NOT_IN_PHRASE || 
						buyVowelResult == BuyVowelResult.NOT_ENOUGH_MONEY) {
					return PlayResult.LOSE_TURN;
				} else {
					//if they guessed the phrase 
					System.out.println(mBoard.getPhraseString());
				}
			} else if(actionChosen == 3){
				//if the human player want to guess the phrase 
				System.out.println();
				System.out.println("What phrase do you want to guess?");
				String userString = sc.nextLine();
				//checks if the guessed phras was correct 
				if(completeSentence(userString)) {
					//if it is correct 
					System.out.println(mName + " guessed the phrase");
					System.out.println();
					return PlayResult.COMPLETE_SENTENCE;
				} else {
					//if it is not correct 
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
