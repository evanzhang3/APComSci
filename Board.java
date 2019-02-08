import java.util.ArrayList;

public class Board {
	private PhraseDataBank mPhraseBank;
	private ArrayList<Character> mAnswerKey;
	private ArrayList<Character> mPhrase;
	private ArrayList<Character> mIncorrectLetters;
	private ArrayList<Character> mGuessedChars;
	private ArrayList<Character> mVowels;
	private ArrayList<Character> mRemainingChars;
	private ArrayList<Character> mRemainingVowels;
	//creates all the array lists 
	public Board(String fileName) {
		mPhraseBank = new PhraseDataBank(fileName);
		mAnswerKey = new ArrayList<Character>();
		mPhrase = new ArrayList<Character>();
		mIncorrectLetters = new ArrayList<Character>();
		mGuessedChars = new ArrayList<Character>();
		mVowels = new ArrayList<Character>();
		mRemainingChars = new ArrayList<Character>();
		mRemainingVowels = new ArrayList<Character>();
	}
	// generates phrase at the start of every round
	// resets all the array lists 
	//returns the phrase as an array list 
	public ArrayList<Character> generateSecretSentence() {
		mRemainingChars.clear();
		mAnswerKey.clear();
		mPhrase.clear(); 
		mVowels.clear();
		mIncorrectLetters.clear();
		mGuessedChars.clear(); 
		for(int i = 0; i < 26; i++) {
			if('A' + i != 'A' || 'A' + i != 'E' || 'A' + i != 'I' 
					|| 'A' + i != 'O' || 'A' + i != 'U') {
				mRemainingChars.add((char)(65+i));
			}
		}
		mRemainingVowels.clear();
		mRemainingVowels.add('A');
		mRemainingVowels.add('E');
		mRemainingVowels.add('I');
		mRemainingVowels.add('O');
		mRemainingVowels.add('U');
		String str = mPhraseBank.generatePhrase();
		for(char c: str.toCharArray()) {
			mAnswerKey.add(c);
			if(c == ' ') {
				mPhrase.add(c);
			} else {
				mPhrase.add('-'); 
			}
		}
		mPhraseBank.changeStartingLine();
		return mPhrase;
	}
	//checks if a character is already guessed before 
	public boolean alreadyGuessed(String inputChar) {
		boolean alreadyGuessed = false;
		for(char a: mGuessedChars) {
			if(a == inputChar.toUpperCase().charAt(0)) {
				alreadyGuessed = true;
				break;
			}
		}
		return alreadyGuessed;
	}
	//checks the board if a guessed character is in the phrase 
	public boolean checkBoard(String inputChar) {
		if(mAnswerKey.contains(inputChar.toUpperCase().charAt(0))) {
			for(int i = 0; i < mAnswerKey.size(); i++) {
				if(mAnswerKey.get(i) == inputChar.toUpperCase().charAt(0)) {
					mPhrase.set(i, inputChar.toUpperCase().charAt(0));
				}
				if(i == 0) {
					if(inputChar.toUpperCase().charAt(0) == 'A' || inputChar.toUpperCase().charAt(0) == 'E' || inputChar.toUpperCase().charAt(0) == 'I' 
							|| inputChar.toUpperCase().charAt(0) == 'O' || inputChar.toUpperCase().charAt(0) == 'U') {
						mVowels.add(inputChar.toUpperCase().charAt(0));
						int indexOfVowel = mRemainingVowels.indexOf(inputChar.toUpperCase().charAt(0));
						System.out.println(indexOfVowel);
						mRemainingVowels.remove(indexOfVowel);
					}
				}
			}
			int index = mRemainingChars.indexOf(inputChar.toUpperCase().charAt(0));
			mRemainingChars.remove(index);
			mGuessedChars.add(inputChar.toUpperCase().charAt(0));
			return true; 
		} else {
			mIncorrectLetters.add(inputChar.toUpperCase().charAt(0));
			return false;
		}
	}
	//checks if the character is part of the phrase 
	public boolean isGuessedCorrect(String inputString) {
		if(mAnswerKey.toString().equalsIgnoreCase(inputString)) {
			return true;
		} else {
			return false; 
		}
	}
	//returns answer key as a string 
	public String getAnswerKeyString() {
		StringBuffer sb = new StringBuffer();
		for(char c: mAnswerKey) {
			sb.append("" + c);
		}
		return sb.toString(); 
	}
	//returns the phrase as a string 
	public String getPhraseString() {
		StringBuffer sb = new StringBuffer();
		for(char c: mPhrase) {
			sb.append("" + c);
		}
		return sb.toString(); 
	}
	//returns the incorrect letters as a string 
	public String getIncorrectLettersString() {
		StringBuffer sb = new StringBuffer();
		for(char c: mIncorrectLetters) {
			sb.append("" + c);
			sb.append(" ");
		}
		return sb.toString(); 
	}
	//returns the number of guessed chars 
	public int getNumberOfGuessedChars() {
		return mGuessedChars.size();
	}
	// returns the size of the answer key as a integer
	public int getAnswerKeySize() {
		return mAnswerKey.size();
	}
	// returns the size of the vowel array list 
	public int getVowelsSize() {
		return mVowels.size();
	}
	// returns the vowel aray list 
	public ArrayList<Character> getVowel() {
		return mVowels;
	}
	//returns the remainingchars array list 
	public ArrayList<Character> getRemainingChars() {
		return mRemainingChars;
	}
	//returns the remaining vowel array list 
	public ArrayList<Character> getRemaingVowels() {
		return mRemainingVowels;
	}
	//returns incorrect letters array list 
	public ArrayList<Character> getIncorrectLetters() {
		return mIncorrectLetters;
	}
	public ArrayList<Character> getGuessedChars() {
		return mGuessedChars;
	}
}
