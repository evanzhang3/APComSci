
public class Scorecard {
	private static final int purchaseCost = 250;
	private int balence;
	public Scorecard () {
		balence = 0; 
	}
	public void add(int a) {
		balence += a;
		System.out.println(balence);
	}
	public boolean buyVowel() {
		if(canPurchase()) {
			balence -= 250;
			return true;
		}
		return false;
	}
	public int getBalence() {
		return balence;
	}
	public void resetBalence() {
		balence = 0; 
	}
	public boolean canPurchase() {
		if(balence - purchaseCost >= 0) {
			return true;
		} 
		return false; 
	}
}
