package creatureStuff;

import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;
import projectileStuff.Bullet;
import roomStuff.Room;
import roomStuff.RoomLogic;
import effectStuff.Effect;
import java.awt.Rectangle;


public abstract class Creature {
	protected int x, y;
	protected int width, height;
	protected int speed;
	protected double xspeed, yspeed;
	protected int health;
	protected int healthCap;
	protected List<Bullet> bullets = new ArrayList<>();
	protected List<Effect> inherentEffects = new ArrayList<>();
	protected double effectChance = 0.0;
	protected Room room;
	
    // getting room width and height to prevent enemies and player from going out of room
    private int roomWidth = RoomLogic.getRoomWidth();
    private int roomHeight = RoomLogic.getRoomHeight();
	
	// bullet logic
	protected Class<? extends Bullet> bulletClass;

	public Creature(int startX, int startY, int startSpeed, int startHealth) {
		this.x = startX;
		this.y = startY;
		this.speed = startSpeed;
		this.health = startHealth;
		this.healthCap = startHealth;
	}
	
	public Rectangle getBounds() {
	    return new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public void setRoom(Room room) {
	    this.room = room;
	}

	public void takeDamage(int damage) {
		health -= damage;
	}
	

	public void calculateSpeeds(double dx, double dy) {
//		System.out.println("x pos = " + x + " y pos = " + y);
		this.xspeed = speed * dx;
		this.yspeed = speed * dy;

//		Below is code for wall collisions to prevent duplications using separate method if performance is bad keep this instead
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
//		int[] topWallx = {0,roomWidth};
//		int[] topWally = {-1,0};
//		int[] bottomWally = {roomHeight, roomHeight+1};
//		int[] rightWallx = {roomWidth,roomWidth+1};
//		int[] leftWallx = {-1,0};
//		int[] sideWally = {0,roomHeight};
//		checkValidSpeed(topWallx,topWally);
//		checkValidSpeed(rightWallx,sideWally);
//		checkValidSpeed(topWallx,bottomWally);
//		checkValidSpeed(leftWallx,sideWally);
		
	}
	
	public void move() {
		x += xspeed;
		y += yspeed;
		xspeed = 0;
		yspeed = 0;
	}
	
//	This method is to check if the creature is moving at a valid speed based on locations of rocks and holes in room
//	I don't want to implement falling into a pit and would rather treat it as an obstacle the player can't cross but bullets can
//	We can add a wing item that allows the player to fly over holes and then this method will simply not be ran
	public void checkValidSpeed(int[] xs, int[] ys) {
		if (y + height/2 -4> ys[0] && y+4 < ys[1]) {
			if (xspeed < 0 && x-width/2 <= xs[1] && x+width/2 > xs[0]) {
				xspeed = 0;
				x = width/2 + xs[1];
			} else if (xspeed > 0 && x+width/2 >= xs[0] && x-width/2 < xs[1]) {
				xspeed = 0;
				x = xs[0]- width/2;
			}
		}
		if (x+width/2-4 > xs[0] && x-width/2+4 < xs[1]) {
			if (yspeed < 0 && y <= ys[1] && y+height/2 > ys[0]) {
				yspeed = 0;
				y = ys[1];
			} else if (yspeed > 0 && y+height/2 >= ys[0] && y < ys[1]) {
				yspeed = 0;
				y = ys[0] - height/2;
			}
		}
		
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
	protected Bullet createBullet(double dx, double dy, int speed, int damage) {
	    try {
	        return bulletClass
	            .getConstructor(double.class, double.class, double.class, double.class, int.class, int.class, Creature.class)
	            .newInstance((double) x, (double) y, dx, dy, speed, damage, this);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}



	// required for all subclasses
	public abstract void shoot(double dx, double dy);

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
	
	public int getHealth() {
		return health;
	}

	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public abstract double getWidth();

	public abstract double getHeight();
}


	//public void addItem(Item item) {
	//	
	//}
	
	//public void shoot(Projectile type) {
	//	
	//}
