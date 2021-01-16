package efreiDicewars;

import java.util.ArrayList;

public class Territory{
	private static int count = 0; 
	private int ID;
	private int PlayerID;
	private ArrayList<Integer> NeighborID;
	private int DiceNumber;
	
	public Territory(int playerID, int diceNumber)
	{
		this.ID = count++;
		this.PlayerID = playerID;
		this.DiceNumber = diceNumber;
		this.NeighborID = new ArrayList<Integer>();
	}
	
	public void updateDiceNumber(int newDiceNumber)
	{
		this.DiceNumber = newDiceNumber;
	}
	
	public void addNeighbour(int neighbour)
	{
		this.NeighborID.add(neighbour);
	}
	
	public void removeNeighbour(int neighbour)
	{
		this.NeighborID.remove(neighbour);
	}
	
		
	public void updatePlayerID(int newPlayerID)
	{
		this.PlayerID = newPlayerID;
	}
	
	public ArrayList<Integer> getNeighborID() {
		return this.NeighborID;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public int getPlayerID() {
		return this.PlayerID;
	}
	
	@Override
	public String toString() {
		return("[" + Integer.toString(this.PlayerID) + ", " + Integer.toString(this.DiceNumber) + "]");
	}
	
}