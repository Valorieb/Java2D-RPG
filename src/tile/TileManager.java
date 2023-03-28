package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import mainPack.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum [] [];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[10]; //We will create 10 kinds of tiles e.g. grass tiles, water tiles, etc.
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world-map.txt");
		
	}
	
	public void getTileImage () {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap( String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath); //this is the format to read the text file
			BufferedReader br = new BufferedReader (new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" "); //gets the numbers one by one by splitting the line into individual strings.
					
					int num = Integer.parseInt(numbers[col]); // converts the numbers from strings to integers
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	public void draw(Graphics2D g2) {
		
//		g2.drawImage(tile[0].image, 0, 0, gp.tileSize,gp.tileSize, null);
//		g2.drawImage(tile[1].image, 48, 0, gp.tileSize,gp.tileSize, null);
//		g2.drawImage(tile[2].image, 96, 0, gp.tileSize,gp.tileSize, null);
		//THIS IS TOO MUCH TYPING FOR EACH INDIVIDUAL TILE. INSTEAD WILL CREATE A WHILE LOOP.
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			
			//this makes the screen render more quickly. If the map is not currently on the screen, the computer doesn't render it.
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				
				//This calls the method to render tiles from the map.
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
			}
				
			
			
			worldCol ++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
				
			}
		}
		
		
		
		
		
	}

}
