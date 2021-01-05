package efreiDicewars;

import java.util.ArrayList;
import java.util.Random;

public class Map {
	private Territory[][] Map;
	private int x;
	private int y;
	
	public Map() {
	}
	
	public Map(Game game, int xSizeMap, int ySizeMap) {
		this.x = xSizeMap;
		this.y = ySizeMap;
		this.Map = new Territory [this.x][this.y];
		for (int x = 0; x < xSizeMap; x++) {
			for (int y = 0; y < this.y; y++) {
				int belongs = -1;
				// cases with problem, need an other solution if we want empty places
				/*if (emptyZonePossible(x, y, this.y - 1, stockEmptyTerritory)) {
					System.out.print("in it");
					while (belongs == -1 || !stockEmpty(belongs, xSizeMap, game, stockEmptyTerritory)) {
						belongs =  getRandomInt(xSizeMap + 1);
						System.out.print("belongs: " + Integer.toString(belongs));
					}
				} else {
				*/
					while (belongs == -1 || !stockEmpty(belongs, xSizeMap, game.getPlayers())) {
							belongs =  getRandomInt(xSizeMap);
					}
					int maxDices = 8;
					int dices;
					Player player = game.getPlayers().get(belongs);
					if (player.getDiceStock() < 8)
						maxDices = player.getDiceStock();
					dices =  getRandomInt(maxDices) + 1;
					// case too few dices
					if ((this.y - player.getTerritories().size()) == player.getDiceStock())
						dices = 1;
					// case too few dices 2
					if ((player.getDiceStock() - dices) < (this.y - player.getTerritories().size() - 1))
							dices = dices - (player.getDiceStock() - this.y + player.getTerritories().size() + 1);
					// case too much dices for a player at the end of the array
					else if ((8 * (this.y - player.getTerritories().size())) == player.getDiceStock())
						dices = 8;
					// case last cell, put the rest of the dices
					else if ((this.y - player.getTerritories().size()) == 1 && player.getDiceStock() < 8)
						dices = player.getDiceStock();
					player.removeDiceStock(dices);
					this.Map[x][y] = new Territory(belongs, dices);
					getNeighbours(x, y);
					player.addTerritory(this.Map[x][y].getID());
			}
		}
	}
	
	private void getNeighbours(int x, int y) {
		// upper neighbor
		if (x != 0 && this.Map[x-1][y] != null) {
			if (!this.Map[x-1][y].getNeighborID().contains(this.Map[x][y].getPlayerID())) {
				this.Map[x-1][y].getNeighborID().add(this.Map[x][y].getPlayerID());
			}
			if (!this.Map[x][y].getNeighborID().contains(this.Map[x-1][y].getPlayerID())) {
				this.Map[x][y].getNeighborID().add(this.Map[x-1][y].getPlayerID());
			}
		}
	
		// right neighbor
		if (y + 1 < this.y && this.Map[x][y + 1] != null) {
			if (!this.Map[x][y+1].getNeighborID().contains(this.Map[x][y].getPlayerID())) {
				this.Map[x][y+1].getNeighborID().add(this.Map[x][y].getPlayerID());
			}
			if (!this.Map[x][y].getNeighborID().contains(this.Map[x][y+1].getPlayerID())) {
				this.Map[x][y].getNeighborID().add(this.Map[x][y+1].getPlayerID());
			}
		}
	
		// bottom neighbor
		if (x + 1 < this.x && this.Map[x+1][y] != null) {
			if (!this.Map[x+1][y].getNeighborID().contains(this.Map[x][y].getPlayerID())) {
				this.Map[x+1][y].getNeighborID().add(this.Map[x][y].getPlayerID());
			}
			if (!this.Map[x][y].getNeighborID().contains(this.Map[x+1][y].getPlayerID())) {
				this.Map[x][y].getNeighborID().add(this.Map[x+1][y].getPlayerID());
			}
		}
		
		// left neighbor
		if (y > 0 && this.Map[x][y - 1] != null) {
			if (!this.Map[x][y-1].getNeighborID().contains(this.Map[x][y].getPlayerID())) {
				this.Map[x][y-1].getNeighborID().add(this.Map[x][y].getPlayerID());
			}
			if (!this.Map[x][y].getNeighborID().contains(this.Map[x][y-1].getPlayerID())) {
				this.Map[x][y].getNeighborID().add(this.Map[x][y-1].getPlayerID());
			}
		}
	}

	private boolean stockEmpty(int belongs, int xSizeMap, ArrayList<Player> players) {
		Player player = players.get(belongs);
		if (player.getTerritories().size() < this.y)
			return true;
		return false;
			
	}
	
// useless for the moment
/*	private boolean emptyZonePossible(int x, int y, int yMax, int stockEmptyTerritory) {
		if (stockEmptyTerritory == 0)
			return false;
		if (x == 0)
			return true;
		if (this.Map[x-1][y] != null
				&& (y == 0 || (x != 0 && this.Map[x-1][y-1] == null))
				&& (y == yMax || (x != 0 && this.Map[x-1][y+1] == null))
				&& (x == 1 || (x > 1 && this.Map[x-2][y] == null)))
			return false;
		return true;
	}
*/
	
	public void displayMap() {
		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y ++) {
				System.out.print(this.Map[x][y]);
			}
			System.out.println("");
		}
	}
	
	public boolean oneOwner() {
		int id = this.Map[0][0].getPlayerID();
		for (int x = 1; x < this.x; x++) {
			for (int y = 0; y < this.y; y ++) {
				if (id != this.Map[x][y].getPlayerID())
					return false;
			}
		}
		return true;
	}
	
	public int getWinner() {
		return this.Map[0][0].getPlayerID();
	}
	
	public static int getRandomInt(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}

}
