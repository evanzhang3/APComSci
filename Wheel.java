import java.util.ArrayList;
import java.util.Random;

public class Wheel {
	private ArrayList<Integer> mWheelValue;
	private int valueToGet;
	public Wheel() {
		mWheelValue = new ArrayList<Integer>();
		makeWheel();
	}
	Random rand = new Random();
	private void makeWheel() {
		mWheelValue.add(1);
		mWheelValue.add(2);
		for(int i = 100; i <= 1000; i += 50) {
			mWheelValue.add(i);
		}
	}
	public int spinWheel() {
		valueToGet = rand.nextInt(21);
//		if(valueToGet == 1) {
//		} else if(valueToGet == 2) {
//		} else {
//		}
		return mWheelValue.get(valueToGet);
	}
	public ArrayList<Integer> getWheelValues() {
		return mWheelValue; 
	}
	public int getSpinValue() {
		return valueToGet;
	}
}
