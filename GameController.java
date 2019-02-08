import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {
	public static final boolean DBG_MODE = false;
	public static final String PhraseBankFile = "./src/PhraseBank";

	public static void main(String[] args) {
		int numberOfHumanPlayers;
		int numberOfComputerPlayers;
		int roundCounter = 0; 
		Player[] players;
		int playerRotation;
		int totalPlayers = 0; 
		int indexCounter = 0; 
		int computerSkill = 0; 
		int numberOfRounds = 0; 
		ArrayList<Integer> winnerList = new ArrayList<Integer>(); // incase there are multiple winners
		//create scanner wheel and board objects
		Scanner sc = new Scanner(System.in);
		Wheel wheel = new Wheel();
		Board board = new Board(PhraseBankFile);
		System.out.println("Welcome To Wheel Of Fortune!!!");

		//ask for the number of players 
		while(true) {
			System.out.println("How many human players are there? (Max of three players): ");
			numberOfHumanPlayers = sc.nextInt();
			sc.nextLine();
			System.out.println("How many computer players are there? (Max of three players): ");
			numberOfComputerPlayers = sc.nextInt();
			sc.nextLine();
			if(numberOfHumanPlayers + numberOfComputerPlayers > 3) {
				System.out.println("You can only have a max of 3 players.");
			} else {
				totalPlayers = numberOfHumanPlayers + numberOfComputerPlayers; 
				players = new Player[totalPlayers];
				break;
			}
		}

		//creates human players puts into array
		for(int i = 0; i < numberOfHumanPlayers; i++) {
			System.out.print("\nEnter the name of human player #" + Integer.toString(i+1) + ": ");
			String humanName = sc.nextLine();
			players[indexCounter] = new HumanPlayer(board, wheel, humanName);
			indexCounter++; 
		}

		//creates computer players puts into array 
		for(int i = 0; i < numberOfComputerPlayers; i++) {

			System.out.print("\nEnter the name of computer player #" + Integer.toString(i+1) + ": ");
			String computerName = sc.nextLine();
			System.out.println();
			while(true) {
				try {
				System.out.print("Enter the skill level of computer player #" + 
						Integer.toString(i+1) + " (1:beginner  2:average  3:master): ");
				computerSkill = sc.nextInt();
				sc.nextLine(); 
				} catch(InputMismatchException e) {
					System.out.println("Please enter 1, 2, or 3");
					sc.nextLine();
					continue;
				}
				if(computerSkill == 1 || computerSkill == 2 || computerSkill ==3) {
					break;
				} else {
					System.out.println("Please enter in a valid input");
				}
			}
			players[indexCounter] = new ComputerPlayer(board, wheel, computerSkill, computerName);
			indexCounter++; 
		}
		while(true) {
			System.out.println("\nHow many rounds do you want to play: ");
			try {
				numberOfRounds = sc.nextInt();	
				break;
			} catch(InputMismatchException e) {
				System.out.print("Please enter valid input");
				sc.nextLine();

			}
		}
		// starts the game 
		System.out.println("\n------------------- Start of game ------------------\n");
		board.generateSecretSentence();
		if (DBG_MODE) {
			System.out.println("Secrete sentence: " + board.getAnswerKeyString());
		}
		//rotates through the different players 
		while(roundCounter < numberOfRounds) {
			playerRotation = 0; 
			for(int i = 0; i < players.length; i++) {
				System.out.println(players[i].getName() + " has " + players[i].getBalence() + " dollers this game");
			}
			while(true) {
				System.out.println(board.getPhraseString());
				System.out.print("Incorrect Letters: ");
				System.out.print(board.getIncorrectLettersString());
				System.out.println();
				PlayResult result = players[playerRotation].play();
				if(result == PlayResult.BANKRUPT || result == PlayResult.LOSE_TURN) {
					//if the player rolls bankrupt or somehow loses their turn 
					//goes to next player 
					playerRotation++;
				} else {
					for(int i = 0; i < players.length; i++) {
						if(i == playerRotation) {
							players[playerRotation].updateRoundAdd();
						} else {
							players[i].updateRoundZero();
						}
					}

					//if they guess the phrase 
					board.generateSecretSentence();
					break;
				}
				// checks if the last person is at the end of the array and if so rotates to the first player in the array 
				if(playerRotation > players.length - 1) {
					playerRotation = 0; 
				}
			}
			// goes to next round 
			roundCounter++;
		}
		//determines who wins after the number of rounds based on the amount of money they have  
		int highestCash = players[0].getEndBalence(); 
		winnerList.add(0);
		for(int i = 1; i < totalPlayers; i++) {
			if(players[i].getEndBalence() > highestCash) {
				winnerList.clear();
				winnerList.add(i);
			} else if(players[i].getEndBalence() == highestCash){
				winnerList.add(i);
			}
		}
		if(winnerList.size() == 1) {
			//single winner 
			System.out.println(players[winnerList.get(0)].getName() + " is the winner");
			System.out.println("With a end balence of " + players[winnerList.get(0)].getEndBalence());
		} else {
			System.out.print("The winners are: ");
			for(int i = 0; i < winnerList.size(); i++) {
				System.out.print(players[winnerList.get(i)].getName() + " ");
			}
			System.out.println("With an end balence of " + players[winnerList.get(0)].getEndBalence());
		}
		System.out.println("End of Program");
	}
}
