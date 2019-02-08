import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {

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
		//make scanner wheel class and board class
		Scanner sc = new Scanner(System.in);
		Wheel wheel = new Wheel();
		Board board = new Board("./src/PhraseBank");
		System.out.println("Welcome To Wheel Of Fortune!!!");
		
		//ask for the number of players 
		while(true) {
			System.out.println("How many human players are there? Note: Max of three players");
			numberOfHumanPlayers = sc.nextInt();
			sc.nextLine();
			System.out.println("How many computer players are there? Note: Max of three players");
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
			System.out.print("\nEnter the name of human player number " + Integer.toString(i+1) + ": ");
			String humanName = sc.nextLine();
			players[indexCounter] = new HumanPlayer(board, wheel, humanName);
			indexCounter++; 
		}

		//creates computer players puts into array 
		for(int i = 0; i < numberOfComputerPlayers; i++) {

			System.out.print("\nEnter the name of computer player number " + Integer.toString(i+1) + ": ");
			String computerName = sc.nextLine();
			System.out.println();
			while(true) {
				System.out.print("Enter the skill level of computer player number " + 
						Integer.toString(i+1) + " (1:beginner  2:average  3:master): ");
				computerSkill = sc.nextInt();
				sc.nextLine(); 
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
		System.out.println("Start of game");
		board.generateSecretSentence();
		System.out.println(board.getAnswerKeyString()); // debug
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
					//if they guess the phrase 
					board.generateSecretSentence();
					if(numberOfHumanPlayers + numberOfComputerPlayers == 2) {
						if(players[0].getBalence() < players[1].getBalence()) {
							players[1].updateRoundAdd();
							players[0].updateRoundZero();
						} else {
							players[0].updateRoundAdd();
							players[1].updateRoundZero();
						}
					} else if(numberOfHumanPlayers + numberOfComputerPlayers == 3) {
						if(players[0].getBalence() > players[1].getBalence() && players[0].getBalence() > players[2].getBalence()) {
							players[0].updateRoundAdd();
							players[1].updateRoundZero();
							players[2].updateRoundZero();
						} else if(players[1].getBalence() > players[0].getBalence() && players[1].getBalence() > players[2].getBalence()) {
							players[1].updateRoundAdd();
							players[0].updateRoundZero();
							players[2].updateRoundZero();
						} else {
							
							players[2].updateRoundAdd();
							players[1].updateRoundZero();
							players[0].updateRoundZero();
						}
					}
					break;
				}
				// checks if the last person is at the end of the array and if so rotates to the first player in the array 
				if(playerRotation > players.length - 1) {
					playerRotation = 0; 
				}
			}
			// goes to next round 
			System.out.println("Next Round!");
			roundCounter++;
		}
		//determines who wins after thr number of rounds based on the amount of money they have  
		if(numberOfHumanPlayers + numberOfComputerPlayers == 2) {
			if(players[0].getBalence() < players[1].getBalence()) {
				System.out.println(players[1].getName() + " is the winner");
				players[1].updateRoundAdd();
				players[0].updateRoundZero();
			} else {
				System.out.println(players[0].getName() + " is the winner");
				players[0].updateRoundAdd();
				players[1].updateRoundZero();
			}
		} else if(numberOfHumanPlayers + numberOfComputerPlayers == 3) {
			if(players[0].getBalence() > players[1].getBalence() && players[0].getBalence() > players[2].getBalence()) {
				System.out.println(players[0].getBalence() + " is the winner");
				players[0].updateRoundAdd();
				players[1].updateRoundZero();
				players[2].updateRoundZero();
			} else if(players[1].getBalence() > players[0].getBalence() && players[1].getBalence() > players[2].getBalence()) {
				System.out.println(players[1].getName() + " is the winner");
				players[1].updateRoundAdd();
				players[0].updateRoundZero();
				players[2].updateRoundZero();
			} else {
				System.out.println(players[2].getName() + " is the winner");
				players[2].updateRoundAdd();
				players[1].updateRoundZero();
				players[0].updateRoundZero();
			}
		}
		System.out.println("End of Program");
	}
}
