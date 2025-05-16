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
    private static int ROOM_X, ROOM_Y;
    HashMap<Point, Room> roomLayout = new HashMap<>();

    public RoomLogic(int tileSize, int roomWidth, int roomHeight, int roomX, int roomY, JFrame frame) {
        this.level = 0;
        this.numRooms = 0;
        this.frame = frame;
        TILE_SIZE = tileSize; // square tiles, so X and Y are equal
        ROOM_WIDTH = roomWidth;
        ROOM_HEIGHT = roomHeight;
        ROOM_X = roomX;
        ROOM_Y = roomY;
        this.hero = new Player((TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2, 8, 3);
        this.generateLayout(numRooms);
        System.out.println(this.roomLayout.keySet());
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

    private void generateLayout(int numRooms) {
    	System.out.println(level);
    	this.numRooms = numRooms;
        char[][] placeholderLayout = layout0;
        ArrayList<Point> rooms = new ArrayList<>();
        Random rand = new Random();

        if (numRooms == 0) {
//        	This is initial condition generating a Layout with 0 floors
//        	will put you in the begining room that will contain the door to start
			rooms.add(new Point(0, 0));
            Room r = new Room(placeholderLayout, false, false, false, false, TILE_SIZE);
            currentRoom = r;
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
					Point temp2 = new Point(temp.x - 1, temp.y);
					if (!rooms.contains(temp2) && this.surroundingRoomsCheck(temp, temp2, rooms)) {
						rooms.add(new Point(temp.x - 1, temp.y));
					}
				} else if (randDirec == 1) {
					Point temp2 = new Point(temp.x + 1, temp.y);
					if (!rooms.contains(temp2) && this.surroundingRoomsCheck(temp, temp2, rooms)) {
						rooms.add(new Point(temp.x + 1, temp.y));
					}
				} else if (randDirec == 2) {
					Point temp2 = new Point(temp.x, temp.y - 1);
					if (!rooms.contains(temp2) && this.surroundingRoomsCheck(temp, temp2, rooms)) {
						rooms.add(new Point(temp.x, temp.y - 1));
					}
				} else if (randDirec == 3) {
					Point temp2 = new Point(temp.x, temp.y + 1);
					if (!rooms.contains(temp2) && this.surroundingRoomsCheck(temp, temp2, rooms)) {
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
		        currentRoom = r;
	        }
		}

        currentPoint = new Point(0,0);
        this.setCurrentRoom(currentPoint,(TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2);

        List<Enemy> enemyTypes = List.of(
            new ZigZag(0, 0, hero),
            new Predictive1(0, 0, hero),
            new Suicide(0, 0, hero),
            new WalkingEnemy(0, 0, hero));

        for (int i1 = 0; i1 < 5; i1++) {
            int ex = rand.nextInt(TILE_SIZE * 13 - 100) + 50;
            int ey = rand.nextInt(TILE_SIZE * 7 - 100) + 50;

            Enemy template = enemyTypes.get(rand.nextInt(enemyTypes.size()));
            Enemy enemy;
            if (template instanceof ZigZag) {
                enemy = new ZigZag(ex, ey, hero);
            } else if (template instanceof Predictive1) {
                enemy = new Predictive1(ex, ey, hero);
            } else if (template instanceof Suicide) {
                enemy = new Suicide(ex, ey, hero);
            } else {
                enemy = new WalkingEnemy(ex, ey, hero);
            }

            currentRoom.addEnemy(enemy);
        }
    }

    private boolean surroundingRoomsCheck(Point roomChoice, Point possibleLoc, ArrayList<Point> rooms) {
    	int x = possibleLoc.x;
    	int y = possibleLoc.y;
    	boolean availiable = false;
    	if (!rooms.contains(new Point(x+1,y)) || roomChoice.equals(new Point(x+1,y))) {
        	if (!rooms.contains(new Point(x-1,y)) || roomChoice.equals(new Point(x-1,y))) {
            	if (!rooms.contains(new Point(x,y+1)) || roomChoice.equals(new Point(x,y+1))) {
                	if (!rooms.contains(new Point(x,y-1)) || roomChoice.equals(new Point(x,y-1))) {
                		availiable = true;
                	}
            	}
        	}
    	}
    	return availiable;
    }
    public void updateObjects() {
        currentRoom.updateEntities();
        this.goNextFloor(currentRoom.goThroughDoor());
        this.switchRooms(currentRoom.goThroughDoor());
        currentRoom.updateEntities();
        currentRoom.updateBullets();   
    }

    public void drawScreen() {
        currentRoom.drawScreen();
//        System.out.println(currentPoint);
    }

    
    public void goNextFloor(char playerHitFloorDoor) {
    	if (Character.isUpperCase(playerHitFloorDoor)) {
    		this.level++;
        	this.generateLayout(this.numRooms+4+3*this.level);
    	}
    }
    
    public void switchRooms(char hitDoor) {
    	if (hitDoor != ' ') {
    		if (hitDoor == 'n') {
    			currentPoint = new Point(currentPoint.x,currentPoint.y+1);
    			this.setCurrentRoom(currentPoint, ROOM_WIDTH/2, ROOM_HEIGHT-TILE_SIZE);
    		} else if (hitDoor == 'e') {
    			currentPoint = new Point(currentPoint.x+1,currentPoint.y);
    			this.setCurrentRoom(currentPoint, TILE_SIZE, ROOM_HEIGHT/2);
    		} else if (hitDoor == 's') {
    			currentPoint = new Point(currentPoint.x,currentPoint.y-1);
    			this.setCurrentRoom(currentPoint, ROOM_WIDTH/2, TILE_SIZE);
    		}else if (hitDoor == 'w') {
    			currentPoint = new Point(currentPoint.x-1,currentPoint.y);
    			this.setCurrentRoom(currentPoint, ROOM_WIDTH-TILE_SIZE, ROOM_HEIGHT/2);
    		}
    	}
    }
    
    private void setCurrentRoom(Point point, int x, int y) {
    	frame.remove(currentRoom);
    	System.out.println(point);
    	currentRoom = roomLayout.get(point);
    	frame.add(currentRoom);
        currentRoom.setBounds(ROOM_X, ROOM_Y, ROOM_WIDTH, ROOM_HEIGHT);
        currentRoom.setPlayer((Player) hero);
        hero.setX(x);
        hero.setY(y);
    	currentRoom.repaint();
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
}

