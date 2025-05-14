package roomStuff;

import java.awt.Color;

public class FloorDoor extends Door{

	public FloorDoor(Boolean visable, char dir) {
		super(visable, dir);
		this.dir = Character.toUpperCase(super.dir);
		this.color = Color.red;
	}
	
	@Override
    public char playerHitDoor(int x, int y) {
		return Character.toUpperCase(super.playerHitDoor(x, y));
	}

}
