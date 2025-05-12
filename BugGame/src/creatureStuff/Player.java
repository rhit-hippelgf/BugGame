package creatureStuff;
import java.awt.Point;

import handlingStuff.RngHandler;
import itemStuff.Item;
import effectStuff.poison;
import projectileStuff.Bullet;
import projectileStuff.Normal;
import projectileStuff.ZigZagBullet;
import java.awt.Color;
import java.awt.Graphics;



public class Player extends Creature{
	private Point roomLoc = new Point(0,0);
	private int critChance = 0;
	private int fireRate = 1;
	private int dodgeChance = 0;
	private int lives = 1;
	private String damageType = "normal";
	private int lightingChance = 0;
	private boolean triShot = false;

	public Player(int startX, int startY, int startSpeed, int startHealth) {
		super(startX, startY, startSpeed, startHealth, startHealth);
		setBulletClass(Normal.class);
		// TODO Auto-generated constructor stub
	}
	
	public void addItem(Item item) {
		switch (item.getStatCat()) {
			case "health":
				health+=item.getStatBoost();
				break;
			case "speed":
				speed+=5;
				break;
			case "healthCap":
				healthCap+=item.getStatBoost();
				break;
			case "critChance":
				critChance+=item.getStatBoost();
				break;
			case "fireRate":
				fireRate+=item.getStatBoost();
				break;
			case "dodgeChance":
				dodgeChance+=item.getStatBoost();
				break;
			case "lives":
				lives +=item.getStatBoost();
				break;
			case "poison":
				damageType  = "poison";
				addEffect(new poison(3));
				break;
			case "lightingChance":
				lightingChance+=item.getStatBoost();
				break;
			case "triShot":
				triShot   = true;
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
	public void draw(Graphics g) {
	    g.setColor(Color.WHITE);          // player sprite
	    g.fillOval(x - 12, y - 12, 24, 24);

	    // draw the player’s bullets
	    for (Bullet b : bullets) {
	        b.draw(g);
	    }
	}
	@Override
	public void shoot(double angle) {
		int baseDamage = 1;
		if (new RngHandler().handleCheck(critChance)) {
			baseDamage *= 2;
		}

		Bullet b = createBullet(angle, 8, baseDamage);
		if (b != null) bullets.add(b);
	}
	public int getX() {
		return x;
	}	
	
	public int getY() {
		return y;
	}

	public void update() { 
	    // 1. move-update all bullets the player has fired
	    for (Bullet b : bullets) {
	        b.update();
	    }

	    // 2. clean up off-screen bullets
	    bullets.removeIf(b -> b.getX() < -20 || b.getX() > 1300
	                       || b.getY() < -20 || b.getY() > 900);
	}
	
}
