package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import projectileStuff.ZigZagBullet;
import roomStuff.RoomLogic;
import projectileStuff.Bullet;
import effectStuff.poison; // default poison

public class ZigZag extends Enemy {
    private int zigzagCounter = 0;
    private boolean poisoned = false;
    private boolean spriteLoaded;
	private int frameCount;
	private File file1;
	private File file2;
	private BufferedImage image1;
	private BufferedImage image2;


    public ZigZag(int x, int y, Creature target) {
        super(x, y, 2, 5, target);

        addEffect(new poison(3)); // built in poison
        addEffectChance(0.25); // 25% chance
        setBulletClass(ZigZagBullet.class);
        this.width = 20;
        this.height = 20;
        
        file1 = new File("assets/sprites/creatures/Moth1.png");
        file2 = new File("assets/sprites/creatures/Moth2.png");
        try {
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
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
        double offset = (zigzagCounter++ / 10 % 2 == 0) ? 0.3 : -0.3;
        super.calculateSpeeds(angle + offset);
        super.move();
        if (zigzagCounter % 50 == 0) {
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
			if (frameCount <= 15) {
			g.drawImage(image1, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
			} else if (frameCount > 15 && frameCount <= 30) {
			g.drawImage(image2, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
			if (frameCount >= 30) frameCount=0;
			}
		} else {
    	
    	g.setColor(poisoned ? Color.GREEN : Color.BLUE);
        g.fillOval(x - 10, y - 10, 20, 20);
		}
        for (var b : bullets) {
            b.draw(g);
        }
		
    }

    @Override
    public void onDeath() {
        System.out.println("ZigZag enemy has died!");
    }
}
