package creatureStuff;

public abstract class Creature {
	protected int x, y;
	protected int speed;
	protected int health;
	protected int healthCap;
	//private int damageModifier;
	//private projectile projectileType;
	
	public Creature(int startX, int startY, int startSpeed, int startHealth, int healthCap) {
		this.x=startX;
		this.y=startY;
		this.speed=startSpeed;
		this.health=startHealth;
		this.health=healthCap;
	}
	
	public void takeDamage(int damage) {
		health-=damage;
	}
	public void move(double theta) {
		x+=speed*Math.cos(theta);
		y+=speed*Math.sin(theta);
	}
	
	//public void addItem(Item item) {
	//	
	//}
	
	//public void shoot(Projectile type) {
	//	
	//}
}
