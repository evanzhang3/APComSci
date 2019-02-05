import java.util.Scanner;

public abstract class Player {
	public Scorecard mScoreCard;
	protected Board mBoard;
	protected Wheel mWheel;
	protected String mName;
	static Scanner sc = new Scanner(System.in);
	public Player(Board board, Wheel wheel, String name) {
		mScoreCard = new Scorecard();
		mBoard = board;					
		mWheel = wheel;
		mName = name; 
	}
	abstract PlayResult play();
	public PlayResult spinWheel() {
		int spinResult = mWheel.spinWheel();
		if(spinResult != 1 && spinResult != 2)
		System.out.println(mName + " could win " + spinResult + " dollers");
		if(spinResult == 1) {
			mScoreCard.resetBalence();
			System.out.println(mName + " just spun bankrupt");
			return PlayResult.BANKRUPT; 
		} else if(spinResult == 2) {
			System.out.println(mName + " just losted their turn");
			return PlayResult.LOSE_TURN; 
		} else {
			System.out.println("What consonant do you want to guess?");
			String userChar = sc.nextLine();
			if(!isVowel(userChar)) {
				if(mBoard.checkBoard(userChar)) {
					mScoreCard.add(spinResult);
					System.out.println(mName +" guess is correct");
					System.out.println(userChar + " is in the phrase");
					System.out.println(mName + " earned " + spinResult + " dollers");
					System.out.println(mBoard.getPhraseString());
					System.out.print("Incorrect Letters: ");
					System.out.print(mBoard.getIncorrectLettersString());
					System.out.println();
					return PlayResult.GAIN_MONEY;
				} else {
					System.out.println(mName + " guess was not in the phrase");
					System.out.println(mName + " did not earn " + spinResult + " dollers");
					System.out.println(mName + " just losted your turn");
					return PlayResult.LOSE_TURN;
				}
			} else {
				System.out.println("Guess a vowel");
			}
		}
		return PlayResult.TRY_AGAIN;
	}
	public static boolean isVowel(String randomChar) {
		if(randomChar.charAt(0) == 'A' || randomChar.charAt(0) == 'E' || randomChar.charAt(0) == 'I' || randomChar.charAt(0) == 'O' || randomChar.charAt(0) == 'U') {
			return true;
		}
		return false; 
	}
	public BuyVowelResult buyVowel(String selectedVowel) {
		if(mScoreCard.buyVowel()) {
			if(mBoard.checkBoard(selectedVowel)) {
				System.out.println(selectedVowel + " is in the phrase");
//				System.out.println("Chicken");
				return BuyVowelResult.IN_PHRASE;
			} else {
				System.out.println("Vowel not in phrase");
				return BuyVowelResult.NOT_IN_PHRASE;
			}
		} else {
			System.out.println( mName + " do not have enough money");
		}
		return BuyVowelResult.NOT_ENOUGH_MONEY; 
	}
	public boolean completeSentence(String guessedPhrase) {
		if(mBoard.getAnswerKeyString().equalsIgnoreCase(guessedPhrase)) {
			System.out.println("The phrase was " + mBoard.getAnswerKeyString());
			return true;
		} else {
			return false; 
		}
	}
	public String getName() {
		return mName; 
	}
	public int getBalence() {
		return mScoreCard.getBalence();
	}
	
}
