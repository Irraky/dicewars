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
	
	public int getNextPlayer(Map map) {
		// game finished, player not important
		if (map.oneOwner() == true)
			return this.actualPlayer + 1; 
		int next = (this.actualPlayer + 1) % this.players.size();
		// if player has no territory, he can't play (because he lost)
		while (this.get(this.actualPlayer).getTerritories().size() == 0)
			next = (this.actualPlayer + 1) % this.players.size();
		return next;
	}

	public Player get(int belongs) {
		for (Player s : players) {
			if (belongs == s.getID())
			return s;
		}
		return null;
	}

	public static void main(String[] args) {
    	Scanner scanner = new Scanner( System.in );
    	String answer = "yes";
    	
    	System.out.println("Welcome to dicewars!");
    	System.out.println("Do you want to play a game ? (Yes-no)");
    	while (!answer.equals("no")) {
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
	    	while ( nbPlayers > 6 || nbPlayers < 2) {
	    		System.out.println("Enter the number of players (between 2 and 6): ");
	    		if(scanner.hasNextInt()) 
	    		{
	    		   nbPlayers = scanner.nextInt();
	    		}
	    		// line to complete the reading of the line
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
	    	System.out.println("Order of a turn: ");
	    	int id = 0;
	    	for (Player s : game.players) {
	    		s.changeID(id);
				System.out.println("Player " + s.getID() + ": " + s.getName());
				id++;
			}
	
	    	Map map = new Map(game, nbPlayers, size);
	    	System.out.println("\nMap at beginning with [owner id, number of dices]:");
	    	map.displayMap();
	    	
	    	
	    	boolean endTurn;
	    	while (!map.oneOwner()) {
	    		endTurn = false;
	    		System.out.println("\nTurn of player: " + game.get(game.actualPlayer).getName());
	    		while (endTurn == false) {
	    			System.out.println("Choose your actions:\n1. Attack\n2. end turn");
	    			answer = scanner.nextLine();
	    			if (answer.contains("end turn") || answer.contains("2") || answer.contains("end")) {
	    				game.get(game.actualPlayer).endTurn();
	    				endTurn = true;
	    			}
					else {
						try {
							game.get(game.actualPlayer).attackTerritory();
						} catch (InvalidInput e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidInput_1 e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvalidInput_2 e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SameInput e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
	    		}
	    		game.actualPlayer = game.getNextPlayer(map);
	    	}
	    	System.out.println("End of the game. Player " + game.get(map.getWinner()).getName());
	    	System.out.println("Do you want to play a new game ? (Yes-no) ");
    	}
    	System.out.println("Goodbye");
    	scanner.close();
    }
}
