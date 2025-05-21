package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import roomStuff.RoomLogic;
import roomStuff.Room;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Suicide extends Enemy {
    private int detonationRange = 30;
    private boolean detonated = false;
    private int explosionX = 0;
    private int explosionY = 0;
	private boolean spriteLoaded;
	private int frameCount;
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	private File file5;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	private BufferedImage image5;

	private Room room; // reference to room

    public Suicide(int x, int y, Creature target, Room room) {
        super(x, y, 3, 5, target);
        this.drawWidth = RoomLogic.getTileSize();
        this.drawHeight = RoomLogic.getTileSize();
        this.width = drawWidth - 20;
        this.height = drawHeight - 40;
        this.room = room;

        file1 = new File("assets/sprites/creatures/BBeetle1.png");
        file2 = new File("assets/sprites/creatures/BBeetle2.png");
        file3 = new File("assets/sprites/creatures/BBeetle3.png");
        file4 = new File("assets/sprites/creatures/BBeetle4.png");
        file5 = new File("assets/sprites/creatures/BBeetle5.png");
        
        try {
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
			image3 = ImageIO.read(file3);
			image4 = ImageIO.read(file4);
			image5 = ImageIO.read(file5);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - width/2, y - height/2, width, height);
    }

    @Override
    public void update() {
        if (health <= 0) {
            onDeath();
            return;
        }

        if (detonated) return; 

        double xDist = target.getX() - x;
        double yDist = target.getY() - y;
        double dist = Math.hypot(xDist, yDist);

        if (dist <= detonationRange) {
            target.takeDamage(10);
            explosionX = x;
            explosionY = y;
            this.health = 0;       // suicide
            this.detonated = true; // explosion effect
        } else {

            super.calculateSpeeds(xDist/dist,yDist/dist);
//            super.move();
        }
    }

    @Override
    public void draw(Graphics2D g) {
//    	int scaleWidth = RoomLogic.getTileSize();
		frameCount+=1;
        if (detonated) {
            g.setColor(new Color(255, 0, 0, 100)); // see through red
            g.fillOval(explosionX - detonationRange, explosionY - detonationRange, 
                       detonationRange * 2, detonationRange * 2);
        }
		if (spriteLoaded == true) {
			if (frameCount <= 10) {
			g.drawImage(image1, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount > 10 && frameCount <= 20) {
			g.drawImage(image2, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount > 20 && frameCount <= 30) {
			g.drawImage(image3, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount > 30 && frameCount <= 40) {
			g.drawImage(image4, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount > 40 && frameCount <= 50) {
				g.drawImage(image5, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
				if (frameCount >= 50) frameCount=0;
			}
		} else {
        
        g.setColor(Color.RED);
        g.fillOval(x - 10, y - 10, 20, 20); // enemy body
		}
		g.drawRect(x-width/2, y-height/2, width, height);
    }

    @Override
    public void shoot(double dx, double dy) {
        // doesnt shoot
    }

    @Override
    public void onDeath() {
        System.out.println("Suicide enemy has detonated and died!");
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
