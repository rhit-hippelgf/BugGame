package roomStuff;

import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;
import java.util.*;

public class RoomLogic {

	private int level;
	private int numRooms;
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
	}
}

				
				// Consider the following, I don't think it works though. 
				// My bad for breaking room logic (if i did)
				
//				package roomStuff;
//
//				import java.awt.Point;
//				import java.util.ArrayList;
//				import java.util.HashMap;
//				import java.util.Random;
//
//				public class RoomLogic {
//
//					private int level;
//					private int numRooms;
//					private HashMap<Point, Room> roomLayout = new HashMap<>();
//
//					public RoomLogic(int level, int numRooms) {
//						this.level = level;
//						this.numRooms = numRooms;
//						this.generateLayout();
//						System.out.println(this.roomLayout.keySet());
//					}
//
//					public RoomLogic() {
//						this(0, 0);
//					}
//
//					private void generateLayout() {
//						ArrayList<Point> rooms = new ArrayList<>();
//						Random rand = new Random();
//
//						rooms.add(new Point(0, 0));  // Starting room
//
//						while (rooms.size() <= numRooms) {
//							Point temp = rooms.get(rand.nextInt(rooms.size()));
//							Point candidate = null;
//
//							switch (rand.nextInt(4)) {
//								case 0 -> candidate = new Point(temp.x - 1, temp.y);
//								case 1 -> candidate = new Point(temp.x + 1, temp.y);
//								case 2 -> candidate = new Point(temp.x, temp.y - 1);
//								case 3 -> candidate = new Point(temp.x, temp.y + 1);
//							}
//
//							if (!rooms.contains(candidate)) {
//								rooms.add(candidate);
//							}
//						}
//
//						for (Point temp : rooms) {
//							boolean north = rooms.contains(new Point(temp.x, temp.y + 1));
//							boolean east  = rooms.contains(new Point(temp.x + 1, temp.y));
//							boolean south = rooms.contains(new Point(temp.x, temp.y - 1));
//							boolean west  = rooms.contains(new Point(temp.x - 1, temp.y));
//
//							roomLayout.put(temp, new Room(north, east, south, west));
//						}
//					}
//
//					public Room getRoom(Point p) {
//						return roomLayout.get(p);
//					}
//
//					public HashMap<Point, Room> getRoomLayout() {
//						return roomLayout;
//					}
//
//					public Point getStartingRoomLocation() {
//						return new Point(0, 0);
//					}
//				}
				
