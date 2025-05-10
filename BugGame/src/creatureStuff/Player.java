package creatureStuff;
import java.awt.Point;
import java.util.Random;
import itemStuff.Item;
public class Player extends Creature{
	private Point roomLoc = new Point(0,0);
	private int critChance = 0;
	private int fireRate = 1;
	private int dodgeChance = 0;
	private int lives = 1;
	private String damageType = "normal";
	private int lightingChance = 0;
	private boolean triShot = false;

	public Player(int startX, int startY, int startSpeed, int startHealth, Point roomLoc) {
		super(startX, startY, startSpeed, startHealth, startHealth);
		this.roomLoc = roomLoc;
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
				break;
			case "lightingChance":
				lightingChance+=item.getStatBoost();
				break;
			case "triShot":
				triShot   = true;
				break;
				
			default:
				break;
			}
		}
	
	public void shoot() {
		Random r = new Random();
		int critCheck = r.nextInt(100);
		boolean isCrit;
		if (critChance>= critCheck) isCrit = true;
		
	}
	
}
