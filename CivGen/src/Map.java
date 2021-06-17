
public class Map {
	public int WORLD_HEIGHT = 100;
	public int WORLD_WIDTH = 100;
	public byte mp[][];
	
	public int wild_neighbor,
	farm_neighbor,
	barren_neighbor,
	village_neighbor,
	ruins_neighbor,
	evil_neighbor;
	// Tile State Catalog
	// 0 = wild
	// 1 = farm
	// 2 = barren
	// 3 = village
	// 4 = ruins
	// 5 = darkness / evil
	
	public Map(int width, int height){
		WORLD_HEIGHT = height;
		WORLD_WIDTH = width;
		mp = new byte[WORLD_HEIGHT][WORLD_WIDTH];
		for(int x = 0; x < WORLD_WIDTH; x++)
		{			
			for(int y = 0; y < WORLD_HEIGHT; y++)
			{
				this.mp[y][x] = 0;
			}
		}
	}
	
	//randomly seeds some number (i) of tiles
	public void seed(int i)
	{
		for(int j = 0; j < i; j++)
		{
			int y = (int)(Math.random() * WORLD_HEIGHT);
			int x = (int)(Math.random() * WORLD_WIDTH);
			mp[y][x] = 3;								//spawn i number of villages
			y = (int)(Math.random() * WORLD_HEIGHT);
			x = (int)(Math.random() * WORLD_WIDTH);
			mp[y][x] = 5;								//spawn i number of evil
		}
	}
	
	public void setState(int x, int y, byte s)
	{
		this.mp[y][x] = s;
	}
	
	public byte getState(int x, int y)
	{
		return this.mp[y][x];
	}
	
	public void age(int i)
	{
		for(int j = 0; j < i; j++)
		{
			this.age();
		}
	}
	
	public void age()
	{
		byte nextGen[][] = new byte[WORLD_HEIGHT][WORLD_WIDTH];
		
		for(int y = 0; y < WORLD_HEIGHT; y++)
		{			
			for(int x = 0; x < WORLD_WIDTH; x++)
			{	
				this.clearNeighbors();
				this.countNeighbors(y, x);
				
				if(mp[y][x] == 0)								// WILD
				{
					if(evil_neighbor > 0 && village_neighbor > 0)
					{
						if(evil_neighbor > village_neighbor)
						{
							nextGen[y][x] = 2;											//become barren
						}else{
							nextGen[y][x] = 1;											//become farm
						}
					}
					else if(village_neighbor > 0 || farm_neighbor > 2){
						if((int)((Math.random() * 100) + 1) < 60) nextGen[y][x] = 1;	//become farm
						else nextGen[y][x] = 0;										//stay wild
					}
					if(evil_neighbor > 0 || barren_neighbor > 3){
						if((int)((Math.random() * 100) + 1) < 80) nextGen[y][x] = 4;	//become barren
						else nextGen[y][x] = 0;										//stay wild
					}
				}
				else if(mp[y][x] == 1)							// FARM
				{
					if(farm_neighbor > 5) nextGen[y][x] = 3;							//become village
					else if(evil_neighbor > 2 || barren_neighbor > 4)
					{
						if(evil_neighbor > 4) nextGen[y][x] = 5;						//become evil
						if((int)(Math.random() * 100) + 1 > 70)
							nextGen[y][x] = 2;											//become barren
					}else nextGen[y][x] = 1;										//stay farm
				}
				else if(mp[y][x] == 2)							// BARREN
				{
					if(evil_neighbor > 1 || barren_neighbor > 6)
					{
						nextGen[y][x] = 5;											//become evil
					}
					else if(farm_neighbor > 4 && village_neighbor > 0){
						if((int)(Math.random() * 100) + 1 > 40) nextGen[y][x] = 0;	//become wild
						else nextGen[y][x] = 2;										//stay barren
					}else if(wild_neighbor > 6) {
						nextGen[y][x] = 0;											//become wild
					}
				}
				else if(mp[y][x] == 3)							// VILLAGE
				{
					if(village_neighbor < 5){
						if(evil_neighbor > 3 || barren_neighbor > 6){
							nextGen[y][x] = 4;										//become ruins
						}
					}else nextGen[y][x] = 3;										//stay village
				}
				else if(mp[y][x] == 4)							// RUINS
				{
					if(farm_neighbor > 3 && barren_neighbor > 3){
						if(farm_neighbor > barren_neighbor)
						{
							nextGen[y][x] = 3;										//become village
						}else{
							nextGen[y][x] = 5;										//become evil
						}				
					}else if(wild_neighbor > 3) nextGen[y][x] = 0;					//become wild
					else nextGen[y][x] = 4;											//stay ruins					
				}
				else if(mp[y][x] == 5)							// EVIL
				{
					if(village_neighbor > 3)
					{
						nextGen[y][x] = 0;											//become wild
					}
				}
			}
		}
		
		mp = nextGen;
	}
	
	public void clearNeighbors()
	{
		wild_neighbor 	= 0;
		farm_neighbor 	= 0;
		barren_neighbor = 0;
		village_neighbor = 0;
		ruins_neighbor 	= 0;
		evil_neighbor 	= 0;
	}
	
	public void countNeighbors(int y, int x)
	{
		byte nw, n, ne, w, e, sw, s, se;
		
		if(y != WORLD_HEIGHT-1){
			n = mp[y+1][x];
			incrementNeighbor(n);
			if(x != 0){
				nw = mp[y+1][x-1];
				incrementNeighbor(nw);
			}
			if(x != WORLD_WIDTH-1){
				ne = mp[y+1][x+1];
				incrementNeighbor(ne);
			}
		}
		if(y != 0)
		{
			s = mp[y-1][x];
			incrementNeighbor(s);
			if(x != 0){
				sw = mp[y-1][x-1];
				incrementNeighbor(sw);
			}
			if(x != WORLD_WIDTH-1){
				se = mp[y-1][x+1];
				incrementNeighbor(se);
			}
		}
		if(x != 0){
			w = mp[y][x-1];
			incrementNeighbor(w);
		}
		if(x != WORLD_WIDTH-1){
			e = mp[y][x+1];
			incrementNeighbor(e);
		}
	}
	
	public void incrementNeighbor(byte p)
	{
		if(p == 0) wild_neighbor++;
		if(p == 1) farm_neighbor++;
		if(p == 2) barren_neighbor++;
		if(p == 3) village_neighbor++;
		if(p == 4) ruins_neighbor++;
		if(p == 5) evil_neighbor++;
	}
	
	public void printNeighborCount()
	{
		System.out.println("wild_neighbor = " + wild_neighbor);
		System.out.println("farm_neighbor = " + farm_neighbor);
		System.out.println("barren_neighbor = " + barren_neighbor);
		System.out.println("village_neighbor = " + village_neighbor);
		System.out.println("ruins_neighbor = " + ruins_neighbor);
		System.out.println("evil_neighbor = " + evil_neighbor);
	}
}
