import java.io.*;
import java.util.ArrayList;

public class PhraseDataBank {
	private ArrayList<String> mPhrases;
	private String mFileName;
	private int mLineNumber;
	private int mBookSize;
	public PhraseDataBank(String fileName) {
		mPhrases = new ArrayList<String>();
		mFileName = fileName;
		mBookSize = 0; 
		readFile();
	}
	//reads in the text file 
	private void readFile() {
		try {
			FileReader reader = new FileReader(mFileName);
			BufferedReader bufferedReader = new BufferedReader(reader);
			// looks at the first line in the text file and goes to that line number (first line of text file is a number)
			String line;
			if((line = bufferedReader.readLine()) != null) {
				try {
					mLineNumber = Integer.parseInt(line);
					System.out.println(line);
				} catch (NumberFormatException e) {
					System.out.println("First Line of text file is not a number");
				}
			}
			//makes the seret phrase the phrase on that line 
			while ((line = bufferedReader.readLine()) != null) {
				mPhrases.add(line);
			}
			mBookSize = mPhrases.size();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//gets the phrase and return the phrase 
	public String generatePhrase() {
		return mPhrases.get(mLineNumber++ - 1 );
	}
	//changes the first number of the txt file
	public void changeStartingLine() {
		try {
			FileOutputStream outputStream = new FileOutputStream(mFileName);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			//if the line number is greater then the number of lines in the txt file. 
			//changes the first number to 1 
			//else goes to the next line
			if(mLineNumber > mBookSize) {
				bufferedWriter.write(Integer.toString(1));
				bufferedWriter.newLine();
			} else {
				bufferedWriter.write(Integer.toString(mLineNumber));
				bufferedWriter.newLine();
			}
			for(int i = 0; i < mPhrases.size(); i++) {
				bufferedWriter.write(mPhrases.get(i));
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void printout() {
		System.out.println(mPhrases);
	}
}
