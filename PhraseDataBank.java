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
	private void readFile() {
		try {
			FileReader reader = new FileReader(mFileName);
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;
			if((line = bufferedReader.readLine()) != null) {
				try {
					mLineNumber = Integer.parseInt(line);
					System.out.println(line);
				} catch (NumberFormatException e) {
					System.out.println("First Line of text file is not a number");
				}
			}
			while ((line = bufferedReader.readLine()) != null) {
				mPhrases.add(line);
			}
			mBookSize = mPhrases.size();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String generatePhrase() {
		return mPhrases.get(mLineNumber++ - 1 );
	}
	public void changeStartingLine() {
		try {
			FileOutputStream outputStream = new FileOutputStream(mFileName);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
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
