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

    public Item(int x, int y, String spritePath) {
        this.x = x;
        this.y = y;
        try {
            this.sprite = ImageIO.read(new File(spritePath));
            spriteLoaded = true;
        } catch (Exception e) {
            System.out.println("Failed to load sprite: " + spritePath);
            spriteLoaded = false;
        }
    }

    public abstract void applyEffect(Player player);
    public abstract String getStatCat();
    public abstract int getStatBoost();
    public Rarity getRarity() { return Rarity.COMMON; }

    public void draw(Graphics2D g2) {
        if (spriteLoaded) {
            g2.drawImage(sprite, x - sprite.getWidth() / 2, y - sprite.getHeight() / 2, null);
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
