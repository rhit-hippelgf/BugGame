package creatureStuff;

public class Player extends Creature{
	
	 private boolean up, down, left, right;
	 public void setUp(boolean b)    { up    = b; }
	 public void setDown(boolean b)  { down  = b; }
	 public void setLeft(boolean b)  { left  = b; }
	 public void setRight(boolean b) { right = b; }
	 
	 public void update() {
		 int speed = 4;
		 int dx = (right ? 1 : 0) - (left ? 1 : 0);
		 int dy = (down  ? 1 : 0) - (up   ? 1 : 0);
		 
		 if (dx != 0 && dy != 0) {
			 dx *= 0.707;  dy *= 0.707;
		 }
		 x += dx * speed;
		 y += dy * speed;
	 }

	public Player(int startX, int startY, int startSpeed, int startHealth) {
		super(startX, startY, startSpeed, startHealth);
		// TODO Auto-generated constructor stub
	}
}
