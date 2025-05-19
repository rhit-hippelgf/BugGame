package roomComponents;
/**
 * This class will be the base of all floor components, boulder will extend tile but load a different image and have collision logic
 * The painting logic will all be the same so that will be done in this tile class whose only method is to draw itself, in this case a
 * white square and boulder and hole will be different color squares until we get sprites implemented. x1,x2,y1,y2 represent the binding box
 * for easier access in collisions t
 */
import roomStuff.RoomLogic;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Color;

public class Tile {

	protected int x1,x2;
	protected int y1,y2;
	protected Color color;
	protected static int TILE = RoomLogic.getTileSize();
	private BufferedImage dirt1;
	private BufferedImage dirt2;
	private BufferedImage dirt3;
	private BufferedImage dirt4;
	private BufferedImage dirt5;
	private BufferedImage dirt6;
	private BufferedImage dirt7;
	private BufferedImage dirt8;
	private BufferedImage stone1;
	private BufferedImage stone2;
	private BufferedImage stone3;
	private BufferedImage stone4;
	private BufferedImage lava1;
	private BufferedImage lava2;
	private BufferedImage lava3;
	private BufferedImage lava4;
	private BufferedImage lava5;
	private BufferedImage lava6;
	private BufferedImage lava7;
	private int n;
	private boolean spriteLoaded;
	private Random r;
	protected int level;
	
	
//	When constructing use the top left of the grid so a tile or boulder, or hole in top left represents a position of x=0 and y=0
	public Tile(int x1, int y1, int level) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1+TILE;
		this.y2 = y1+TILE;
		this.color = Color.WHITE;
		r = new Random();
		if (level <= 2) {
			int n = r.nextInt(8);
		} else if (level <= 4 && level >= 3) {
			int n = r.nextInt(4);
		} else {
			int n = r.nextInt(7);
		}
		try {
			dirt1 = ImageIO.read(new File("assets/sprites/floors/dirtfloor1.png"));
			dirt2 = ImageIO.read(new File("assets/sprites/floors/dirtfloor2.png"));
			dirt3 = ImageIO.read(new File("assets/sprites/floors/dirtfloor3.png"));
			dirt4 = ImageIO.read(new File("assets/sprites/floors/dirtfloor4.png"));
			dirt5 = ImageIO.read(new File("assets/sprites/floors/dirtfloor5.png"));
			dirt6 = ImageIO.read(new File("assets/sprites/floors/dirtfloor6.png"));
			dirt7 = ImageIO.read(new File("assets/sprites/floors/dirtfloor7.png"));
			dirt8 = ImageIO.read(new File("assets/sprites/floors/dirtfloor8.png"));
			stone1 = ImageIO.read(new File("assets/sprites/floors/stonefloor1.png"));
			stone2 = ImageIO.read(new File("assets/sprites/floors/stonefloor2.png"));
			stone3 = ImageIO.read(new File("assets/sprites/floors/stonefloor3.png"));
			stone4 = ImageIO.read(new File("assets/sprites/floors/stonefloor4.png"));
			lava1 = ImageIO.read(new File("assets/sprites/floors/lavafloor1.png"));
			lava2 = ImageIO.read(new File("assets/sprites/floors/lavafloor2.png"));
			lava3 = ImageIO.read(new File("assets/sprites/floors/lavafloor3.png"));
			lava4 = ImageIO.read(new File("assets/sprites/floors/lavafloor4.png"));
			lava5 = ImageIO.read(new File("assets/sprites/floors/lavafloor5.png"));
			lava6 = ImageIO.read(new File("assets/sprites/floors/lavafloor6.png"));
			lava7 = ImageIO.read(new File("assets/sprites/floors/lavafloor7.png"));
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
		
//		for different tile styles can run random to randomly assign one of the n different tile styles applies to boulder and hole
	}
	
//	Every floor tile component will be drawn with this method but subclasses (rock and hole) uses its own color/image
//	each component will load its own image and run the super version of the method I can handle it with room generation
	public void draw(Graphics2D g2) {
		
		if (spriteLoaded == true) {
			if (level <= 2) {
			
				if (n==0) {
					g2.drawImage(dirt1, x1, y1, TILE, TILE, null);
				} else if (n==1) {
					g2.drawImage(dirt2, x1, y1, TILE, TILE, null);
				} else if (n==2) {
					g2.drawImage(dirt3, x1, y1, TILE, TILE, null);
				} else if (n==3) {
					g2.drawImage(dirt4, x1, y1, TILE, TILE, null);
				} else if (n==4) {
					g2.drawImage(dirt5, x1, y1, TILE, TILE, null);
				} else if (n==5) {
					g2.drawImage(dirt6, x1, y1, TILE, TILE, null);
				} else if (n==6) {
					g2.drawImage(dirt7, x1, y1, TILE, TILE, null);
				} else {
					g2.drawImage(dirt8, x1, y1, TILE, TILE, null);
				} 
			} else if (level <= 4 && level >= 3) {
				
				if (n==0) {
					g2.drawImage(stone1, x1, y1, TILE, TILE, null);
				} else if (n==1) {
					g2.drawImage(stone2, x1, y1, TILE, TILE, null);
				} else if (n==2) {
					g2.drawImage(stone3, x1, y1, TILE, TILE, null);
				} else {
					g2.drawImage(stone4, x1, y1, TILE, TILE, null);
				}
			} else {
				if (n==0) {
					g2.drawImage(lava1, x1, y1, TILE, TILE, null);
				} else if (n==1) {
					g2.drawImage(lava2, x1, y1, TILE, TILE, null);
				} else if (n==2) {
					g2.drawImage(lava3, x1, y1, TILE, TILE, null);
				} else if (n==3) {
					g2.drawImage(lava4, x1, y1, TILE, TILE, null);
				} else if (n==4) {
					g2.drawImage(lava5, x1, y1, TILE, TILE, null);
				} else if (n==5) {
					g2.drawImage(lava6, x1, y1, TILE, TILE, null);
				} else {
					g2.drawImage(lava7, x1, y1, TILE, TILE, null);
				} 
			}
		} else {
		
		Color old = g2.getBackground();
		g2.setColor(color);
		g2.fillRect(x1, y1, TILE, TILE);
		g2.setColor(old);
		}
	}
	
	public int[] getXs() {
		int[] xs = {x1,x2};
		return xs;
	}
	
	public int[] getYs() {
		int[] ys = {y1,y2};
		return ys;
	}
	
}
