package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mainPack.GamePanel;

public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		
		//this makes the screen render more quickly. If the map is not currently on the screen, the computer doesn't render it.
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			
			//This calls the method to render tiles from the map.
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null); 
		}
		
	}
		
}


