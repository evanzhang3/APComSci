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
		System.out.println();
		if(spinResult == 1) {
			mScoreCard.resetBalence();
			System.out.println(mName + " just spun bankrupt");
			System.out.println();
			return PlayResult.BANKRUPT; 
		} else if(spinResult == 2) {
			System.out.println(mName + " just losted their turn");
			System.out.println();
			return PlayResult.LOSE_TURN; 
		} else {
			System.out.println("What consonant do you want to guess?");
			String userChar = sc.nextLine();
			while(true) {
				if(alreadyGuessed(userChar) == false) {
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
						System.out.println("Please guess a consonent");
					}
				} else {
					System.out.println(userChar + "has already been guessed");
				}
			}
		}
	}
	public boolean alreadyGuessed(String inputChar) {
		boolean alreadyGuessed = false;
		for(char a: mBoard.getGuessedChars()) {
			if(a == inputChar.toUpperCase().charAt(0)) {
				alreadyGuessed = true;
				break;
			}
		}
		return alreadyGuessed;
	}

	public static boolean isVowel(String vowel) {
		String randomChar = vowel.toUpperCase(); 
		if(randomChar.charAt(0) == 'A' || randomChar.charAt(0) == 'E' || randomChar.charAt(0) == 'I' || randomChar.charAt(0) == 'O' || randomChar.charAt(0) == 'U') {
			return true;
		}
		return false; 
	}
	public BuyVowelResult buyVowel(String selectedVowel) {
		while(true) {
			if(isVowel(selectedVowel)) {
				if(mScoreCard.buyVowel()) {
					if(mBoard.checkBoard(selectedVowel)) {
						System.out.println(selectedVowel + " is in the phrase");
						System.out.println();
						return BuyVowelResult.IN_PHRASE;
					} else {
						System.out.println(selectedVowel + " is not in phrase");
						return BuyVowelResult.NOT_IN_PHRASE;
					}
				} else {
					System.out.println( mName + " does not have enough money");
					return BuyVowelResult.NOT_ENOUGH_MONEY; 
				}
			} else {
				System.out.println("Please enter a vowel");
				selectedVowel = sc.nextLine();
			}
		}
	}
	public boolean completeSentence(String guessedPhrase) {
		if(mBoard.getAnswerKeyString().equalsIgnoreCase(guessedPhrase)) {
			System.out.println("The phrase was " + mBoard.getAnswerKeyString());
			System.out.println();
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
	public void updateRoundAdd() {
		mScoreCard.addFinalBalence();
		mScoreCard.resetBalence();
	}
	public void updateRoundZero() {
		mScoreCard.resetBalence();
	}
	public int getEndBalence() {
		return mScoreCard.getFinalBalence();
	}

}
