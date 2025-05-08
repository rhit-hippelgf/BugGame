package creatureStuff;
import java.awt.Point;
public class Player extends Creature{
	private Point roomLoc = new Point(0,0);

	public Player(int startX, int startY, int startSpeed, int startHealth, Point roomLoc) {
		super(startX, startY, startSpeed, startHealth);
		this.roomLoc = roomLoc;
		// TODO Auto-generated constructor stub
	}
	
	public void addItem(int itemId) {
		switch (itemId) {
		case 1:
			health+=20;
			break;
		case 2:
			speed+=5;
			break;
		case 3:
			
			default:
				
		}
		}
}
