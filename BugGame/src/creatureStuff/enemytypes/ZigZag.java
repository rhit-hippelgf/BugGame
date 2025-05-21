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
import roomStuff.Room;
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

    private Room room; // reference to Room

    public ZigZag(int x, int y, Creature target, Room room) {
        super(x, y, 2, 5, target);
        this.room = room;

        addEffect(new poison(3)); // built in poison
        addEffectChance(0.25); // 25% chance
        setBulletClass(ZigZagBullet.class);
        this.drawWidth = RoomLogic.getTileSize();
        this.drawHeight = RoomLogic.getTileSize();
        this.width = drawWidth-20;
        this.height = drawHeight-20;
        
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
        return new Rectangle(x-width/2, y-height/2, width, height);
    }

    @Override
    public void update() {
        if (health <= 0) {
            onDeath();
            return;
        }

        // Use getters to avoid coordinate sync issues
        double tx = target.getX();
        double ty = target.getY();
        double ex = this.getX();
        double ey = this.getY();
        
        double xTemp = tx-ex;
        double yTemp = ty-ey;
        double mag = Math.sqrt(xTemp*xTemp + yTemp*yTemp);
        

//        double angle = Math.atan2(ty - ey, tx - ex);
        double offset = (zigzagCounter++ / 10 % 2 == 0) ? 0.3 : -0.3;

        // Debug output
//        System.out.println("[ZigZag Update]");
//        System.out.println("Target: (" + tx + ", " + ty + ")");
//        System.out.println("Enemy:  (" + ex + ", " + ey + ")");
//        System.out.println("Angle:  " + angle);
//        System.out.println("Offset: " + offset);
//        System.out.println("Final Angle Used To Move: " + (angle + offset));

        super.calculateSpeeds(xTemp/mag,yTemp/mag);
//        super.move();

        if (zigzagCounter % 50 == 0) {
//            System.out.println(">>> SHOOTING at angle: ");
            shoot(xTemp/mag, yTemp/mag);  
        }

        // bullets are now updated in Room, not here
    }


    @Override
    public void shoot(double dx, double dy) {
        Bullet b = createBullet(dx, dy, 2, 1);
        if (b != null && room != null) {
            room.getEnemyBullets().add(b);
        }
    }

    @Override
    public void draw(Graphics2D g) {
    	int scaleWidth = RoomLogic.getTileSize();
		frameCount += 1;
		if (spriteLoaded == true) {
			if (frameCount <= 15) {
				g.drawImage(image1, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight, null);
			} else if (frameCount > 15 && frameCount <= 30) {
				g.drawImage(image2, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight, null);
				if (frameCount >= 30) frameCount = 0;
			}
		} else {
			g.setColor(poisoned ? Color.GREEN : Color.BLUE);
			g.fillOval(x - 10, y - 10, 20, 20);
		}
		g.drawRect(x-width/2, y-height/2, width, height);

        // bullets are now drawn in Room, not here
    }

    @Override
    public void onDeath() {
        System.out.println("ZigZag enemy has died!");
    }

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
