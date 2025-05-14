package roomStuff;

import java.util.*;

import javax.swing.JFrame;

import java.awt.Point;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import creatureStuff.Player;
import creatureStuff.enemytypes.*;

public class RoomLogic {

    private int level;
    private int numRooms;
    private Room currentRoom;
    private Point currentPoint;
    private Creature hero;
    private JFrame frame;
    private static int TILE_SIZE;
    private static int ROOM_WIDTH;
    private static int ROOM_HEIGHT;
    HashMap<Point, Room> roomLayout = new HashMap<>();

    public RoomLogic(int tileSize, int roomWidth, int roomHeight, JFrame frame) {
        this.level = 0;
        this.numRooms = 2;
        this.frame = frame;
        this.TILE_SIZE = tileSize; // square tiles, so X and Y are equal
        this.ROOM_WIDTH = roomWidth;
        this.ROOM_HEIGHT = roomHeight;
        this.hero = new Player((TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2, 10, 3);
        this.generateLayout(numRooms, level);
        System.out.println(this.roomLayout);
    }

    char[][] layout0 = {
            {'T','T','T','T','T','T','T','T','T','T','T','T','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','.','Z','.','F','.','B','.','.','.','S','.','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','.','.','B','.','.','.','.','Z','.','.','.','T'},
            {'T','.','.','.','.','.','.','.','.','.','.','.','T'},
            {'T','T','T','T','T','T','T','T','T','T','T','T','T'}
    };

    private void generateLayout(int numRooms, int level) {
    	System.out.println(level);
    	this.level = level;
    	this.numRooms = numRooms;
        char[][] placeholderLayout = layout0;
        ArrayList<Point> rooms = new ArrayList<>();
        Random rand = new Random();

        if (numRooms == 0) {
			rooms.add(new Point(0, 0));
            Room r = new Room(placeholderLayout, true, true, true, true, TILE_SIZE);
            r.setPlayer((Player) hero);
            roomLayout.put(rooms.get(0), r);
            r.setFloorDoor();
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

	        int i = 0;
	        while (i < rooms.size()) {
	            Point temp = rooms.get(i);
	            boolean north = false, east = false, south = false, west = false;
	
	            for (Point p : rooms) {
	                if (p.y == temp.y + 1 && p.x == temp.x) north = true;
	                else if (p.y == temp.y && p.x == temp.x + 1) east = true;
	                else if (p.y == temp.y - 1 && p.x == temp.x) south = true;
	                else if (p.y == temp.y && p.x == temp.x - 1) west = true;
	            }
	
	            Room r = new Room(placeholderLayout, north, east, south, west, TILE_SIZE);
	            roomLayout.put(temp, r);
	            i++;
	        }
		}

        currentPoint = new Point(0,0);
        currentRoom = roomLayout.get(currentPoint);
        this.setCurrentRoom(currentPoint);

//        List<Enemy> enemyTypes = List.of(
//            new ZigZag(0, 0, hero),
//            new Predictive1(0, 0, hero),
//            new Suicide(0, 0, hero),
//            new WalkingEnemy(0, 0, hero));
//
//        for (int i1 = 0; i1 < 5; i1++) {
//            int ex = rand.nextInt(tileSize * 13 - 100) + 50;
//            int ey = rand.nextInt(tileSize * 7 - 100) + 50;
//
//            Enemy template = enemyTypes.get(rand.nextInt(enemyTypes.size()));
//            Enemy enemy;
//            if (template instanceof ZigZag) {
//                enemy = new ZigZag(ex, ey, hero);
//            } else if (template instanceof Predictive1) {
//                enemy = new Predictive1(ex, ey, hero);
//            } else if (template instanceof Suicide) {
//                enemy = new Suicide(ex, ey, hero);
//            } else {
//                enemy = new WalkingEnemy(ex, ey, hero);
//            }
//
//            currentRoom.addEnemy(enemy);
//        }
    }

    public void updateObjects() {
        currentRoom.updateEntities();
//        this.goNextFloor(currentRoom.goThroughDoor());
//        this.switchRooms(currentRoom.goThroughDoor());
    }

    public void drawScreen() {
        currentRoom.drawScreen();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    public static int getTileSize() {
    	return TILE_SIZE;
	}
    
    public static int getRoomWidth() {
    	return ROOM_WIDTH;
    }
    
    public static int getRoomHeight() {
    	return ROOM_HEIGHT;
    }
    
//    public void goNextFloor(char playerHitFloorDoor) {
//    	if (Character.isUpperCase(playerHitFloorDoor)) {
//    		System.out.println("check 1");
//        	this.generateLayout(this.numRooms+4, this.level+1);
//        	hero.setX(200);
//        	hero.setY(200);
//    	}
//    }
//    
//    public void switchRooms(char hitDoor) {
//    	if (hitDoor != ' ') {
//    		if (hitDoor == 'n') {
//    			currentPoint = new Point(currentPoint.x,currentPoint.y+1);
//    			this.setCurrentRoom(currentPoint);
//    		} else if (hitDoor == 'e') {
//    			currentPoint = new Point(currentPoint.x+1,currentPoint.y);
//    			this.setCurrentRoom(currentPoint);
//    		} else if (hitDoor == 's') {
//    			currentPoint = new Point(currentPoint.x,currentPoint.y-1);
//    			this.setCurrentRoom(currentPoint);
//    		}else if (hitDoor == 'w') {
//    			currentPoint = new Point(currentPoint.x-1,currentPoint.y);
//    			this.setCurrentRoom(currentPoint);
//    		}
//    	}
//    }
    
    private void setCurrentRoom(Point point) {
    	frame.remove(currentRoom);
    	currentRoom = roomLayout.get(point);
        currentRoom.setPlayer((Player) hero);
    	currentRoom.repaint();
    }
}

