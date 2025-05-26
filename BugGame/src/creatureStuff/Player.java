package creatureStuff;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
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
    private int blockChance = 0;
    private int lives = 1;
    private boolean isDead;
    //private String damageType = "normal";
    private int lightingChance = 0;
    private boolean triShot = false;
    private int shootCount;
    private int shootCooldown = 25;
    private int score;
    private int baseDamage=1;
    // ref to Room component for screen coordinates and dimensions
//    private Room currentRoom;
    
	private File fileIdle;
	private BufferedImage idleImage;
	private boolean spriteLoaded;
	private boolean isWalking;
	private int frameCount;
	private Image walkLeftImage;
	private Image walkRightImage;
	private File fileLeft;
	private File fileRight;
	private Room room;

    public Player(int startX, int startY, int startSpeed, int startHealth) {
        super(startX, startY, startSpeed, startHealth);
        drawWidth = RoomLogic.getTileSize();
        drawHeight = RoomLogic.getTileSize();
        width = drawWidth/2+10;
        height = drawHeight-10;
        setBulletClass(Normal.class);
        fileIdle = new File("assets/sprites/creatures/Beetle1.png");
        fileLeft = new File("assets/sprites/creatures/Beetle2.png");
        fileRight = new File("assets/sprites/creatures/Beetle3.png");
        try {
			idleImage = ImageIO.read(fileIdle);
			walkLeftImage = ImageIO.read(fileLeft);
			walkRightImage = ImageIO.read(fileRight);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
    }

    // linking player to its room for despawn bounds 
//    Removed as the player should not need to know what room but the room knows the player is in its room.
//    public void setCurrentRoom(Room room) {
//        this.currentRoom = room;
//    }

    public void addItem(Item item) {
        switch (item.getStatCat()) {
            case "health":
                health += item.getStatBoost();
                break;
            case "speed":
                speed += item.getStatBoost();
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
            case "blockChance":
                blockChance += item.getStatBoost();
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
    
    public void simpleItemEffect(String effect) {
    	if (effect.equals("small health")) {
    		
    	} else if (effect.equals("speed up")) {
    		
    	} else if (effect.equals("damage up")) {
    		
    	} else if (effect.equals("big health")) {
    		
    	}
    }
    
    @Override
    public void update() {
    	shootCount++;
    	if (health <= 0) {
    		this.isDead = true;
    	}
        // placeholder for movement, status effects, cooldowns, etc.
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
    
    public List<Bullet> getRawBulletList() {
        return bullets;
    }


    @Override
    public void draw(Graphics2D g2) {
    	
		frameCount+=1;
		
		if (spriteLoaded == true && isWalking==false) {
			//System.out.println("PLAYER DRAWN");
			
			g2.drawImage(idleImage, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
		} else if (spriteLoaded == true && isWalking==true) {
			if (frameCount < 10) {
			g2.drawImage(walkLeftImage, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount >= 10 && frameCount <= 20) {
			g2.drawImage(idleImage, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			} else if (frameCount > 20) {
			g2.drawImage(walkRightImage, this.getX() - drawWidth/2, this.getY() - drawHeight/2, drawWidth, drawHeight, null);
			if (frameCount >= 30) frameCount=0;
			} 
		} else {
			
			g2.setColor(Color.BLACK);
			g2.fillOval(getX() - idleImage.getWidth()/2, getY() - idleImage.getHeight()/2, idleImage.getWidth(), idleImage.getHeight());
		}
        //g.setColor(Color.BLACK); // draw player sprite
        //g.fillOval(x - width/2, y - height/2, width, height);
    }

    @Override
    public void shoot(double dx, double dy) {
    	int damage=baseDamage;
    	boolean crit = new RngHandler().handleCheck(critChance);
        
        if (crit) {
            damage *= 2;
        }
        
        Bullet b = null;
//        System.out.println("Calling shoot!");
        
        if (shootCount >= getEffectiveCooldown()) {
            shootCount = 0;
        }
        
        if (shootCount == 0) {
            b = createBullet(dx, dy, 5, damage);
            if (b != null && crit) {
                b.setCrit(true);
            }
            playShootSound();
            shootCount = 1;
        } else if (shootCount > shootCooldown) {
        	shootCount = 0;
        }

        if (b != null && room != null) {
            room.getPlayerBullets().add(b);
        }
    }
    
    public int getEffectiveCooldown() {
        return Math.max(2, shootCooldown - fireRate);  // Prevent zero or negative cooldown
    }


    @Override
    public void takeDamage(int amount) {
        if (new RngHandler().handleCheck(blockChance)) {
            if (room != null) {
                room.spawnText("Blocked!", getX(), getY() - 20, Color.CYAN);
            }
            return;
        }

        super.takeDamage(amount);
        if (health <= 0) {
            isDead = true;
        }
    }

    
	public int getCurrentHealth() {
		return super.health;
	}
	
	public int getMaxHealth() {
		return super.healthCap;
	}

	public void setIsWalking(boolean bool) {
		isWalking = bool;
	}
	
	public void setRoom(Room r) {
	    this.room = r;
	}
	
	public double getWidth() {
	    return this.width;
	}

	public double getHeight() {
	    return this.height;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void heal() {
		if (health < healthCap) health++;
	}
	
	public void raiseHealthCap() {
		healthCap++;
		health++;
	}
	
	public int getCritChance() { return critChance; }
	public void setCritChance(int critChance) { this.critChance = critChance; }

	public int getFireRate() { return fireRate; }
	public void setFireRate(int fireRate) { this.fireRate = fireRate; }

	public int getDodgeChance() { return blockChance; }
	public void setDodgeChance(int dodgeChance) { this.blockChance = dodgeChance; }

	public int getLives() { return lives; }
	public void setLives(int lives) { this.lives = lives; }

	public int getLightingChance() { return lightingChance; }
	public void setLightingChance(int lightingChance) { this.lightingChance = lightingChance; }

	public boolean isTriShot() { return triShot; }
	public void setTriShot(boolean triShot) { this.triShot = triShot; }

	public int getShootCooldown() { return shootCooldown; }
	public void setShootCooldown(int shootCooldown) { this.shootCooldown = shootCooldown; }

	public int getBaseDamage() { return baseDamage; }
	public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }





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
