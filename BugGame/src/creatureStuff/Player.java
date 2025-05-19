package creatureStuff;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.imageio.ImageIO;

import java.util.ArrayList;

import handlingStuff.RngHandler;
import itemStuff.Item;
import effectStuff.poison;
import projectileStuff.Bullet;
import projectileStuff.Normal;
import projectileStuff.ZigZagBullet;
import roomStuff.Room; // needed to track room position/dimensions
import roomStuff.RoomLogic;

public class Player extends Creature {
    private int critChance = 0;
    private int fireRate = 1;
    private int dodgeChance = 0;
    private int lives = 1;
    //private String damageType = "normal";
    private int lightingChance = 0;
    private boolean triShot = false;

    // ref to Room component for screen coordinates and dimensions
    private Room currentRoom;
	private File file;
	private BufferedImage image;
	private boolean spriteLoaded;

    public Player(int startX, int startY, int startSpeed, int startHealth) {
        super(startX, startY, startSpeed, startHealth);
        width = RoomLogic.getTileSize()/2;
        height = RoomLogic.getTileSize();
        setBulletClass(Normal.class);
        file = new File("assets/sprites/creatures/Beetle1.png");
        try {
			image = ImageIO.read(file);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
        System.out.println(spriteLoaded);
    }

    // linking player to its room for despawn bounds
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void addItem(Item item) {
        switch (item.getStatCat()) {
            case "health":
                health += item.getStatBoost();
                break;
            case "speed":
                speed += 5;
                break;
            case "healthCap":
                healthCap += item.getStatBoost();
                break;
            case "critChance":
                critChance += item.getStatBoost();
                break;
            case "fireRate":
                fireRate += item.getStatBoost();
                break;
            case "dodgeChance":
                dodgeChance += item.getStatBoost();
                break;
            case "lives":
                lives += item.getStatBoost();
                break;
            case "poison":
                //damageType = "poison";
                addEffect(new poison(3));
                break;
            case "lightingChance":
                lightingChance += item.getStatBoost();
                break;
            case "triShot":
                triShot = true;
                break;
            case "normalBullet":
                setBulletClass(Normal.class);
                break;
            case "zigzagBullet":
                setBulletClass(ZigZagBullet.class);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void update() {
        // Placeholder for movement, status effects, cooldowns, etc.
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
    
    public List<Bullet> getRawBulletList() {
        return bullets;
    }



    @Override
    public void draw(Graphics g) {
    	super.draw(g);
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		if (spriteLoaded == true) {
			//System.out.println("PLAYER DRAWN");
			int scaleWidth = RoomLogic.getTileSize();
			g2.drawImage(image, this.getX() - scaleWidth/2, this.getY() - scaleWidth/2, scaleWidth, scaleWidth, null);
		} else {
			
			g2.setColor(Color.BLACK);
			g2.fillOval(getX() - image.getWidth()/2, getY() - image.getHeight()/2, image.getWidth(), image.getHeight());
		}
    	
        //g.setColor(Color.BLACK); // draw player sprite
        //g.fillOval(x - width/2, y - height/2, width, height);
    }

    @Override
    public void shoot(double angle) {
//        System.out.println("Shooting angle: " + angle);
//        System.out.println("Current bullet count: " + bullets.size());

        int baseDamage = 1;

        if (new RngHandler().handleCheck(critChance)) {
            baseDamage *= 2;
        }

        // cerate/fire a bullet from player's center
        Bullet b = createBullet(angle, 5, baseDamage);
        if (b == null) System.out.println("Bullet creation failed!");

        if (b != null) bullets.add(b);
    }

	public int getCurrentHealth() {
		return super.health;
	}
	
	public int getMaxHealth() {
		return super.healthCap;
	}





//    @Override
//    public int getX() {
//        return x;
//    }
//
//    @Override
//    public int getY() {
//        return y;
//    }
}
