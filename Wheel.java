import java.util.ArrayList;
import java.util.Random;

public class Wheel {
	private ArrayList<Integer> mWheelValue;
	private int valueToGet;
	//constructor 
	public Wheel() {
		mWheelValue = new ArrayList<Integer>();
		makeWheel();
	}
	Random rand = new Random();
	//adds the values into the wheel array list
	private void makeWheel() {
		mWheelValue.add(1);
		mWheelValue.add(2);
		for(int i = 100; i <= 1000; i += 50) {
			mWheelValue.add(i);
		}
	}
	//returns a random index in the wheel array list 
	public int spinWheel() {
		valueToGet = rand.nextInt(21);
		return mWheelValue.get(valueToGet);
	}
	//returns the wheel array list
	public ArrayList<Integer> getWheelValues() {
		return mWheelValue; 
	}
	//returns the most recent value that was spun
	public int getSpinValue() {
		return valueToGet;
	}
}
