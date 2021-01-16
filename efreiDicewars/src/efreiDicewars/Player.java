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
    	System.out.println("\nScore defender = " + attackerSum);
    
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
    
    public void attackTerritory() throws InvalidInput, InvalidInput_1, InvalidInput_2, SameInput{
        int attacking_territory = -1;
        int targeted_territory = -1;
        System.out.println("Enter the attacking territory followed by a space and the targeted territory: ");
        int attempt_1 = 1;
        int attempt_2 = 1;
        int attempt_3 = 1;
        int last_seen = 0;
        Scanner s = new Scanner(System.in);
        Pattern p = Pattern.compile("^(\\d+)(\\s+)(\\d+)$", Pattern.MULTILINE);
        Pattern p2 = Pattern.compile("^(\\d+)(\\s+)(\\D+)$", Pattern.MULTILINE);
        Pattern p2_1 = Pattern.compile("^(\\d+)$", Pattern.MULTILINE);
        Pattern p3 = Pattern.compile("^(\\D+)(\\s+)(\\d+)$", Pattern.MULTILINE);
        Pattern p4 = Pattern.compile("^(\\D+)$", Pattern.MULTILINE);
        Pattern p5 = Pattern.compile("^(\\s+)$", Pattern.MULTILINE);
        while(s.hasNextLine() && (targeted_territory ==-1 || attacking_territory < 0) && (attempt_3 == 1 || attempt_2 == 1 || attempt_1 == 1)){
            if(s.findInLine(p)!=null && attempt_1 == 1 && attempt_2 == 1){
                MatchResult result = s.match();
                //System.out.println("full line:" + result.group(0));
                //System.out.println("individuals");
                for (int i=1; i<=result.groupCount(); i++){
                    if (i == 1){
                        attacking_territory = Integer.parseInt(result.group(i));
                        attempt_1 = 0;
                    }
                    if (i == 3) {
                        targeted_territory = Integer.parseInt(result.group(i));
                        attempt_2 = 0;
                        last_seen = 2;
                    }
                }
            }
            else{
                if((s.findInLine(p2)!=null) && attempt_1 == 1){
                    MatchResult result = s.match();
                    for (int i=1; i<=1; i++){
                        if (i == 1){
                            attacking_territory = Integer.parseInt(result.group(i));
                            last_seen = 1;
                        }
                    }
                    attempt_1 = 0;
                    s.nextLine();
                    s = new Scanner(System.in);
                    System.out.println("Wrong input reenter the targeted territory");
                }
                if(s.findInLine(p3)!=null && attempt_2 == 1){
                    MatchResult result = s.match();
                    for (int i = 3; i<=result.groupCount(); i++){
                        if (i == 3){
                            targeted_territory = Integer.parseInt(result.group(i));
                            attempt_2 = 0;
                        }
                        last_seen = 2;
                    }
                    System.out.println("");
                    s.nextLine();
                    s = new Scanner(System.in);
                    System.out.println("Wrong input reenter the attacking territory");
                }
                if(last_seen == 2 && attempt_1 == 1 && attempt_2 == 0 && attacking_territory<0){
                    attempt_1 = 1;
                    do{
                        if((s.findInLine(p2_1)!=null) && attempt_1 == 1){
                            MatchResult result = s.match();
                            for (int i=1; i<=result.groupCount(); i++) {
                                if (i == 1) {
                                    attacking_territory = Integer.parseInt(result.group(i));
                                    attempt_1 = 0;
                                    last_seen = 2;
                                }
                            }
                        }else{
                            if(s.hasNextLine() && (s.findInLine(p4)!=null || s.findInLine(p5)!=null || s.findInLine(p2_1)==null)){
                                System.out.println("Wrong input.\nReenter the attacking territory: ");
                                s = new Scanner(System.in);
                            }
                        }
                    }while(s.hasNextLine() && attacking_territory < 0 && (attempt_1 == 1));
                }
                if(last_seen == 1 && attempt_2 == 1 && attempt_1 == 0 && targeted_territory<0){
                    attempt_2 = 1;
                    do{
                        if((s.findInLine(p2_1)!=null) && attempt_2 == 1){
                            MatchResult result = s.match();
                            for (int i=1; i<=result.groupCount(); i++) {
                                if (i == 1) {
                                    targeted_territory = Integer.parseInt(result.group(i));
                                    attempt_2= 0;
                                }
                            }
                        }else{
                            if(s.hasNextLine() && (s.findInLine(p4)!=null || s.findInLine(p5)!=null || s.findInLine(p2_1)==null)){
                                System.out.println("Wrong input.\nReenter the targeted territory: ");
                                s = new Scanner(System.in);
                            }
                        }
                    }while(s.hasNextLine() && targeted_territory < 0 && (attempt_1 == 1));
                }
            }
            if(targeted_territory <0 && attacking_territory <0 && last_seen == 0 && attempt_1 == 1 && attempt_2 == 1) {
                System.out.println("Wrong input.\nEnter the attacking territory followed by a space and the targeted territory: ");
                s = new Scanner(System.in);
            }
            if(attempt_1 == 0 && attempt_2 == 0 && targeted_territory == attacking_territory){
                System.out.println("You can't attack yourself");
                if(last_seen == 2){
                    attempt_1 = 1;
                    s = new Scanner(System.in);
                    System.out.println("Reenter the attacking territory: ");
                    attacking_territory = -1;
                }
                if(last_seen == 1){
                    attempt_2 = 1;
                    targeted_territory = -1;
                    s = new Scanner(System.in);
                    System.out.println("Reenter the targeted territory: ");
                }
            }else{
                if(attempt_1 == 0 && attempt_2 == 0){
                    attempt_3 = 0;}
            }
        }
        s.close();
        System.out.println(attacking_territory);
        System.out.println(targeted_territory);
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

class InvalidInput extends Exception{
    private String myData;
    public InvalidInput(String data){
        super(data);
    }

    public String getMyData() {
        return myData;
    }
}
class ErrorIntegerInput extends Error{
    private String myData;
    public ErrorIntegerInput(String data){myData = new String(data);};
    public String getMyData(){return myData;}
}
class InvalidInput_1 extends Exception{
    private String myData;
    public InvalidInput_1(String data){
        super(data);
    }

    public String getMyData() {
        return myData;
    }
}
class ErrorIntegerInput_1 extends Error{
    private String myData;
    public ErrorIntegerInput_1(String data){myData = new String(data);};
    public String getMyData(){return myData;}
}
class InvalidInput_2 extends Exception {
    private String myData;

    public InvalidInput_2(String data) {
        super(data);
    }

    public String getMyData() {
        return myData;
    }
}
class ErrorIntegerInput_2 extends Error{
    private String myData;
    public ErrorIntegerInput_2(String data){myData = new String(data);};
    public String getMyData(){return myData;}
}
class SameInput extends Exception{
    private String myData;
    public SameInput(String data){
        super(data);
    }

    public String getMyData() {
        return myData;
    }
}
class ErrorSameIntegerInput extends Error{
    private String myData;
    public ErrorSameIntegerInput(String data){myData = new String(data);};
    public String getMyData(){return myData;}
}