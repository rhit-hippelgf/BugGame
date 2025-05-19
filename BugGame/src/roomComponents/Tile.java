package roomComponents;
/**
 * This class will be the base of all floor components, boulder will extend tile but load a different image and have collision logic
 * The painting logic will all be the same so that will be done in this tile class whose only method is to draw itself, in this case a
 * white square and boulder and hole will be different color squares until we get sprites implemented. x1,x2,y1,y2 represent the binding box
 * for easier access in collisions t
 */
import roomStuff.RoomLogic;

public class Tile {

	private int x1,x2;
	private int y1,y2;
	protected static int TILE = RoomLogic.getTileSize();
	
//	When constructing use the top left of the grid so a tile or boulder, or hole in top left represents a position of x=0 and y=0
	public Tile(int x1, int y1) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x1+TILE;
		this.y2 = y1+TILE;
//		for different tile styles can run random to randomly assign one of the n different tile styles applies to boulder and hole
	}
	
	public void draw() {
		
	}
	
}
