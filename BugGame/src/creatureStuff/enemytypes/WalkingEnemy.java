package creatureStuff.enemytypes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import projectileStuff.Normal;
import roomStuff.RoomLogic;
import projectileStuff.Bullet;

public class WalkingEnemy extends Enemy {

	private boolean spriteLoaded;
	private int frameCount;
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	
    public WalkingEnemy(int x, int y, Creature target) {
        super(x, y, 2, 10, target);
        setBulletClass(Normal.class);
        this.width = 20;
        this.height = 20;
        file1 = new File("assets/sprites/creatures/BulletAnt1.png");
        file2 = new File("assets/sprites/creatures/BulletAnt2.png");
        file3 = new File("assets/sprites/creatures/BulletAnt3.png");
        file4 = new File("assets/sprites/creatures/BulletAnt4.png");
        
        try {
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
			image3 = ImageIO.read(file3);
			image4 = ImageIO.read(file4);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {
        if (health <= 0) {
            onDeath();
            return;
        }

        double angle = Math.atan2(target.getY() - y, target.getX() - x);
        angle += (Math.random() - 0.5) * 0.4; // slight wobble
        super.calculateSpeeds(angle);
        super.move();
        // shoot occasionally
        if (Math.random() < 0.005) {
            shoot(angle);
        }

        for (var b : bullets) {
            b.update();
        }
    }

    @Override
    public void shoot(double angle) {
        Bullet b = createBullet(angle, 2, 1);
        if (b != null) bullets.add(b);
    }

    @Override
    public void draw(Graphics2D g) {
    	int scaleWidth = RoomLogic.getTileSize();
		frameCount+=1;
		if (spriteLoaded == true) {
			if (frameCount <= 10) {
			g.drawImage(image1, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
			} else if (frameCount > 10 && frameCount <= 20) {
			g.drawImage(image2, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
			} else if (frameCount > 20 && frameCount <= 30) {
			g.drawImage(image3, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
			} else if (frameCount > 30 && frameCount <= 40) {
			g.drawImage(image4, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
				if (frameCount >= 40) frameCount=0;
			}
		} else {
        g.setColor(Color.MAGENTA);
        g.fillRect(x - 10, y - 10, 20, 20); // center the enemy
		}

        for (var b : bullets) {
            b.draw(g);
        }
    }

    @Override
    public void onDeath() {
        System.out.println("WalkingEnemy has died!");
    }
}
