package efreiDicewars;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Player{
    private int ID;
    private String Name;
    private int DiceStock;
    private ArrayList<Integer> list_of_territories;
    
    Player(int id, String name, int diceStock){
        this.ID = id;
        this.Name = name;
        this.list_of_territories = new ArrayList<Integer>();
        this.DiceStock = diceStock;
    }
    
    public String getName() {
    	return this.Name;
    }
    
    public int getID() {
    	return this.ID;
    }
    
    public void changeID(int id) {
    	this.ID = id;
    }
    
    public ArrayList<Integer> getTerritories() {
    	return this.list_of_territories;
    }
    
    public void addTerritory(int id) {
    	this.list_of_territories.add(id);
    }
    
    public void removeTerritory(int id) {
    	this.list_of_territories.remove(id);
    }
    
    public int getDiceStock() {
    	return this.DiceStock;
    }
    
    public void removeDiceStock(int dices) {
    	this.DiceStock = this.DiceStock - dices;
    }
    
  /*
    public static void main(String[] args) throws InvalidInput,InvalidInput_1, InvalidInput_2,SameInput{
        Player P1 =  new Player(1);
        P1.list_of_territories.add(1);
        P1.list_of_territories.add(3);
        P1.attackTerritory();
    }
   */

    // return true if the attack is a success
    public boolean rollDices(Territory territoryAttacked, Territory territoryAttacker) {
    	int diceRoll;
    	int attackedSum = 0;
    	int attackerSum = 0;
    	
    	System.out.print("Dices of the attacker territory: ");
    	for (int x = 0; x < territoryAttacker.getDiceNumber(); x++) {
    		diceRoll = Map.getRandomInt(6) + 1;
    		System.out.print(diceRoll);
    		if (x != (territoryAttacker.getDiceNumber()) - 1)
    			System.out.print(" + ");
    		attackerSum += diceRoll;
    	}
    	System.out.println("\nScore attacker = " + attackerSum);
    	
    	System.out.print("Dices of the defended territory: ");
    	for (int x = 0; x < territoryAttacked.getDiceNumber(); x++) {
    		diceRoll = Map.getRandomInt(6) + 1;
    		System.out.print(diceRoll);
    		if (x != (territoryAttacked.getDiceNumber()) - 1)
    			System.out.print(" + ");
    		attackedSum += diceRoll;
    	}
    	System.out.println("\nScore defender = " + attackedSum);
    
    	if (attackedSum > attackerSum)
    		return false;
    	return true;
    }
    
    public void runAttack(Territory territoryAttacked, Territory territoryAttacker, ArrayList<Player> players) {
    	if (rollDices(territoryAttacked, territoryAttacker) == true) {
    		// add territory id to the winner of this fight
    		Player attacker = players.get(territoryAttacker.getPlayerID());
    		attacker.addTerritory(territoryAttacked.getID());
    		// remove territory id from the loser of the fight
    		Player attacked = players.get(territoryAttacked.getPlayerID());
    		attacked.removeTerritory(territoryAttacked.getID());
    		// update the number of dices of the attacked territory
    		territoryAttacked.updateDiceNumber(territoryAttacker.getDiceNumber() - 1);
    		territoryAttacked.updatePlayerID(territoryAttacker.getPlayerID());
    	}
    	// update neighbors
    	territoryAttacker.updateDiceNumber(1);
    }
    
    public void attackTerritory(Map map, ArrayList<Player> players, Scanner s) {
        int attacking_territoryX = -1;
        int attacking_territoryY = -1;
        int targeted_territoryX = -1;
        int targeted_territoryY = -1;
        do {
        	if (!map.attackPossible(this.ID) &&
        		(attacking_territoryX >= 0 && attacking_territoryX < map.getX()) && 
        		(attacking_territoryY >= 0 && attacking_territoryY < map.getY()))
        		System.out.println("This territory doesn't belong to you or doesn't have enough dice");
        	System.out.println("Enter the coordinates x y of the attacking territory (format: \"x y\"):");
        	if(s.hasNextInt()) 
    		{
        		attacking_territoryX = s.nextInt();
    		}
        	if(s.hasNextInt()) 
    		{
        		attacking_territoryY = s.nextInt();
    		}
        	s.nextLine();
        }
        while (!map.attackPossible(this.ID) &&
        		!(targeted_territoryX >= 0 && targeted_territoryX < map.getX()) && 
        		!(targeted_territoryY >= 0 && targeted_territoryY < map.getY()));
        do {
        	if (map.attackPossible(this.ID) &&
        		(targeted_territoryX >= 0 && targeted_territoryX < map.getX()) && 
        		(targeted_territoryY >= 0 && targeted_territoryY < map.getY()))
        		System.out.println("This territory belongs to you (can't attack it) or doesn't have enough dice");
        	System.out.println("Enter the coordinates x y of the attacked territory (format: \"x y\"):");
        	if(s.hasNextInt()) 
    		{
        		targeted_territoryX = s.nextInt();
    		}
        	if(s.hasNextInt()) 
    		{
        		targeted_territoryY = s.nextInt();
    		}
        	s.nextLine();
        }
        while (map.attackPossible(this.ID) &&
        		!(targeted_territoryX >= 0 && targeted_territoryX < map.getX()) && 
        		!(targeted_territoryY >= 0 && targeted_territoryY < map.getY()));
        runAttack(map.getTerritoryByCoordinates(targeted_territoryX, targeted_territoryY), map.getTerritoryByCoordinates(attacking_territoryX, attacking_territoryY), players);
    }

    public void endTurn(Map map){
        /*throws SkipTurn */
    	if (this.getTerritories().size() == 0) {
    		return;
    	}
    	int bonus = map.getLargestTerritoriesContiguous(this.getID());
    	Territory territory;
    	int id;
    	int dices;
    	for (int x = 0; x < bonus; x++) {
    		do {
    			id = Map.getRandomInt(this.list_of_territories.size());
    			territory = map.getTerritory(id);
    			dices = territory.getDiceNumber();
    		}
    		while (dices == 8 && !map.maxDicesOnTerritories(this.getID()));
    		territory.updateDiceNumber(dices + 1);
    	}
    }
}