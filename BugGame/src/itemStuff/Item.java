package itemStuff;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import creatureStuff.Player;

public abstract class Item {
    protected int x, y;
    protected BufferedImage sprite;
    protected boolean spriteLoaded;
    protected int statBoost;

    // Constructor for items WITHOUT stat boost
    public Item(int x, int y, String spritePath) {
        this.x = x;
        this.y = y;
        this.statBoost = 0; // default
        try {
            this.sprite = ImageIO.read(new File(spritePath));
            System.out.println("Loaded sprite " + spritePath + " size: " + sprite.getWidth() + "x" + sprite.getHeight());
            spriteLoaded = true;
        } catch (Exception e) {
            System.out.println("Failed to load sprite: " + spritePath);
            spriteLoaded = false;
        }

    }

    // Constructor for items WITH stat boost
    public Item(int x, int y, String spritePath, int statBoost) {
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(new File(spritePath));
            System.out.println("Loaded sprite " + spritePath + " size: " + sprite.getWidth() + "x" + sprite.getHeight());
            spriteLoaded = true;
        } catch (Exception e) {
            System.out.println("Failed to load sprite: " + spritePath);
            spriteLoaded = false;
        }
    }

    // Subclass responsibilities
    public abstract void applyEffect(Player player);
    public abstract String getStatCat(); // e.g., "critChance"
    public abstract int getStatBoost();

    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    public void draw(Graphics2D g2) {
        int drawSize = 96; 

        if (spriteLoaded && sprite != null) {
            g2.drawImage(sprite, x - drawSize / 2, y - drawSize / 2, drawSize, drawSize, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(x - 8, y - 8, 16, 16);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x - 8, y - 8, 16, 16);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
