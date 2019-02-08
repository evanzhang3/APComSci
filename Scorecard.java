
public class Scorecard {
	private static final int purchaseCost = 250;
	private int balence;
	private int finalBalence;
	//constructor 
	public Scorecard () {
		balence = 0; 
		finalBalence = 0; 
	}
	//adds a specifiec doller amount into a players balence 
	public void add(int a) {
		balence += a;
	}
	//checks if a player can buy a vowel 
	public boolean buyVowel() {
		if(canPurchase()) {
			balence -= 250;
			return true;
		}
		return false;
	}
	//returns the players balence
	public int getBalence() {
		return balence;
	}
	//resets the players balence back to zero 
	public void resetBalence() {
		balence = 0; 
	}
	//adds teh players balence from the specific round to their game balence 
	public void addFinalBalence() {
		finalBalence += balence; 
	}
	//checks if the player has enough money to purchase a vowel 
	public boolean canPurchase() {
		if(balence - purchaseCost >= 0) {
			return true;
		} 
		return false; 
	}
	//returns the players final balence 
	public int getFinalBalence() {
		return finalBalence;
	}
}
