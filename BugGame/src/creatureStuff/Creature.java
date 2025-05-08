package creatureStuff;

public abstract class Creature {
	protected int x, y;
	protected int speed;
	protected int health;
	//private int damageModifier;
	//private projectile projectileType;
	
	public Creature(int startX, int startY, int startSpeed, int startHealth) {
		this.x=startX;
		this.y=startY;
		this.speed=startSpeed;
		this.health=startHealth;
	}
	
	public void takeDamage(int damage) {
		health-=damage;
	}
	public void move(int theta) {
		x+=speed*Math.cos(theta);
		y+=speed*Math.sin(theta);
	}
	
	//public void addItem(Item item) {
	//	
	//}
}
