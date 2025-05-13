package roomStuff;

import java.util.ArrayList;
import java.util.Random;

import creatureStuff.Creature;
import creatureStuff.Player;

import java.awt.Point;
import java.util.*;

public class RoomLogic {

	private int level;
	private int numRooms;
	private Room currentRoom;
	private Creature hero;
	HashMap<Point, Room> roomLayout = new HashMap<>();

	public RoomLogic(int level, int numRooms) {
		this.level = level;
		this.numRooms = numRooms;
		this.generateLayout();
		System.out.println(this.roomLayout.keySet());
	}

	public RoomLogic() {
		this.level = 0;
		this.numRooms = 0;
		this.hero = new Player(1920/2,1080/2,10,3);
		this.generateLayout();
		System.out.println(this.roomLayout);
	}

	private void generateLayout() {
		ArrayList<Point> rooms = new ArrayList<>();
		Random rand = new Random();

		if (numRooms == 0) {
			rooms.add(new Point(0, 0));
		} else {
			rooms.add(new Point(0, 0));
			while (rooms.size() <= numRooms) {
				int roomPick = rand.nextInt(rooms.size());
				Point temp = rooms.get(roomPick);
				int randDirec = rand.nextInt(4);

				if (randDirec == 0) {
					if (!rooms.contains(new Point(temp.x - 1, temp.y))) {
						rooms.add(new Point(temp.x - 1, temp.y));
					}
				} else if (randDirec == 1) {
					if (!rooms.contains(new Point(temp.x + 1, temp.y))) {
						rooms.add(new Point(temp.x + 1, temp.y));
					}
				} else if (randDirec == 2) {
					if (!rooms.contains(new Point(temp.x, temp.y - 1))) {
						rooms.add(new Point(temp.x, temp.y - 1));
					}
				} else if (randDirec == 3) {
					if (!rooms.contains(new Point(temp.x, temp.y + 1))) {
						rooms.add(new Point(temp.x, temp.y + 1));
					}
				}
			}
		}

		int i = 0;
		while (i < rooms.size()) {
			Point temp = rooms.get(i);
			boolean north = false;
			boolean east = false;
			boolean south = false;
			boolean west = false;

			for (int j = 0; j < rooms.size(); j++) {
				if ((rooms.get(j).y == temp.y + 1) && (rooms.get(j).x == temp.x)) {
					north = true;
				} else if ((rooms.get(j).y == temp.y) && rooms.get(j).x == temp.x + 1) {
					east = true;
				} else if ((rooms.get(j).y == temp.y - 1) && rooms.get(j).x == temp.x) {
					south = true;
				} else if ((rooms.get(j).y == temp.y) && rooms.get(j).x == temp.x - 1) {
					west = true;
				}
			}

			roomLayout.put(temp, new Room(north, east, south, west));
			i++;
		}
		currentRoom = roomLayout.get(new Point(0,0));
		currentRoom.setPlayer((Player) hero);
	}

	public void updateObjects() {
		currentRoom.updateEntities();
	}

	public void drawScreen() {
		currentRoom.drawScreen();
	}
	
	public Room getCurrentRoom() {
		return currentRoom;
	}
}

				
			
				
