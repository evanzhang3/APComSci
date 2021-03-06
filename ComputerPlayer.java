import java.util.*;
public class ComputerPlayer extends Player{
	private int mComputerLevel;
	private double mComputerGuess;
	static Random rand = new Random();
	public ComputerPlayer(Board board, Wheel wheel, int computerLevel, String name) {
		super(board, wheel, name);	
		mComputerLevel = computerLevel;
		if(computerLevel == 1) {
			mComputerGuess = 0.9;
		} else if(computerLevel == 2) {
			mComputerGuess = 0.75;
		} else {
			mComputerGuess = 0.4; 
		}
	}
	public PlayResult play() {
		while(true) {
			System.out.println(mName + " has " + mScoreCard.getBalence() + " dollers");
			if(mBoard.getNumberOfGuessedChars() / mBoard.getAnswerKeySize() < mComputerGuess) {
				if(mBoard.getVowelsSize() < 5 && mScoreCard.getBalence() >= 250) {
					int randomAction = rand.nextInt(10);
					if(randomAction < 5) {
						int spinResult = mWheel.spinWheel();
						if(spinResult == 1) {
							mScoreCard.resetBalence();
							System.out.println(mName + " is bankrupt");
							return PlayResult.BANKRUPT; 
						} else if(spinResult == 2) {
							System.out.println(mName + " just losted their turn");
							return PlayResult.LOSE_TURN; 
						} else {
							int remainingCharsSize = mBoard.getRemainingChars().size();
							int randomCharIndex = rand.nextInt(remainingCharsSize);
							String randomChar = mBoard.getRemainingChars().get(randomCharIndex).toString();
							if(!isVowel(randomChar)) {
								if(mBoard.checkBoard(randomChar)) {
									mScoreCard.add(spinResult);
								} else {
									System.out.println(mName + " just guessed the letter");
									return PlayResult.LOSE_TURN;
								}
							}
						}
					} else {
						int remainingVowelSize = mBoard.getRemaingVowels().size();
						int randomVowelIndex = rand.nextInt(remainingVowelSize);
						String randomVowel = mBoard.getRemainingChars().get(randomVowelIndex).toString();
						if(mScoreCard.buyVowel()) {
							if(!mBoard.checkBoard(randomVowel)) {
								return PlayResult.LOSE_TURN;
							}
						}
					}
				} else {
					int randomAction = rand.nextInt(10);
					if(randomAction < 5) {
						int spinResult = mWheel.spinWheel();
						if(spinResult == 1) {
							mScoreCard.resetBalence();
							System.out.println(mName + " is bankrupt");
							return PlayResult.BANKRUPT; 
						} else if(spinResult == 2) {
							System.out.println(mName + " just losted their turn");
							return PlayResult.LOSE_TURN; 
						} else {
							int remainingCharsSize = mBoard.getRemainingChars().size();
							int randomCharIndex = rand.nextInt(remainingCharsSize);
							String randomChar = mBoard.getRemainingChars().get(randomCharIndex).toString();
							if(!isVowel(randomChar)) {
								if(mBoard.checkBoard(randomChar)) {
									System.out.println(mName + " just guessed the letter");
									mScoreCard.add(spinResult);
								} else {
									return PlayResult.LOSE_TURN;
								}
							}
						}
					}
				}
			} else {
				String answerToBeGuessed = mBoard.getAnswerKeyString();
				if(completeSentence(answerToBeGuessed)) {
					return PlayResult.COMPLETE_SENTENCE;
				}
			}
		}
	}
}
