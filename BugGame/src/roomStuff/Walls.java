package roomStuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Walls {
	protected int x;
	protected int y;
	protected Color color=Color.GRAY;
	protected static int SCALINGSIZE = RoomLogic.getTileSize();
	private BufferedImage normalWalls;
	private BufferedImage enemyWalls;
	private BufferedImage nBossDoor;
	private BufferedImage eBossDoor;
	private BufferedImage sBossDoor;
	private BufferedImage wBossDoor;
	private BufferedImage nNoDoor;
	private BufferedImage eNoDoor;
	private BufferedImage sNoDoor;
	private BufferedImage wNoDoor;
	private char nDoor;
	private char eDoor;
	private char sDoor;
	private char wDoor;
	private boolean spriteLoaded;
	private boolean isCleared;
	
	public Walls(int x, int y, char nDoor, char eDoor, char sDoor, char wDoor, boolean isCleared) {
		this.x=x;
		this.y=y;
		this.nDoor=nDoor;
		this.eDoor=eDoor;
		this.sDoor=sDoor;
		this.wDoor=wDoor;
		this.isCleared=isCleared;
		
		try {
			normalWalls = ImageIO.read(new File("assets/sprites/walls/baseWalls.png"));
			enemyWalls = ImageIO.read(new File("assets/sprites/walls/enemyWalls.png"));
			if (nDoor == 'B') {
			nBossDoor = ImageIO.read(new File("assets/sprites/walls/nBoss.png"));
			} else if (eDoor == 'B') {
			eBossDoor = ImageIO.read(new File("assets/sprites/walls/eBoss.png"));
			} else if (sDoor == 'B') {
			sBossDoor = ImageIO.read(new File("assets/sprites/walls/sBoss.png"));
			} else if (wDoor == 'B') {
			wBossDoor = ImageIO.read(new File("assets/sprites/walls/wBoss.png"));
			} else if (nDoor == 'N') {
			nNoDoor = ImageIO.read(new File("assets/sprites/walls/nNoDoor.png"));
			} else if (eDoor == 'N') {
			eNoDoor = ImageIO.read(new File("assets/sprites/walls/eNoDoor.png"));
			} else if (sDoor == 'N') {
			sNoDoor = ImageIO.read(new File("assets/sprites/walls/sNoDoor.png"));
			} else if (wDoor == 'N') {
			wNoDoor = ImageIO.read(new File("assets/sprites/walls/wNoDoor.png"));
			}
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}
	
public void draw(Graphics2D g2) {
		
		if (spriteLoaded == true) {
			g2.drawImage(normalWalls, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			if (isCleared==false) g2.drawImage(enemyWalls, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			
			if (nDoor == 'B') {
				g2.drawImage(nBossDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (eDoor == 'B') {
				g2.drawImage(eBossDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (sDoor == 'B') {
				g2.drawImage(sBossDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (wDoor == 'B') {
				g2.drawImage(wBossDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (nDoor == 'N') {
				g2.drawImage(nNoDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (eDoor == 'N') {
				g2.drawImage(eNoDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (sDoor == 'N') {
				g2.drawImage(sNoDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			} else if (wDoor == 'N') {
				g2.drawImage(wNoDoor, x, y, SCALINGSIZE*15, SCALINGSIZE*8, null);
			}
		} else {
		
		Color old = g2.getBackground();
		g2.setColor(color);
		g2.fillRect(x, y, SCALINGSIZE*15, SCALINGSIZE*8);
		g2.setColor(old);
		}
	}
}