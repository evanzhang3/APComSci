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
	public ArrayList<Character> generateSecretSentence() {
		//pre: Takes in nothing
		//post: returns the phrase to be guessed in an array list 
		// generates phrase at the start of every round
		// resets all the array lists 
		//returns the phrase as an array list 
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
		//converts phrase to dashes 
		for(char c: str.toCharArray()) {
			mAnswerKey.add(c);
			if(c == ' ') {
				mPhrase.add(c);
			} else {
				mPhrase.add('-'); 
			}
		}
		mPhraseBank.changeStartingLine(); // changes the starting line of the phrase bank text file 
		return mPhrase;
	}
	//checks if a character is already guessed before 
	public boolean alreadyGuessed(String inputChar) {
		boolean alreadyGuessed = false;
		for(char a: mGuessedChars) { // checks with correctly guessed chaacters 
			if(a == inputChar.toUpperCase().charAt(0)) {
				alreadyGuessed = true;
				break;
			}
		}
		return alreadyGuessed;
	}
	//checks the board if a guessed character is in the phrase 
	public boolean checkBoard(String inputChar) {
		//pre: takes in a string inputChar
		//post: return boolean if inout char is in the phrase or not 
		if(mAnswerKey.contains(inputChar.toUpperCase().charAt(0))) {// checks if input letter is in the phrase 
			for(int i = 0; i < mAnswerKey.size(); i++) {
				//sets all cases of the letter 
				if(mAnswerKey.get(i) == inputChar.toUpperCase().charAt(0)) {
					mPhrase.set(i, inputChar.toUpperCase().charAt(0));
				}
				if(i == 0) {
					//checks if the letter is vowel on the first time going through the phrase 
					if(inputChar.toUpperCase().charAt(0) == 'A' || inputChar.toUpperCase().charAt(0) == 'E' || inputChar.toUpperCase().charAt(0) == 'I' 
							|| inputChar.toUpperCase().charAt(0) == 'O' || inputChar.toUpperCase().charAt(0) == 'U') {
						mVowels.add(inputChar.toUpperCase().charAt(0)); // adds letter to the vowel list 
						int indexOfVowel = mRemainingVowels.indexOf(inputChar.toUpperCase().charAt(0)); 
						mRemainingVowels.remove(indexOfVowel); // removes the letter from the avaliable vowels list 
					}
				}
			}
			int index = mRemainingChars.indexOf(inputChar.toUpperCase().charAt(0));
			mRemainingChars.remove(index); // removes letter from the remaining letters lists 
			mGuessedChars.add(inputChar.toUpperCase().charAt(0));  // add letter to guessed characters 
			return true; 
		} else {
			mIncorrectLetters.add(inputChar.toUpperCase().charAt(0)); // if letter is not in the phrase 
			return false;
		}
	}
	//checks if the character is part of the phrase 
	public boolean isGuessedCorrect(String inputString) {
		if(mAnswerKey.toString().equalsIgnoreCase(inputString)) { // checks parameter with the answer key 
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
