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

import java.awt.Dimension;
import java.awt.Color;
import creatureStuff.*; // So it can access ZigZag, Suicide, etc.

public class Room extends JComponent {

    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Player player;

    private Door north, east, south, west;
    private Controller control;
    private char[][] layout;
    private int numDoors;
    private boolean roomCleared;

    private final int TILE_SIZE;

    // construc accepts dynamic square tile size
    public Room(char[][] layout, boolean north, boolean east, boolean south, boolean west, int tileSize) {
        this.setFocusable(true);
        this.setOpaque(true);
        this.layout = layout;
        this.north = new Door(north,'n');
        this.east = new Door(east,'e');
        this.south = new Door(south,'s');
        this.west = new Door(west,'w');
        if (north) numDoors++;
        if (east) numDoors++;
        if (south) numDoors++;
        if (east) numDoors++;
        this.TILE_SIZE = tileSize;
        
//      Temporary testing line remove when adding enimies adding to walk through doors
        this.roomCleared();
    }

    public void setPlayer(Player p) {
        this.player = p;
        this.control = new Controller(this, player);
//        spawnEnemies(); // This method should be removed when room layout works
        }
    
    private void spawnEnemies() {
        if (player == null) return; // avoid null on early call

        Dimension roomSize = getRoomSize();
        int roomWidth = roomSize.width;
        int roomHeight = roomSize.height;

        Random rand = new Random();
        int numEnemies = rand.nextInt(5) + 3; // 3 to 7 enemies

        for (int i = 0; i < numEnemies; i++) {
            int x = rand.nextInt(roomWidth - TILE_SIZE);  // avoid spawning partly out of bounds
            int y = rand.nextInt(roomHeight - TILE_SIZE);

            Enemy enemy = getRandomEnemy(x, y);
            if (enemy != null) {
                enemies.add(enemy);
            }
        }
    }

//This seems like a temporary method until room can spawn grid layout
    private Enemy getRandomEnemy(int x, int y) {
        Random rand = new Random();
        int choice = rand.nextInt(3); // or however many enemy types you have

        switch (choice) {
            case 0:
                return new WalkingEnemy(x, y, player);
            case 1:
                return new ZigZag(x, y, player);
            case 2:
                return new Suicide(x, y, player);
            default:
                return null;
        }
    }


    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public ArrayList<Enemy> getEnemies() {
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
            if (enemies.size() == 0) {
            	roomCleared = true;
            	this.roomCleared();
            }
        }
        for (Enemy e : enemies) e.update();
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
        for (Bullet b : player.getBullets()) {
            b.update();
            for (Enemy e : enemies) {
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
        for (Enemy e : enemies) {
            for (Bullet b : e.getBullets()) {
                b.update();
                if (b.getBounds().intersects(player.getBounds())) {
                    b.onHit(player);
                }
            }
        }

        // cleanup
        player.getBullets().removeIf(Bullet::isMarkedForRemoval); 
        for (Enemy e : enemies) {
            e.getBullets().removeIf(Bullet::isMarkedForRemoval);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[0].length; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }
        north.draw(g2);
        east.draw(g2);
        south.draw(g2);
        west.draw(g2);
        
     // draw player b
        if (player != null) {
            for (Bullet b : player.getBullets()) {
                b.draw(g2);
            }
        }

        // draw enemy b
        for (Enemy e : enemies) {
            for (Bullet b : e.getBullets()) {
                b.draw(g2);
            }
        }


        if (player != null) player.draw(g2);
        for (Enemy e : enemies) e.draw(g);
        

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
    
    private void roomCleared() {
    	north.openDoor();
    	east.openDoor();
    	west.openDoor();
    	south.openDoor();
    }
}