package roomStuff;

import creatureStuff.Creature;

public class BossRoom extends Room {

	public BossRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level, Creature player) {
		super(north, east, south, west, tileSize, level, player);
		FileReader pickLayout = new FileReader(north, east, south, west, true, false, false, false, false, level);
        layout = pickLayout.getLayout();
	}

}
