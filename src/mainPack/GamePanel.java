package mainPack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	
	final int originalTileSize = 16; // 16x16 tile. Common tile size of retro games.
	final int scale = 3; //3*16 = 48; Resizing for modern, high res screen.
	
	final int tileSize = originalTileSize * scale; // 48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//Frames per Second
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; // a thread is something you can stop and start. Once a thread is started it keeps your program running until you stop it. 
		//This also helps animate our game by refreshing the picture, say, 60 times per second.
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
		
	public void startGameThread() {
		gameThread = new Thread(this); //keyword "this" passes the GamePanel through this constructor. This instantiates the thread.
		gameThread.start();
	}
	
	@Override
//		public void run() {
//		
//		double drawInterval = 1000000000/FPS;
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		
//		while(gameThread != null) {
//				
//				
//				// 1 UPDATE: update information such as character positions
//				update();
//				
//				//2 DRAW: draw the screen with the updated information
//				repaint();
//				
//				
//				
//				try {
//					double remainingTime = nextDrawTime - System.nanoTime();
//					remainingTime = remainingTime/1000000;
//					
//					if(remainingTime < 0) {
//						remainingTime = 0;
//					}
//					
//					Thread.sleep((long) remainingTime);
//					
//					nextDrawTime += drawInterval;
//					
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			
//			
//		}
	
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				
				update ();
				repaint();
				delta--;
				drawCount++;
				
			}
			
			if (timer>= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
		
	}
	
	public void update() {
		
		if(keyH.upPressed == true) {
			playerY -= playerSpeed; 
			
		}
		else if (keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if (keyH.leftPressed == true) {
			playerX -= playerSpeed;
		}else if (keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
		
	}

}
