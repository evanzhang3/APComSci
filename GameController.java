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
		Scanner sc = new Scanner(System.in);
		Wheel wheel = new Wheel();
		Board board = new Board("./src/PhraseBank");
		System.out.println("Welcome To Wheel Of Fortune!!!");
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

		//creates human players
		for(int i = 0; i < numberOfHumanPlayers; i++) {
			System.out.print("\nEnter the name of human player number " + i+1 + ": ");
			String humanName = sc.nextLine();
			players[indexCounter] = new HumanPlayer(board, wheel, humanName);
			indexCounter++; 
		}
		
		//creates computer players
		for(int i = 0; i < numberOfComputerPlayers; i++) {

			System.out.print("\nEnter the name of computer player number " + i+1 + ": ");
			String computerName = sc.nextLine();
			System.out.println();
			System.out.print("Enter the skill level of computer player number " + i+1 + " (1:beginner  2:average  3:master): ");
			int computerSkill = sc.nextInt();
			sc.nextLine(); 
			players[indexCounter] = new ComputerPlayer(board, wheel, computerSkill, computerName);
			indexCounter++; 
		}
		
		System.out.print("\nHow many rounds do you want to play: ");
		int numberOfRounds = sc.nextInt();
		sc.nextLine();
		System.out.println("Start of game");
		board.generateSecretSentence();
		System.out.println(board.getAnswerKeyString()); // debug
		while(roundCounter < numberOfRounds) {
			playerRotation = 0; 
			while(true) {
				System.out.println(board.getPhraseString());
				System.out.print("Incorrect Letters: ");
				System.out.print(board.getIncorrectLettersString());
				System.out.println();
				PlayResult result = players[playerRotation].play();
				if(result == PlayResult.BANKRUPT || result == PlayResult.LOSE_TURN) {
					playerRotation++;
				} else {
					board.generateSecretSentence();
					break;
				}
				if(playerRotation > players.length - 1) {
					playerRotation = 0; 
				}
			}
			System.out.println("Next Round!");
			roundCounter++;
		}
		if(numberOfHumanPlayers + numberOfComputerPlayers == 2) {
			if(players[0].getBalence() < players[1].getBalence()) {
				System.out.println(players[0].getName() + " is the winner");
			} else {
				System.out.println(players[0].getName() + " is the winner");
			}
		} else if(numberOfHumanPlayers + numberOfComputerPlayers == 3) {
			if(players[0].getBalence() > players[1].getBalence() && players[0].getBalence() > players[2].getBalence()) {
				System.out.println(players[0].getBalence() + " is the winner");
			} else if(players[1].getBalence() > players[0].getBalence() && players[1].getBalence() > players[2].getBalence()) {
				System.out.println(players[1].getName() + " is the winner");
			} else {
				System.out.println(players[2].getName() + " is the winner");
			}
		}
		System.out.println("End of Program");
	}
}
