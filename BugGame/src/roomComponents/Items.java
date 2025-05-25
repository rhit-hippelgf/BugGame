//package roomComponents;
//
//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.Random;
//
//import javax.imageio.ImageIO;
//
//public class Items extends Tile{
//	
//	private int itemNumber;
//	private boolean spriteLoaded;
//	private BufferedImage item;
//
//	public Items(int x1, int y1, int level) {
//		super(x1, y1, level);
//		super.color = Color.RED;
//		Random r = new Random();
//		itemNumber = r.nextInt(4);
//		try {
//			if (itemNumber == 0) {
//				item = ImageIO.read(new File("assets/sprites/items/Kiwi.png"));
//			} else if (itemNumber == 1) {
//				item = ImageIO.read(new File("assets/sprites/items/Choc.png"));
//			} else if (itemNumber == 2) {
//				item = ImageIO.read(new File("assets/sprites/items/Ace.png"));
//			} else {
//				item = ImageIO.read(new File("assets/sprites/items/Bread.png"));
//			}
//			spriteLoaded = true;
//		} catch (IOException e) {
//			spriteLoaded = false;
//		}
//	}
//
//	@Override
//	public void draw(Graphics2D g2) {
//		
//		if (spriteLoaded == true) {
//				g2.drawImage(item, x1, y1, TILE, TILE, null);
//		} else {
//		
//		Color old = g2.getBackground();
//		g2.setColor(color);
//		g2.fillRect(x1, y1, TILE, TILE);
//		g2.setColor(old);
//		}
//	}
//	
//	public String itemType() {
//		if (itemNumber == 0) return "small health";
//		else if (itemNumber == 1) return "speed up";
//		else if (itemNumber == 2) return "damage up";
//		else if (itemNumber == 3) return "big health";
//		else return " ";
//	}
//	
//	public Rectangle getBounds() {
//	    return new Rectangle(x1, y1, x2-x1, y2-y1);
//	}
//	
//}
