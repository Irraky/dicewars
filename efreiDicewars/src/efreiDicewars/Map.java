package efreiDicewars;

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
		int stockEmptyTerritory = this.y - 1;
		for (int x = 0; x < xSizeMap; x++) {
			for (int y = 0; y < this.y; y++) {
				int belongs = -1;
				System.out.print("in it");
				if (emptyZonePossible(x, y, this.y, stockEmptyTerritory)) {
					System.out.print("in it");
					int z = 0;
					while (belongs == -1 || stockEmpty(belongs, xSizeMap, game, stockEmptyTerritory)) {
						belongs =  getRandomInt(xSizeMap + 1);
						System.out.print("belongs: " + Integer.toString(belongs));
						z++;
						if (z == 10)
							break;
					}
				} else {
					System.out.println("OUT");
					int z = 0;
					while (belongs == -1 || stockEmpty(belongs, xSizeMap, game, stockEmptyTerritory)) {
							belongs =  getRandomInt(xSizeMap);
							z++;
							if (z == 10)
								break;
					}
				}
				System.out.println("");
				if (belongs != xSizeMap) {
					int maxDices = 9;
					int dices;
					Player player = game.get(belongs);
					if (player.getDiceStock() < 8)
						maxDices = player.getDiceStock() + 1;
					dices =  getRandomInt(maxDices);
					// case too much dices for a player at the end of the array
					if ((8 * (this.y- 1 - player.getTerritories().size())) == player.getDiceStock())
						dices = 8;
					this.Map[x][y] = new Territory(belongs, dices);
					getNeighbours(x, y);
				}
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

	private boolean stockEmpty(int belongs, int xSizeMap, Game game, int stockEmptyTerritory) {
		System.out.println("belongs: " + Integer.toString(belongs) + "  xmap: "+ Integer.toString(xSizeMap));
		if (belongs == xSizeMap && stockEmptyTerritory > 0)
			return true;
		System.out.println("no null");
		Player player = game.get(belongs);
		if (player.getTerritories().size() < this.x - 1)
			return true;
		return false;
			
	}

	private boolean emptyZonePossible(int x, int y, int yMax, int stockEmptyTerritory) {
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
	
	public void displayMap() {
		for (int x = 0; x < this.x; x++) {
			for (int y = 0; y < this.y; y ++) {
				System.out.print(this.Map[x][y]);
			}
			System.out.println("");
		}
	}
	
	public static int getRandomInt(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}

}
