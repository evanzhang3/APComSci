import java.util.Scanner;

public class GameController {

	public static void main(String[] args) {
		int numberOfHumanPlayers;
		int numberOfComputerPlayers;
		String humanPlayer1;
		String humanPlayer2; 
		String humanPlayer3;
		String computerPlayer1;
		int computer1Difficulty;
		String computerPlayer2;
		int computer2Difficulty;
		int roundCounter = 0; 
		Player[] players;
		int playerRotation;
		int totalPlayers = 0; 
		int indexCounter = 0; 
		Scanner sc = new Scanner(System.in);
		Wheel wheel = new Wheel();
		Board board = new Board("D:\\ComSci_Wheel_Of_Fortune_Project\\ComSciProject.txt");
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
		if(numberOfHumanPlayers == 1) {
			System.out.println("What is the name of the human player?");
			humanPlayer1 = sc.nextLine();
			Player humanPlayerOne = new HumanPlayer(board,wheel, humanPlayer1);
			for(int i = 0; i < numberOfHumanPlayers; i++) {
				players[i] = (HumanPlayer) humanPlayerOne;
				indexCounter++;
			}
		} else if(numberOfHumanPlayers == 2){
			System.out.println("What is the name of the first human player?");
			humanPlayer1 = sc.nextLine();
			Player humanPlayerOne = new HumanPlayer(board, wheel, humanPlayer1);
			System.out.println("What is the name of the second human player");
			humanPlayer2 = sc.nextLine(); 
			Player humanPlayerTwo = new HumanPlayer(board, wheel, humanPlayer2);
			for(int i = 0; i < numberOfHumanPlayers; i++) {
				if(i == 1)
					players[i] = (HumanPlayer) humanPlayerOne;
				indexCounter++;
				if(i == 2)
					players[i] = (HumanPlayer) humanPlayerTwo;
				indexCounter++; 
			}
		} else if(numberOfHumanPlayers == 3) {
			System.out.println("What is the name of the first human player?");
			humanPlayer1 = sc.nextLine();
			Player humanPlayerOne = new HumanPlayer(board, wheel, humanPlayer1);
			System.out.println("What is the name of the second human player");
			humanPlayer2 = sc.nextLine(); 
			Player humanPlayerTwo = new HumanPlayer(board, wheel, humanPlayer2);
			System.out.println("What is the name of the second human player");
			humanPlayer3 = sc.nextLine(); 
			Player humanPlayerThree = new HumanPlayer(board, wheel, humanPlayer3);
			for(int i = 0; i < numberOfHumanPlayers; i++) {
				if(i == 1)
					players[i] = (HumanPlayer) humanPlayerOne;
				indexCounter++; 
				if(i == 2)
					players[i] = (HumanPlayer) humanPlayerTwo;
				indexCounter++; 
				if(i == 3)
					players[i] = (HumanPlayer) humanPlayerThree;
				indexCounter++; 
			}
		}
		if(numberOfComputerPlayers == 1) {
			System.out.println("What is the name of the computer Player?");
			computerPlayer1 = sc.nextLine();
			System.out.println("What is the level of diffuculty of this computer player?");
			computer1Difficulty = sc.nextInt();
			Player computerPlayerOne = new ComputerPlayer(board, wheel, computer1Difficulty, computerPlayer1);
			sc.nextLine();
			for(int i = 0; i < numberOfComputerPlayers; i++) {
				players[i + indexCounter] = (ComputerPlayer) computerPlayerOne;
				indexCounter++; 
			} 
		} else if(numberOfComputerPlayers == 2) {
			System.out.println("What is the name of the first computer Player?");
			computerPlayer1 = sc.nextLine();
			System.out.println("What is the level of diffuculty of the first computer player?");
			computer1Difficulty = sc.nextInt();
			sc.nextLine();
			Player computerPlayerOne = new ComputerPlayer(board, wheel, computer1Difficulty, computerPlayer1);
			System.out.println("What is the name of the second computer Player?");
			computerPlayer2 = sc.nextLine();
			System.out.println("What is the level of diffuculty of the second computer player?");
			computer2Difficulty = sc.nextInt();
			sc.nextLine();
			Player computerPlayerTwo = new ComputerPlayer(board, wheel, computer2Difficulty, computerPlayer2);
			for(int i = 0; i < numberOfComputerPlayers; i++) {
				if(i == 1)
					players[i + indexCounter] = (ComputerPlayer) computerPlayerOne;
				indexCounter++; 
				if(i == 2)
					players[i + indexCounter] = (ComputerPlayer) computerPlayerTwo;
				indexCounter++; 
			}
		}
		System.out.println("How many rounds do you want to play?");
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
			System.out.println(players[playerRotation].getName() + " guessed the phrase");
			System.out.println("Next Round!");
			roundCounter++;
		}
	}
}
