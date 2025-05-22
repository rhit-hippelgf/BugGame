package roomComponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hole extends Tile {

	private boolean spriteLoaded;
	private BufferedImage hole1;
	private BufferedImage hole2;
	private BufferedImage hole3;
	
	public Hole(int x1, int y1, int level) {
		super(x1, y1, level);
		super.color = Color.GRAY;
		try {
			hole1 = ImageIO.read(new File("assets/sprites/objects/hole1.png"));
			hole2 = ImageIO.read(new File("assets/sprites/objects/hole2.png"));
			hole3 = ImageIO.read(new File("assets/sprites/objects/hole3.png"));
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}
	
	@Override
	public void draw(Graphics2D g2) {
		
		if (spriteLoaded == true) {
			if (level <= 2) {
				g2.drawImage(hole1, x1, y1, TILE, TILE, null);
			} else if (level <= 4 && level >= 3) {
				g2.drawImage(hole2, x1, y1, TILE, TILE, null);
			} else {
				g2.drawImage(hole3, x1, y1, TILE, TILE, null);
			}
		} else {
		
		Color old = g2.getBackground();
		g2.setColor(color);
		g2.fillRect(x1, y1, TILE, TILE);
		g2.setColor(old);
		}
	}
	
	public boolean bulletHitBoulder(int x, int y) {
		return false;
	}
}
