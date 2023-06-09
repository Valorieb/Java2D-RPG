package mainPack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	
	final int originalTileSize = 16; // 16x16 tile. Common tile size of retro games.
	final int scale = 3; //3*16 = 48; Resizing for modern, high res screen.
	
	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	// WORLD SETTINGS	
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	//Frames per Second
	int FPS = 60;
	
	
	//SYSTEM
	TileManager tileM = new TileManager (this);
	KeyHandler keyH = new KeyHandler();
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; // a thread is something you can stop and start. Once a thread is started it keeps your program running until you stop it. 
	//This also helps animate our game by refreshing the picture, say, 60 times per second.
	
	//ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10]; //Can display up to ten objects on screen at a time
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		
		playMusic(0);
	}
		
	public void startGameThread() {
		gameThread = new Thread(this); //keyword "this" passes the GamePanel through this constructor. This instantiates the thread.
		gameThread.start();
	}
	
	@Override
	
	//SLEEP GAME LOOP
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
	
	
	//DELTA GAME LOOP
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
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//TILE
		tileM.draw(g2); //tiles need to be drawn before the player so that they are in the background and not over the character
		
		//OBJECT
		for (int i = 0; i < obj.length; i++) {
			
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		
		//PLAYER
		player.draw(g2);
		
		//UI
		ui.draw(g2);
		
		g2.dispose();
		
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
		
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
