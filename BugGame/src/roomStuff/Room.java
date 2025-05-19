package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

import controllerStuff.Controller;
import creatureStuff.Enemy;
import creatureStuff.Player;
import creatureStuff.enemytypes.Suicide;
import creatureStuff.enemytypes.WalkingEnemy;
import creatureStuff.enemytypes.ZigZag;
import projectileStuff.Bullet;
import roomComponents.Hole;
import roomComponents.Rock;
import roomComponents.Tile;

import java.awt.Dimension;
import java.awt.Color;
import creatureStuff.*; // So it can access ZigZag, Suicide, etc.

public class Room extends JComponent {

	private static final char TILE = '.';
	private static final char SUICIDE = 's';
	private static final char ROCK = 'r';
	private static final char HOLE = 'o';
	private static final char ZIGZIG = 'z';
	private static final char STANDARD_ENEMY = 'e';
	private static final char LEGENDARY_ITEM = 'L';
	private static final char EPIC_ITEM = 'E';
	private static final char RARE_ITEM = 'R';
	private static final char COMMON_ITEM = 'C';
	private static final char SMALL_HEALTH = 'h';
	private static final char BIG_HEALTH = 'H';
	
	private Tile[][] gridTiles = new Tile[7][13];
	private ArrayList<Hole> Obsticles = new ArrayList<>();
	private ArrayList<Rock> BulletObsticles = new ArrayList<>(); // could use previous list but don't know how to differentiate between hole and rock
    private ArrayList<Creature> enemies = new ArrayList<>();
    private Creature player;

    private Door north, east, south, west;
    private Controller control;
    private char[][] layout;
    private boolean roomCleared;

    private final int TILE_SIZE;
    private int level;
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> enemyBullets = new ArrayList<>();

    // construc accepts dynamic square tile size
    public Room(boolean north, boolean east, boolean south, boolean west, int tileSize, int level, Creature player) {
        this.setFocusable(true);
        this.setOpaque(true);
        this.north = new Door(north,'n');
        this.east = new Door(east,'e');
        this.south = new Door(south,'s');
        this.west = new Door(west,'w');
        FileReader pickLayout = new FileReader(north, east, south, west, false, false, false, false, level);
        layout = pickLayout.getLayout();
        this.TILE_SIZE = tileSize;
        this.level = level;
        this.player = player;
        this.generateLayout();
        
//      Temporary testing line remove when adding enimies adding to walk through doors
        this.roomCleared();
    }

    public void setPlayer(Player p) {
        this.player = p;
        this.control = new Controller(this, (Player) player);
        this.player.setRoom(this);
        this.addMouseListener(control);
        control.setRoom(this);
        }
    
    public void generateLayout() {
    	for (int row = 0; row < gridTiles.length; row++) {
    		for (int col = 0; col < gridTiles[0].length; col++) {
    			char i = layout[row][col];
    			int y = row*TILE_SIZE;
    			int x = col*TILE_SIZE;
    			if (i == '.') {
    				gridTiles[row][col] = new Tile(x,y,level);
    			} else if (i == 'r') {
    				Tile rock = new Rock(x,y,level);
    				gridTiles[row][col] = rock;
    				Obsticles.add((Hole)rock);
    				BulletObsticles.add((Rock)rock);
    			} else if (i == 'o') {
    				Tile hole = new Hole(x,y,level);
    				gridTiles[row][col] = hole;
    				Obsticles.add((Hole)hole);
    			} else if (i == 's') {
    				gridTiles[row][col] = new Tile(x,y,level);
    				Creature e = new Suicide(x,y,player);
    				this.addEnemy(e);
    			} else if (i == 'z') {
    				gridTiles[row][col] = new Tile(x,y,level);
    				Creature e = new ZigZag(x,y,player);
    				this.addEnemy(e);
    			} else if (i == 'e') {
    				gridTiles[row][col] = new Tile(x,y,level);
    				Creature e = new WalkingEnemy(x,y,player);
    				this.addEnemy(e);
    			} else {
    				gridTiles[row][col] = new Tile(x,y,level);
    			}
    		}
    	}
    }
    
//    private void spawnEnemies() {
//        if (player == null) return; // avoid null on early call
//
//        Dimension roomSize = getRoomSize();
//        int roomWidth = roomSize.width;
//        int roomHeight = roomSize.height;
//
//        Random rand = new Random();
//        int numEnemies = rand.nextInt(5) + 3; // 3 to 7 enemies
//
//        for (int i = 0; i < numEnemies; i++) {
//            int x = rand.nextInt(roomWidth - TILE_SIZE);  // avoid spawning partly out of bounds
//            int y = rand.nextInt(roomHeight - TILE_SIZE);
//
//            Enemy enemy = getRandomEnemy(x, y);
//            if (enemy != null) {
//                enemies.add(enemy);
//            }
//        }
//    }
//
////This seems like a temporary method until room can spawn grid layout
//    private Enemy getRandomEnemy(int x, int y) {
//        Random rand = new Random();
//        int choice = rand.nextInt(3); // or however many enemy types you have
//
//        switch (choice) {
//            case 0:
//                return new WalkingEnemy(x, y, player);
//            case 1:
//                return new ZigZag(x, y, player);
//            case 2:
//                return new Suicide(x, y, player);
//            default:
//                return null;
//        }
//    }


    public void addEnemy(Creature e) {
        enemies.add(e);
    }

    public ArrayList<Creature> getEnemies() {
        return enemies;
    }

//  This method should be unneeded as RoomLogic has a static getter to get the room width and height
    public Dimension getRoomSize() {
        int width = layout[0].length * TILE_SIZE;
        int height = layout.length * TILE_SIZE;
        return new Dimension(width, height);
    } 

    public void updateEntities() {
        if (player != null) {
            player.update();
            control.moveIfPress();
//          Need line looping through an arrayList of Holes (Holes, and Rocks) to grap there xs and ys and run
//          checkValidSpeed in creature class this is where we update player then below same for enemies
            player.move();
            if (enemies.size() == 0) {
            	roomCleared = true;
            	this.roomCleared();
            }
        }
        for (Creature e : enemies) e.update();
        enemies.removeIf(e -> e.getHealth() <= 0);
        this.handleCollision();
        this.updateBullets();
    }
    
    private void handleCollision() {
//    	Starting handle collision method this will contain all collision logic
    	this.goThroughDoor();
    }

    private void updateBullets() {
        List<Bullet> playerToRemove = new ArrayList<>();

        // Player Bullet Collision Update
        for (Bullet b : playerBullets) {
            b.update();
            for (Creature e : enemies) {
                System.out.println("Bullet bounds: " + b.getBounds());
                System.out.println("Enemy bounds: " + e.getBounds());
                if (b.getBounds().intersects(e.getBounds())) {
                    System.out.println("COLLISION DETECTED!");
                    b.onHit(e);
                    playerToRemove.add(b);
                    break;
                }
            }
        }

        // Enemy Bullet Collision Update
        for (Creature e : enemies) {
            for (Bullet b : e.getBullets()) {
                b.update();
                if (b.getBounds().intersects(player.getBounds())) {
                    b.onHit(player);
                }
            }
        }

        // cleanup
        playerBullets.removeIf(Bullet::isMarkedForRemoval);
        for (Creature e : enemies) {
            e.getBullets().removeIf(Bullet::isMarkedForRemoval);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[0].length; col++) {
            	gridTiles[row][col].draw(g2);
            }
        }
        
        north.draw(g2);
        east.draw(g2);
        south.draw(g2);
        west.draw(g2);
        
     // draw player b
        if (player != null) {
        	for (Bullet b : playerBullets) {
                b.draw(g2);
            }
        }

        // draw enemy b
        for (Creature e : enemies) {
            for (Bullet b : e.getBullets()) {
                b.draw(g2);
            }
        }


        if (player != null) player.draw(g2);
        for (Creature e : enemies) e.draw(g2);
        

    }

    public void drawScreen() {
        this.repaint();
    }
    
    public void setTutorialDoors() {
    	
    }
    
    public void setFloorDoor() {
    	Random rand = new Random();
    	char[] directions = {'n','e','s','w'};
    	boolean pickedFloorDoor = false;
    	while (!pickedFloorDoor) {
    		char dir = directions[rand.nextInt(4)];
    		if (dir == 'n' && !this.north.getVisable()) {
    			this.north = new FloorDoor(true,'n');
    			pickedFloorDoor = true;
    		} else if (dir == 'e' && !this.east.getVisable()) {
    			this.east = new FloorDoor(true,'e');
    			pickedFloorDoor = true;
    		} else if (dir == 's' && !this.south.getVisable()) {
    			this.south = new FloorDoor(true,'s');
    			pickedFloorDoor = true;
    		} else if (dir == 'w' && !this.west.getVisable()) {
    			this.west = new FloorDoor(true,'w');
    			pickedFloorDoor = true;
    		}
    	}
    }
    
    public char goThroughDoor() {
    	char dir = ' ';
    	if (north.playerHitDoor(player.getX(), player.getY()) != ' ') {
    		dir = north.playerHitDoor(player.getX(), player.getY());
    	}
    	if (west.playerHitDoor(player.getX(), player.getY()) != ' ') {
    		dir = west.playerHitDoor(player.getX(), player.getY());
    	};
    	if (south.playerHitDoor(player.getX(), player.getY()) != ' ') {
    		dir = south.playerHitDoor(player.getX(), player.getY());
    	};
    	if (east.playerHitDoor(player.getX(), player.getY()) != ' ') {
    		dir = east.playerHitDoor(player.getX(), player.getY());
    	};
    	return dir;
    }
    
    public void setDoorColor(char dir, Color color) {
    	if (dir == 'n') {north.setColor(color);}
    	if (dir == 'e') {east.setColor(color);}
    	if (dir == 's') {south.setColor(color);}
    	if (dir == 'w') {west.setColor(color);}
    }
    
    private void roomCleared() {
    	north.openDoor();
    	east.openDoor();
    	west.openDoor();
    	south.openDoor();
    }
    
    public int getTileSize(){
    	return TILE_SIZE;
    }
    
    public List<Bullet> getPlayerBullets() { return playerBullets; }
    public List<Bullet> getEnemyBullets() { return enemyBullets; }
}