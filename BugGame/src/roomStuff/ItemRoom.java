package roomStuff;

import creatureStuff.Creature;

public class ItemRoom extends Room {

	public ItemRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level, Creature player) {
		super(north, east, south, west, tileSize, level, player);
		FileReader pickLayout = new FileReader(north, east, south, west, false, true, false, false, level);
        layout = pickLayout.getLayout();
	}

}
