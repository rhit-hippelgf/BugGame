package Creatures;

public abstract class Creature {
	private int x, y;
	private int speed;
	private int health;
	//private int damageModifier;
	//private projectile projectileType;
	
	private Creature(int startX, int startY, double startSpeed, int startHealth) {
		this.x=startX;
		this.y=startY;
		this.speed=startSpeed;
		this.health=startHealth;
	}
	
	private void takeDamage(int damage) {
		health-=damage;
	}
	private void move(int theta) {
		x+=speed*Math.cos(theta);
		x+=speed*Math.sin(theta);
	}
}
