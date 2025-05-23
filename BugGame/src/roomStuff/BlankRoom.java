package roomStuff;

import creatureStuff.Creature;

public class BlankRoom extends Room {

	public BlankRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level,
			Creature player) {
		super(north, east, south, west, tileSize, level, player);
		FileReader pickLayout = new FileReader(north, east, south, west, false, false, false, false, true, level);
        layout = pickLayout.getLayout();
	}

}
