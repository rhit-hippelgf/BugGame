package projectileStuff;

import java.awt.Graphics;
import java. util.List;

public abstract class Bullet {
	protected int x, y;
	protected double dx, dy;
	protected int speed;
	protected int damage;
	protected List<Effect> effects;
	
	public Bullet(int x, int y, double angle, int speed, int damage, List<Effect> effects) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.damage = damage;
		this.effects = effects;
		
		this.dx = speed * Math.cos(angle);
		this.dy = speed * Math.sin(angle);
	}
	public void update() {
		x += dx;
		y += dy;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public List<Effects> getEffects() {
		return effects;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract void onHit();
	
	
	public static void main(String[] args) {
		System.out.println("Kill me now");
		System.out.println("Kill me now now");
	}

}
