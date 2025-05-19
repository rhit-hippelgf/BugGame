package roomComponents;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rock extends Hole {

	private boolean spriteLoaded;
	private BufferedImage rock1;
	private BufferedImage rock2;
	private BufferedImage rock3;

	public Rock(int x1, int y1, int level) {
		super(x1, y1, level);
		super.color = Color.BLACK;
		try {
			rock1 = ImageIO.read(new File("assets/sprites/floors/dirtfloor1.png"));
			rock2 = ImageIO.read(new File("assets/sprites/floors/dirtfloor2.png"));
			rock3 = ImageIO.read(new File("assets/sprites/floors/dirtfloor3.png"));
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}

	public void draw(Graphics2D g2) {
		
		if (spriteLoaded == true) {
			if (level <= 2) {
				g2.drawImage(rock1, x1, y1, TILE, TILE, null);
			} else if (level <= 4 && level >= 3) {
				g2.drawImage(rock2, x1, y1, TILE, TILE, null);
			} else {
				g2.drawImage(rock3, x1, y1, TILE, TILE, null);
			}
		} else {
		
		Color old = g2.getBackground();
		g2.setColor(color);
		g2.fillRect(x1, y1, TILE, TILE);
		g2.setColor(old);
		}
	}
}
