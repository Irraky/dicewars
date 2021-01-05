package efreiDicewars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Game implements Iterable<Player> {
	private ArrayList<Player> players;
	private int actualPlayer;
	
	@Override
	public Iterator<Player> iterator() {
		return players.iterator();
	}
	
	public Game() {
		players = new ArrayList<Player>();
		actualPlayer = 0;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void add(Player player) {
		players.add(player);
	}

	public static void main(String[] args) {
    	Scanner scanner = new Scanner( System.in );
    	String answer;
    	
    	System.out.println("Welcome to dicewars!");
    	System.out.println("Do you want to play a game ? (Yes-no)");
    	answer = scanner.nextLine();
    	if (answer.equals("no")) {
    		System.out.println("Okay then bye");
    		scanner.close();
    		return;
    	}
    	System.out.println("Let's play then! \nFirst, we need to set up some details.");
    	
    	// Do you want to play on an existing map ? (Yes-no)  READ FROM CSV TO DO
    	System.out.println("\nDo you want to play on a small, Medium or huge map?");
    	answer = scanner.nextLine();
    	int size;
    	if (answer.equals("small")) {
    		size = 2;
    		System.out.println("You chose to play on a small map");
    	}
    	else if (answer.equals("medium")) {
    		size = 4;
    		System.out.println("You chose to play on a medium map");
    	}
    	else {
    		size = 6;
    		System.out.println("You chose to play on a huge map");
    	}
    	
    	System.out.println("\nWe have the board, we now need the players.");
    	int nbPlayers = 0;
    	while (nbPlayers > 6 || nbPlayers < 2) {
    		System.out.println("Enter the number of players (between 2 and 6): ");
    		nbPlayers = scanner.nextInt();
    		// complete the reading of the line
    		scanner.nextLine();
    	}
    	
    	Game game = new Game();
    	for (int i = 0; i < nbPlayers; i++) {
    		System.out.println("Name of the player: ");
    		String name = scanner.nextLine();
    		Player player = new Player(i, name, size * 3);
    		game.players.add(player);
    	}
    	Collections.shuffle(game.players);
    	game.actualPlayer = 0;
    	System.out.println("List of the players: ");
    	int id = 0;
    	for (Player s : game.players) {
    		s.changeID(id);
			System.out.println("Player " + s.getID() + ": " + s.getName());
		}
    	scanner.close();
    	Map map = new Map(game, nbPlayers, size);
    	System.out.println("\n Map at beginning with [owner id, number of dices]:");
    	map.displayMap();
    	/*
    	boolean endTurn;
    	while (!map.oneOwner()) {
    		endTurn = false;
    		System.out.println(" turn:");
    		while (endTurn == false) {
    			
    		}
    	}*/
    }

	public Player get(int belongs) {
		for (Player s : players) {
			if (belongs == s.getID())
			return s;
		}
		return null;
	}
}
