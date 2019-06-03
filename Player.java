import java.util.Scanner;

public abstract class Player {
	public Scorecard mScoreCard;
	protected Board mBoard;
	protected Wheel mWheel;
	protected String mName;
	static Scanner sc = new Scanner(System.in);
	//constructor 
	public Player(Board board, Wheel wheel, String name) {
		mScoreCard = new Scorecard();
		mBoard = board;					
		mWheel = wheel;
		mName = name; 
	}
	abstract PlayResult play(); //each type of player has their own play method 
	public PlayResult spinWheel() {
		//pre: takes in nothing
		//post: returns if the player spun bankrupt a doller amount or lose their turn 
		int spinResult = mWheel.spinWheel();
		if(spinResult != 1 && spinResult != 2)
			System.out.println(mName + " could win " + spinResult + " dollers");
		System.out.println();
		if(spinResult == 1) {
			//if bankrupt
			mScoreCard.resetBalence();
			System.out.println(mName + " just spun bankrupt");
			System.out.println();
			return PlayResult.BANKRUPT; 
		} else if(spinResult == 2) {
			//if lose thrit turn 
			System.out.println(mName + " just losted their turn");
			System.out.println();
			return PlayResult.LOSE_TURN; 
		} else {
			//if doller amount 
			System.out.println("What consonant do you want to guess?");
			String userChar = sc.nextLine();
			while(true) {
				if(alreadyGuessed(userChar) == false) { // checks if the selected chacter is already guessed 
					if(!isVowel(userChar)) { //checks if it is a vowel or not 
						if(mBoard.checkBoard(userChar)) { // checks the board if selected character is guessed 
							//if it is guessed correct 
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
							//if it was not guessed correctly 
							System.out.println(mName + " guess was not in the phrase");
							System.out.println(mName + " did not earn " + spinResult + " dollers");
							System.out.println(mName + " just losted your turn");
							return PlayResult.LOSE_TURN;
						}
					} else {
						//if user did not guess a consonent 
						System.out.println("Please guess a consonent");
					}
				} else {
					// if charactor is aleady guessed 
					System.out.println(userChar + "has already been guessed");
				}
			}
		}
	}
	public boolean alreadyGuessed(String inputChar) {
		//pre: takes in a string inputChar 
		//post: returns boolean if it was already guessed 
		boolean alreadyGuessed = false;
		//checks the already guessed chars to see if parameter is guessed already 
		for(char a: mBoard.getGuessedChars()) {
			if(a == inputChar.toUpperCase().charAt(0)) {
				alreadyGuessed = true;
				break;
			}
		}
		return alreadyGuessed;
	}

	public static boolean isVowel(String vowel) {
		//pre: takes in string vowel
		//post: returns boolean if or if not parameter is a vowel 
		String randomChar = vowel.toUpperCase(); 
		if(randomChar.charAt(0) == 'A' || randomChar.charAt(0) == 'E' || randomChar.charAt(0) == 'I' || randomChar.charAt(0) == 'O' || randomChar.charAt(0) == 'U') {
			return true;
		}
		return false; 
	}
	public BuyVowelResult buyVowel(String selectedVowel) {
		//pre; takes in a string selected vowel 
		//post: returns if it is in the phrse, not in phrase, or if the player does not have enough money 
		while(true) {
			if(isVowel(selectedVowel)) {//checks if it a vowel or not 
				if(mScoreCard.buyVowel()) { // if they can buy a vowel 
					if(mBoard.checkBoard(selectedVowel)) {
						//if vowel is in the phrase 
						System.out.println(selectedVowel + " is in the phrase");
						System.out.println();
						return BuyVowelResult.IN_PHRASE;
					} else {
						//if vowel is not in the phrase 
						System.out.println(selectedVowel + " is not in phrase");
						return BuyVowelResult.NOT_IN_PHRASE;
					}
				} else {
					//if the player does not have enough money 
					System.out.println( mName + " does not have enough money");
					return BuyVowelResult.NOT_ENOUGH_MONEY; 
				}
			} else {
				//if the player did not enter in a vowel 
				System.out.println("Please enter a vowel");
				selectedVowel = sc.nextLine();
			}
		}
	}
	public boolean completeSentence(String guessedPhrase) {
		//pre: takes in a string guessed phrase 
		//post: returns boolean if the parameter is matching with the phrase
		if(mBoard.getAnswerKeyString().equalsIgnoreCase(guessedPhrase)) {
			System.out.println("The phrase was " + mBoard.getAnswerKeyString());
			System.out.println();
			return true;
		} else {
			return false; 
		}
	}
	//returns player name
	public String getName() {
		return mName; 
	}
	//returns players balence during this round
	public int getBalence() {
		return mScoreCard.getBalence();
	}
	//updates the player balence when a round finishes 
	public void updateRoundAdd() {
		mScoreCard.addFinalBalence();
		mScoreCard.resetBalence();
	}
	//updates the player balence when a round finishes 
	public void updateRoundZero() {
		mScoreCard.resetBalence();
	}
	//returns the balence for the player in the game 
	public int getEndBalence() {
		return mScoreCard.getFinalBalence();
	}

}
