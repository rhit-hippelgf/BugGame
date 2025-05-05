package roomStuff;

import java.util.ArrayList;
import java.util.Random;

public class RoomLogic {

	private int level;
	private int numRooms;
	
	public RoomLogic(int level, int numRooms) {
		this.level = level;
		this.numRooms = numRooms;
	}
	
	public RoomLogic() {
		this.level = 0;
		this.numRooms = 0;
	}
	
	private void generateLayout() {
		ArrayList<int[]> rooms = new ArrayList<>();
		Random rand = new Random();
		if (numRooms == 0) {
			rooms.add(new int[] {0,0});
		} else {
			rooms.add(new int[] {0,0});
			int n = 1;
			while (n <= numRooms) {
				int roomPick  = rand.nextInt(n);
				int randDirec = rand.nextInt(4);
//				int[] newroom = rooms.get(roomPick) + 
				
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
