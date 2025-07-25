package roomStuff;
/**
 * Class: RoomLogic, this class controls the logic of the game state, it randomly creates
 * a hashmap of Room components with points. Each Room is its own JComponent with each room
 * containing its own components, and RoomLogic switched between rooms depending on doors. 
 * Each room has set bounds in the middle of the frame so this also creates a HUD 
 * displaying everything outside of those bounds. 
 * 
 * @author team 1
 * Restriction: only one instance will ever be created so several variables are static for static getters.
 */
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import creatureStuff.Creature;
import creatureStuff.Player;

public class RoomLogic {

    private int level = 0;
    private int numRooms;
    private Room currentRoom;
    private Point currentPoint;
    private Creature hero;
    private JFrame frame;
    private static int TILE_SIZE;
    private static int ROOM_WIDTH;
    private static int ROOM_HEIGHT;
    private static int ROOM_X, ROOM_Y;
    private static int SCREEN_WIDTH, SCREEN_HEIGHT;
    private List<Integer> scores;
    private int lastScore;
    private HighScoreManager mgr;
    private HashMap<Point, Room> roomLayout = new HashMap<>();
    private BackgroundHud hud;
	//private ArrayList<Point> shopPoints;
    private Point shopLoc;
	public musicPlayer musicPlayer = new musicPlayer();
	private Point bossPoint;

    public RoomLogic(int tileSize, int roomWidth, int roomHeight, int roomX, int roomY, JFrame frame, int screenWidth, int screenHeight) {
        this.numRooms = 0;
        lastScore = 1;
        this.frame = frame;
        TILE_SIZE = tileSize; // square tiles, so X and Y are equal
        ROOM_WIDTH = roomWidth;
        ROOM_HEIGHT = roomHeight;
        ROOM_X = roomX;
        ROOM_Y = roomY;
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        this.hero = new Player((TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2, 8, 5);
        hud = new BackgroundHud((Player) hero);
    	frame.add(hud);
        try {
			mgr = new HighScoreManager();
			scores = mgr.getHighScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
        this.generateLayout(numRooms);

        musicPlayer.playBgm();
    }


    private void generateLayout(int numRooms) {
    	this.numRooms = numRooms;
        ArrayList<Point> rooms = new ArrayList<>();
        roomLayout.clear();
        Random rand = new Random();

        if (numRooms == 0) {
//        	This is initial condition generating a Layout with 0 floors
//        	will put you in the begining room that will contain the door to start
			rooms.add(new Point(0, 0));
            Room r = new TutorialRoom(false, true, true, true, TILE_SIZE, this.level, hero);
            r.generateLayout();
            currentRoom = r;
            r.setPlayer((Player) hero);
            r.setFloorDoor();
            r.roomCleared();
            roomLayout.put(rooms.get(0), r);
            
            Room r1 = new highScoreRoom(true, false, false, false, TILE_SIZE, this.level, hero, scores, lastScore);
            r1.generateLayout();
            r1.roomCleared();
            roomLayout.put(new Point(0,-1), r1);
            
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

			shopLoc = this.setShopRoom(rooms);
			Point[] bossLoc = this.setBossRoom(rooms);
			bossPoint = bossLoc[1];
			rooms.add(bossLoc[1]);
			Point[] itemLoc = this.addItemRoom(rooms, bossLoc[1]);
			rooms.add(itemLoc[1]);
			
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
	
	            Room r;
	            if (temp.equals(bossLoc[1])) {
	            	r = new BossRoom(north, east, south, west, TILE_SIZE, this.level, hero);
	            } else if (temp.equals(shopLoc)) {
	            	r = new ShopRoom(north, east, south, west, TILE_SIZE, this.level, hero);
	            	
	            } else if (temp.equals(itemLoc[1])) {
	            	r = new ItemRoom(north, east, south, west, TILE_SIZE, this.level, hero);
	            } else if (temp.equals(new Point(0,0))) {
	            	r = new BlankRoom(north, east, south, west, TILE_SIZE, this.level, hero);
	            } else {
	            	r = new Room(north, east, south, west, TILE_SIZE, this.level, hero);
	            }
	            r.generateLayout();
	            
	            if (temp.equals(shopLoc)) {
	            	r.setDoorColor('n', Color.YELLOW);
	            	r.setDoorColor('e', Color.YELLOW);
	            	r.setDoorColor('s', Color.YELLOW);
	            	r.setDoorColor('w', Color.YELLOW);
	            }
	            
	            if (this.checkDirection(temp, shopLoc) != ' ') {
	            	char dir = this.checkDirection(temp, shopLoc);
	            	r.setDoorColor(dir, Color.YELLOW);
	            }
	            
	            if (temp.equals(itemLoc[1])) {
	            	r.setDoorColor('n', Color.GREEN);
	            	r.setDoorColor('e', Color.GREEN);
	            	r.setDoorColor('s', Color.GREEN);
	            	r.setDoorColor('w', Color.GREEN);
	            }
	            
	            if (temp.equals(bossLoc[0])) {
	            	char dir = this.checkDirection(bossLoc[0], bossLoc[1]);
	            	r.setDoorColor(dir, Color.BLUE);
	            }
	            
	            if (temp.equals(itemLoc[0])) {
	            	char dir = this.checkDirection(itemLoc[0], itemLoc[1]);
	            	r.setDoorColor(dir, Color.GREEN);
	            }
	            
	            if (temp.equals(bossLoc[1])) {
	            	r.setFloorDoor();
	            }
	            roomLayout.put(temp, r);
	            i++;
		        currentRoom = r;
	        }
		}

        currentPoint = new Point(0,0);
        this.setCurrentRoom(currentPoint,(TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2);
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
    	if (!hud.getLoading()) {
	        currentRoom.updateEntities();
	        if (((Player) hero).getIsDead()) {
	        	try {
					this.handleDeadPlayer();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        this.goNextFloor(currentRoom.goThroughDoor());
	        this.switchRooms(currentRoom.goThroughDoor());
	        hud.detectChange();
    	} else {
    		if (this.level == 0) {
    			this.generateLayout(0);
    			currentPoint = new Point(0,-1);
    	        this.setCurrentRoom(currentPoint,(TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2);
    		} else {
    		this.generateLayout(5+2*this.level);
    		}
    		hud.switchFloor();
    		if (hud.getLevel() != level) {
    			hud.setLevel(level);
    		}
    	}
 
    }

    public void drawScreen() {
        currentRoom.drawScreen();
    }
    
    public void handleDeadPlayer() throws IOException {
//    	Add method in player or new class to grab information from player and save it and a state in hud to display the previous run
    	if (mgr.addScore(((Player)hero).getScore())) {
            mgr.save();
            scores = mgr.getHighScores();
            lastScore = ((Player)hero).getScore();
    	}
        this.hero = new Player((TILE_SIZE * 13) / 2, (TILE_SIZE * 7) / 2, 8, 5);
        frame.remove(currentRoom);
        frame.remove(hud);
        hud = new BackgroundHud((Player) hero);
        frame.add(hud);
        hud.switchLoading();
        this.level = 0;
        
        
    }

    
    public void goNextFloor(char playerHitFloorDoor) {
    	if (Character.isUpperCase(playerHitFloorDoor)) {
    		((Player)hero).raiseHealthCap();
    		this.level++;
    		hud.switchLoading();
    		frame.remove(currentRoom);
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
    
    private Point setShopRoom(ArrayList<Point> points) {
    	ArrayList<Point> validPoints = new ArrayList<>();
    	for (Point point : points) {
    		if (Math.abs(point.x) >= 2 || Math.abs(point.y) >= 2) {
    			validPoints.add(point);
    		}
    	}
    	Random rand = new Random();
    	return validPoints.get(rand.nextInt(validPoints.size()));
    }
    
    private Point[] addItemRoom(ArrayList<Point> points, Point bossLoc) {
    	boolean check = false;
    	Point[] itemLoc = new Point[2];
    	while (!check) {
    		check = true;
	    	Point preItem = this.setShopRoom(points);
	    	if (preItem.equals(bossLoc)) {
	    		check = false;
	    	}
	    	itemLoc[0] = preItem;
	    	if (surroundingRoomsCheck(preItem,new Point(preItem.x,preItem.y+1),points)) {
	    		itemLoc[1] = new Point(preItem.x,preItem.y+1);	
	    	} else if (surroundingRoomsCheck(preItem,new Point(preItem.x+1,preItem.y),points)) {
	    		itemLoc[1] =  new Point(preItem.x+1,preItem.y);
	    	} else if (surroundingRoomsCheck(preItem,new Point(preItem.x-1,preItem.y),points)) {
	    		itemLoc[1] =  new Point(preItem.x-1,preItem.y);
	    	} else if (surroundingRoomsCheck(preItem,new Point(preItem.x,preItem.y-1),points)) {
	    		itemLoc[1] =  new Point(preItem.x,preItem.y-1);
	    	} else {
	    		check = false;
	    	}
	    	if (check == true) {
	    		if (itemLoc[1].equals(bossLoc)) {
	    		check = false;
	    		}
	    	}
    	}
    	return itemLoc;
    }
    
    private Point[] setBossRoom(ArrayList<Point> points) {
    	ArrayList<Point> validPoints = new ArrayList<>();
    	double maxDist = 0;
    	for (Point point : points) {
    		double dist = point.distance(new Point(0,0));
    		if (dist > maxDist) {
    			validPoints.clear();
    			validPoints.add(point);
    			maxDist = dist;
    		} else if (dist == maxDist) {
    			validPoints.add(point);
    		}
    	}
    	Random rand = new Random();
    	Point preBoss = validPoints.get(rand.nextInt(validPoints.size()));
    	Point[] bossLoc = new Point[2];
    	bossLoc[0] = preBoss;
    	if (surroundingRoomsCheck(preBoss,new Point(preBoss.x,preBoss.y+1),points)) {
    		bossLoc[1] = new Point(preBoss.x,preBoss.y+1);	
    	} else if (surroundingRoomsCheck(preBoss,new Point(preBoss.x+1,preBoss.y),points)) {
    		bossLoc[1] =  new Point(preBoss.x+1,preBoss.y);
    	} else if (surroundingRoomsCheck(preBoss,new Point(preBoss.x-1,preBoss.y),points)) {
    		bossLoc[1] =  new Point(preBoss.x-1,preBoss.y);
    	} else {
    		bossLoc[1] =  new Point(preBoss.x,preBoss.y-1);
    	}
    	return bossLoc;
    }
    
    private char checkDirection(Point preRoom, Point postRoom) {
    	if (postRoom.y - preRoom.y == 1 && postRoom.x == preRoom.x) return 'n';
    	else if (postRoom.x - preRoom.x == 1 && postRoom.y == preRoom.y) return 'e';
    	else if (postRoom.y - preRoom.y == -1 && postRoom.x == preRoom.x) return 's';
    	else if (postRoom.x - preRoom.x == -1 && postRoom.y == preRoom.y) return 'w';
    	else return ' ';
    }
    
    private void setCurrentRoom(Point point, int x, int y) {
    	frame.remove(currentRoom);
    	currentRoom = roomLayout.get(point);
    	frame.add(currentRoom);
        currentRoom.setBounds(ROOM_X, ROOM_Y, ROOM_WIDTH, ROOM_HEIGHT);
        currentRoom.setPlayer((Player) hero);
        hero.setX(x);
        hero.setY(y);
        hud.updateRoom(point, currentRoom.getWalls());
        
        musicPlayer.playBgm();
        if (shopLoc != null && point.equals(shopLoc) == true) {
        	musicPlayer.pauseBgm();
        	musicPlayer.pauseBoss();
        	musicPlayer.playShop();
        } else if (bossPoint!=null && point.equals(bossPoint) == true) {
        	musicPlayer.pauseBgm();
        	musicPlayer.pauseShop();
        	musicPlayer.playBoss();
        } else {
        	musicPlayer.pauseShop();
        	musicPlayer.pauseBoss();
        }
        
        
//    	currentRoom.repaint();
    }
    
    
    
    
    public Room getCurrentRoom() {return currentRoom;}
    
    public Point getRoomLoc() {return currentPoint;}
    
    public static int getTileSize() {return TILE_SIZE;}
    
    public static int getRoomWidth() {return ROOM_WIDTH;}
    
    public static int getRoomHeight() {return ROOM_HEIGHT;}
    
    public static int getScreenWidth() {return SCREEN_WIDTH;}
    
    public static int getScreenHeight() {return SCREEN_HEIGHT;}
}

