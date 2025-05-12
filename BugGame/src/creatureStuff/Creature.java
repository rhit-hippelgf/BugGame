package creatureStuff;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import projectileStuff.Bullet;
import effectStuff.Effect;

public abstract class Creature {
	protected int x, y;
	protected int speed;
	protected int health;
	protected int healthCap;
	protected List<Bullet> bullets = new ArrayList<>();
	protected List<Effect> inherentEffects = new ArrayList<>();
	protected double effectChance = 0.0;
	
	// bullet logic
	protected Class<? extends Bullet> bulletClass;

	public Creature(int startX, int startY, int startSpeed, int startHealth, int healthCap) {
		this.x = startX;
		this.y = startY;
		this.speed = startSpeed;
		this.health = startHealth;
		this.healthCap = healthCap;
	}

	public void takeDamage(int damage) {
		health -= damage;
	}

	public void move(double theta) {
		x += speed * Math.cos(theta);
		y += speed * Math.sin(theta);
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
}


	//public void addItem(Item item) {
	//	
	//}
	
	//public void shoot(Projectile type) {
	//	
	//}
