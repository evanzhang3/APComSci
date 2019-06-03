import java.util.*;
public class ComputerPlayer extends Player{
	private int mComputerLevel;
	private double mComputerGuess;
	private String randomChar; 
	private String randomVowel; 
	static Random rand = new Random();
	//constructor 
	public ComputerPlayer(Board board, Wheel wheel, int computerLevel, String name) {
		super(board, wheel, name);	
		mComputerLevel = computerLevel;
		//determines at what percent can the computer guess the phrase (based on how many characters are guessed correctly) 
		if(computerLevel == 1) {
			mComputerGuess = 0.9;
		} else if(computerLevel == 2) {
			mComputerGuess = 0.75;
		} else {
			mComputerGuess = 0.2; 
		}
	}
	//different levels of computer players differ in when they can guess the phrase
	//based on how much of the phrase is already guessed
	//when passed a ceratin percentage the computer player will guess the phrase when it comes their turn
	public PlayResult play() {
		//pre: takes in nothing
		//post: returns if the computer player either losted their turn guessed the phrase, or rolled bankrupt 
		//this method runs till the computer player either guessed the phrase, rolled bankrupt or losted their turn
		while(true) {
			System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers this round");
			System.out.println();
			if((double)mBoard.getNumberOfGuessedChars() / mBoard.getAnswerKeySize() < mComputerGuess) {
				//if it is under a certain percentage of not guessed characters left in the phrase
				if(mBoard.getVowelsSize() < 5 && mScoreCard.getBalence() >= 250) {
					//if the computer player had enough money to buy a vowel and that there are still vowels left to guess
					//the computer in this instance can either guess a charactor or a vowel 
					int randomAction = rand.nextInt(10);
					//chooses to either guess a charactor or a vowel 
					//based on chance 
					if(randomAction < 5) {
						System.out.println(mName + " is guessing a character"); // tells user 
						System.out.println();
						int spinResult = mWheel.spinWheel(); // spins the wheel 
						if(spinResult == 1) {
							//if the wheel spun bankrupt 
							mScoreCard.resetBalence();
							System.out.println(mName + " is bankrupt");
							System.out.println();
							return PlayResult.BANKRUPT; 
						} else if(spinResult == 2) {
							//if the wheel spun bankrupt 
							System.out.println(mName + " just losted their turn");
							System.out.println();
							return PlayResult.LOSE_TURN; 
						} else {
							//if the computer spun a doller amount 
							while(true) {
								//checks if the character is already guessed 
								int remainingCharsSize = mBoard.getRemainingChars().size();
								int randomCharIndex = rand.nextInt(remainingCharsSize);
								randomChar = mBoard.getRemainingChars().get(randomCharIndex).toString();
								if(mBoard.getIncorrectLetters().indexOf(randomChar.charAt(0)) == -1) {
									break;
								}
							}
							//checks the board if the guessed char is in the phrase 
							//also checks if the charcter is not a vowel 
							if(!isVowel(randomChar)) {
								if(mBoard.checkBoard(randomChar)) {
									//if charactor is in the phrase 
									mScoreCard.add(spinResult);
									System.out.println(mName + " just guessed the letter " + randomChar);
									System.out.println();
									System.out.println(mBoard.getPhraseString());
								} else {
									//if charactor is not in the phrase 
									System.out.println(mName + " just guessed the letter " + randomChar);
									System.out.println();
									System.out.println("It was not in the phrase");
									System.out.println();
									System.out.println(mName + " just losed turn");
									return PlayResult.LOSE_TURN;
								}
							}
						}
					} else {
						//if computer is guessing a vowel 
						System.out.println(mName + " is guessing a vowel");
						while(true) {
							//checks if vowel is already guessed 
							int remainingVowelSize = mBoard.getRemaingVowels().size();
							int randomVowelIndex = rand.nextInt(remainingVowelSize);
							randomVowel = mBoard.getRemainingChars().get(randomVowelIndex).toString();
							if(mBoard.getIncorrectLetters().indexOf(randomVowel.charAt(0)) == -1) {
								break;
							}
						}
						//checks if the character is a vowel and subtracts the money it takes to buy a vowel 
						if(mScoreCard.buyVowel() && isVowel(randomVowel)) {
							if(!mBoard.checkBoard(randomVowel)) {
								//if the vowel is not in the phrase 
								System.out.println(mName + " just guessed the letter " + randomVowel);
								System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers");
								System.out.println(mName + " lose turn since they did not guess the correct vowel");
								System.out.println();
								return PlayResult.LOSE_TURN;
							} else {
								//if the vowel is in the phrase 
								System.out.println(mName + " just guessed the letter " + randomVowel);
								System.out.println("It is in the phrase");
								System.out.println(mBoard.getPhraseString());
								System.out.println();
							}
						}
					}
				} else {
					//if the computer only can spin the wheel
					//only occurs when the computer does not have the cash to buy a vowel 
					System.out.println(mName + " is guessing a character");
					int spinResult = mWheel.spinWheel();
					if(spinResult == 1) {
						//if wheel returns bankrupt 
						mScoreCard.resetBalence();
						System.out.println(mName + " is bankrupt");
						System.out.println();
						return PlayResult.BANKRUPT; 
					} else if(spinResult == 2) {
						//if wheel returns lose turn 
						System.out.println(mName + " just losted their turn");
						System.out.println();
						return PlayResult.LOSE_TURN; 
					} else {
						//if wheel returns a doller amount 
						while(true) {
							int remainingCharsSize = mBoard.getRemainingChars().size();
							int randomCharIndex = rand.nextInt(remainingCharsSize);
							String randomChar = mBoard.getRemainingChars().get(randomCharIndex).toString();
							if(mBoard.getIncorrectLetters().indexOf(randomChar.charAt(0)) == -1) {
								break;
							}
						}
						//checks if it is not a vowel 
						if(!isVowel(randomChar)) {
							//if it is in the phrase 
							if(mBoard.checkBoard(randomChar)) {
								System.out.println(mName + " just guessed the letter " + randomChar);
								System.out.println("It is in the phrase");
								System.out.println(mBoard.getPhraseString());
								System.out.println();
								mScoreCard.add(spinResult);
							} else {
								//if it is not in the phrase 
								System.out.println(mName + " just guessed the letter " + randomChar);
								System.out.println(randomChar + " is not in the phrase");
								System.out.println(mName + " just losted their turn");
								System.out.println();
								return PlayResult.LOSE_TURN;
							}
						}
					}
				}
			} else {
				//if the computer guessed a phrase 
				System.out.println(mName + " guessed the phrase");
				String answerToBeGuessed = mBoard.getAnswerKeyString();
				//checks if the guess was correct 
				if(completeSentence(answerToBeGuessed)) {
					return PlayResult.COMPLETE_SENTENCE;
				}
			}
		}
	}
}
