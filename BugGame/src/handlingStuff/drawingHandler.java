package handlingStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class drawingHandler extends JPanel{
	protected java.awt.image.BufferedImage image;
	protected boolean spriteLoaded;

	public void drawingHandler(File file) {
		try {
			image = ImageIO.read(file);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}
	
	public void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		if (spriteLoaded) {
			g2.drawImage(image, this.getX() - image.getWidth()/2, this.getY() - image.getHeight()/2, image.getWidth(), image.getHeight(), this);
		} else {
			
			g2.setColor(Color.BLACK);
			g2.fillOval(getX() - image.getWidth()/2, getY() - image.getHeight()/2, image.getWidth(), image.getHeight());
		}
    }
}
