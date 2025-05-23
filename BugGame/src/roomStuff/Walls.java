package roomStuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Walls {
	private int x;
	private int y;
	private int width, height;
	private final int SCREENWIDTH = RoomLogic.getScreenWidth();
	private final int SCREENHEIGHT = RoomLogic.getScreenHeight();
	private final int TOPGAP = (SCREENHEIGHT - RoomLogic.getRoomHeight())/2;
	private final int SIDEGAP = (SCREENWIDTH - RoomLogic.getRoomWidth())/2;
	private Color color=Color.GRAY;
//	private static int SCALINGSIZE = RoomLogic.getTileSize();
	private BufferedImage normalWall;
	private BufferedImage closeWall;
	private BufferedImage bossDoor;
	private Rectangle noDoor;
	private boolean doorExists;
	private boolean preBoss;
	private char dir;
	private boolean spriteLoaded;
	private boolean isCleared;
	
	public Walls(boolean doorExists, char dir, boolean preBoss) {
		this.doorExists = doorExists;
		this.preBoss = preBoss;
		this.dir = dir;
		this.isCleared=false;
		if (dir == 'w') {
			this.y = TOPGAP;
			this.x = 0;
			this.width = SIDEGAP;
			this.height = SCREENHEIGHT - 2*TOPGAP;
		} else if (dir == 'n') {
			this.y = TOPGAP/3;
			this.x = SIDEGAP;
			this.width = SCREENWIDTH - 2*SIDEGAP;
			this.height = 2*TOPGAP/3;
		} else if (dir == 'e') {
			this.y = TOPGAP;
			this.x = SCREENWIDTH-SIDEGAP;
			this.width = SIDEGAP;
			this.height = SCREENHEIGHT-2*TOPGAP;
		} else if (dir == 's') {
			this.x = SIDEGAP;
			this.y = SCREENHEIGHT-TOPGAP;
			this.width = SCREENWIDTH - 2*SIDEGAP;
			this.height = TOPGAP;
		}
		
		try {
			if (dir == 'n') {
				normalWall = ImageIO.read(new File("assets/sprites/walls/TopWallClear.png"));
				closeWall = ImageIO.read(new File("assets/sprites/walls/TopWallClose.png"));
				bossDoor = ImageIO.read(new File("assets/sprites/walls/nBoss.png"));
				noDoor = new Rectangle(SCREENWIDTH/2-2*SIDEGAP, 0, 4*SIDEGAP,TOPGAP);
			}
			if (dir == 'e') {
				normalWall = ImageIO.read(new File("assets/sprites/walls/RightWallClear.png"));
				closeWall = ImageIO.read(new File("assets/sprites/walls/RightWallClose.png"));
				bossDoor = ImageIO.read(new File("assets/sprites/walls/eBoss.png"));				
				noDoor = new Rectangle(SCREENWIDTH-SIDEGAP, SCREENHEIGHT/2-2*TOPGAP, SIDEGAP,4*TOPGAP);			
				}
			if (dir == 's') {
				normalWall = ImageIO.read(new File("assets/sprites/walls/BottomWallClear.png"));
				closeWall = ImageIO.read(new File("assets/sprites/walls/BottomWallClose.png"));
				bossDoor = ImageIO.read(new File("assets/sprites/walls/sBoss.png"));
				noDoor = new Rectangle(SCREENWIDTH/2-2*SIDEGAP, SCREENHEIGHT-TOPGAP, 4*SIDEGAP,TOPGAP);			
				}
			if (dir == 'w') {
				normalWall = ImageIO.read(new File("assets/sprites/walls/LeftWallClear.png"));
				closeWall = ImageIO.read(new File("assets/sprites/walls/LeftWallClose.png"));
				bossDoor = ImageIO.read(new File("assets/sprites/walls/wBoss.png"));
				noDoor = new Rectangle(0, SCREENHEIGHT/2-2*TOPGAP, SIDEGAP,4*TOPGAP);	
				}
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}
	
	public void draw(Graphics2D g2) {
			
			if (spriteLoaded == true) {
				g2.drawImage(normalWall, x, y, width, height, null);
				if (isCleared==false) g2.drawImage(closeWall, x, y, width, height, null);
				if (preBoss) {
//					g2.drawImage(bossDoor, 0, y, SCREENWIDTH, SCREENHEIGHT, null);
				} else if (!doorExists) {
					g2.fill(noDoor);
				}
			} else {
			
			g2.setColor(color);
			g2.fillRect(x, y, SCREENWIDTH, SCREENHEIGHT);
			}
		}

	public void roomCleared() {
		this.isCleared = true;
	}

}

