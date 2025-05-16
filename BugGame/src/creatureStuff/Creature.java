package creatureStuff;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;
import projectileStuff.Bullet;
import roomStuff.RoomLogic;
import effectStuff.Effect;
import java.awt.Rectangle;


public abstract class Creature {
	protected int x, y;
	protected int width, height;
	protected int speed;
	protected int health;
	protected int healthCap;
	protected List<Bullet> bullets = new ArrayList<>();
	protected List<Effect> inherentEffects = new ArrayList<>();
	protected double effectChance = 0.0;
	
    // getting room width and height to prevent enemies and player from going out of room
    private int roomWidth = RoomLogic.getRoomWidth();
    private int roomHeight = RoomLogic.getRoomHeight();
	
	// bullet logic
	protected Class<? extends Bullet> bulletClass;

	public Creature(int startX, int startY, int startSpeed, int startHealth, int healthCap) {
		this.x = startX;
		this.y = startY;
		this.speed = startSpeed;
		this.health = startHealth;
		this.healthCap = healthCap;
	}
	
	public Rectangle getBounds() {
	    return new Rectangle(x - width / 2, y - height / 2, width, height);
	}



	public void takeDamage(int damage) {
		health -= damage;
	}

	public void move(double theta) {
//		System.out.println("x pos = " + x + " y pos = " + y);
		double xspeed = speed * Math.cos(theta);
		double yspeed = speed * Math.sin(theta);
		if (xspeed < 0 && x <= width/2) {
			xspeed = 0;
			x = width/2;
		} else if (xspeed > 0 && x + width/2 >= roomWidth) {
			xspeed = 0;
			x = roomWidth-width/2;
		}
		if (yspeed < 0 && y <= height/2) {
			yspeed = 0;
			y = height/2;
		} else if (yspeed > 0 && y + height/2 >= roomHeight) {
			yspeed = 0;
			y = roomHeight - height/2;
		}
		x += xspeed;
		y += yspeed;
	}

	// bullet access
	public List<Bullet> getBullets() {
	    return bullets;
	}

	// bullet class setter
	public void setBulletClass(Class<? extends Bullet> bulletClass) {
	    this.bulletClass = bulletClass;
	}

	// helper to create a bullet of the assigned type
	protected Bullet createBullet(double angle, int speed, int damage) {
	    try {
	        return bulletClass
	            .getConstructor(int.class, int.class, double.class, int.class, int.class, Creature.class)
	            .newInstance(x, y, angle, speed, damage, this);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}


	// required for all subclasses
	public abstract void shoot(double angle);

	public int getX() {
		return x;
	}

	public int getY() {
		return y; 
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public List<Effect> getInherentEffects() {
	    return inherentEffects;
	}

	public double getEffectChance() {
	    return effectChance;
	}

	// stack effects (duplicate items)
	public void addEffect(Effect e) {
	    for (Effect existing : inherentEffects) {
	        if (existing.getClass().equals(e.getClass())) {
	            existing.stack(e); 
	            return;
	        }
	    }
	    inherentEffects.add(e);
	}

	// cant exceed %100
	public void addEffectChance(double chance) {
	    effectChance += chance;
	    if (effectChance > 1.0) {
	        effectChance = 1.0;
	    }
	}

	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
}


	//public void addItem(Item item) {
	//	
	//}
	
	//public void shoot(Projectile type) {
	//	
	//}
