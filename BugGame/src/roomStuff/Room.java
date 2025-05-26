package roomStuff;
/**
 * Class: Room, this class is the main component and runs each individual on the frame.
 * Room has several different subclasses for different layouts, and uses the FileReader
 * to pick its layout. It loads a grid of objects and prints the player when the specific
 * room is selected.
 * 
 * @author team 1
 */
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
import effectStuff.LightningStrike;
import itemStuff.Item;
import itemStuff.ItemDropper;
import itemStuff.Rarity;
import projectileStuff.Bullet;
import roomComponents.Hole;
//import roomComponents.Items;
import roomComponents.Rock;
import roomComponents.Tile;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import creatureStuff.*; 

public class Room extends JComponent {

	private static final char TILE = '.';
	private static final char SUICIDE = 's';
	private static final char ROCK = 'r';
	private static final char HOLE = 'o';
	private static final char ZIGZIG = 'z';
	private static final char STANDARD_ENEMY = 'e';
	private static final char ITEM = 'I';
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
    private ArrayList<Item> items = new ArrayList<>();
    private List<FloatingText> activeTexts = new ArrayList<>();
    private List<LightningStrike> lightningStrikes = new ArrayList<>();


    protected Creature player;

    protected Door north;
	private Door east;
	private Door south;
	private Door west;
    private Walls[] walls = new Walls[4];
    private Controller control;
    protected char[][] layout;
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
        walls[0] = new Walls(north, 'n', false);
        walls[1] = new Walls(east, 'e', false);
        walls[2] = new Walls(south, 's', false);
        walls[3] = new Walls(west, 'w', false);
        FileReader pickLayout = new FileReader(north, east, south, west, false, false, false, false, false, level);
        layout = pickLayout.getLayout();
        this.TILE_SIZE = tileSize;
        this.level = level;
        this.player = player;
        
//      Temporary testing line remove when adding enimies adding to walk through doors
//        this.roomCleared();
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
    			if (i == TILE) {
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
    				gridTiles[row][col] = new Tile(x, y, level);
    				Creature e = new Suicide(x+TILE_SIZE/2, y+TILE_SIZE/2, player, this,1+level,1+level*2,4+level);
    				this.addEnemy(e);
    			} else if (i == 'z') {
    				gridTiles[row][col] = new Tile(x, y, level);
    				Creature e = new ZigZag(x+TILE_SIZE/2, y+TILE_SIZE/2, player, this,2+level,level,3,1+Math.floorDiv(level, 3),0.2+0.05*level);
    				this.addEnemy(e);
    			} else if (i == 'e') {
    				gridTiles[row][col] = new Tile(x, y, level);
    				Creature e = new WalkingEnemy(x+TILE_SIZE/2, y+TILE_SIZE/2, player, this,4+level+Math.floorDiv(level, 2),1+Math.floorDiv(level, 2),5+2*level);
    				this.addEnemy(e);
    			} else if (i == 'I' || i == 'C' || i == 'R' || i == 'E' || i == 'L') {
    			    Rarity rarity = switch (i) {
    			        case 'L' -> Rarity.LEGENDARY;
    			        case 'E' -> Rarity.EPIC;
    			        case 'R' -> Rarity.RARE;
    			        case 'C', 'I' -> Rarity.COMMON;
    			        default -> Rarity.COMMON;
    			    };

    			    itemStuff.Item dropped = ItemDropper.getRandomItem(rarity, x + TILE_SIZE / 2, y + TILE_SIZE / 2);
    			    if (dropped != null) {
    			        items.add(dropped);
    			    }
    			    gridTiles[row][col] = new Tile(x, y, level);
//    				gridTiles[row][col] = new Tile(x,y,level);
    			} else {
    				gridTiles[row][col] = new Tile(x,y,level);
    			}
    		}
    	}
    }

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
    
    private void changeToTile(Tile objectToChange) {
    	for (int row = 0; row < gridTiles.length; row++) {
    		for (int col = 0; col < gridTiles[0].length; col++) {
    			if (gridTiles[row][col].equals(objectToChange)) {
    				int y = row*TILE_SIZE;
        			int x = col*TILE_SIZE;
    				gridTiles[row][col] = new Tile(x,y,level);
    			}
    		}
    	}
    }

    public void updateEntities() {
        if (player != null) {
            player.update();
            control.moveIfPress();
            player.checkWallCollision();
            for (Hole o : Obsticles) {
            	player.checkValidSpeed(o.getXs(), o.getYs());
            }
            for (int i = 0; i < items.size(); i++) {
                itemStuff.Item pickedUp = items.get(i);  
                if (pickedUp.getBounds().intersects(player.getBounds())) {
                    pickedUp.applyEffect((Player) player);
                    items.remove(i);
                    break;  // Only pick up one per frame
                }
            }
//          Need line looping through an arrayList of Holes (Holes, and Rocks) to grap there xs and ys and run
//          checkValidSpeed in creature class this is where we update player then below same for enemies
            player.move();
            if (enemies.size() == 0) {
            	roomCleared = true;
            	this.roomCleared();
            }
        }
        for (Creature e : enemies) {
        	e.update();
        	e.checkWallCollision();
        	
        	for (Hole o : Obsticles) {
        		e.checkValidSpeed(o.getXs(), o.getYs());
        	}
        	e.move();
        	if (e.getHealth() <= 0&& e instanceof Enemy enemy && !enemy.isDead()) {
        		((Enemy)e).markAsDead();
        		((Player)player).addScore(((Enemy)e).getScore());
        		if (((Enemy)e).healPlayer()) ((Player)player).heal();
        		((Enemy)e).onDeath(this);

        	}
        }
        
        enemies.removeIf(e ->
        e instanceof Enemy enemy
            ? e.getHealth() <= 0 && enemy.isDead()
            : e.getHealth() <= 0
            );
        if (enemies.isEmpty()) this.roomCleared();
        this.handleCollision();
        this.updateBullets();
        
        List<FloatingText> toRemove = new ArrayList<>();
        for (FloatingText ft : activeTexts) {
            ft.update();
            if (ft.isExpired()) {
                toRemove.add(ft);
            }
        }
        activeTexts.removeAll(toRemove);
        
        List<LightningStrike> lightningToRemove = new ArrayList<>();
        for (LightningStrike strike : lightningStrikes) {
            strike.update();
            if (strike.isExpired()) lightningToRemove.add(strike);
        }
        lightningStrikes.removeAll(lightningToRemove);

    }
    
    private void handleCollision() {
//    	starting handle collision method this will contain all collision logic
    	this.goThroughDoor();
    }

    private void updateBullets() {
        List<Bullet> playerToRemove = new ArrayList<>();
        List<Bullet> enemyToRemove = new ArrayList<>();

        // player bullet update and collision
        for (Bullet b : playerBullets) {
            b.update();
            for (Creature e : enemies) {
                if (b.getBounds().intersects(e.getBounds())) {
                    b.onHit(e);
                    playerToRemove.add(b);
                    break;
                }
            }
            for (Hole o : Obsticles) {
            	if (o.bulletHitBoulder((int) b.getX(),(int) b.getY())) {b.markForRemoval();}
            }
        }

        // enemy bullet update and collision
        for (Bullet b : enemyBullets) {
            b.update();
            if (b.getBounds().intersects(player.getBounds())) {
                b.onHit(player);
                enemyToRemove.add(b);
            }
            for (Hole o : Obsticles) {
            	if (o.bulletHitBoulder((int) b.getX(),(int) b.getY())) {b.markForRemoval();}
            }
        }

        // remove bullets marked for removal or manually added
        playerBullets.removeIf(b -> b.isMarkedForRemoval() || playerToRemove.contains(b));
        enemyBullets.removeIf(b -> b.isMarkedForRemoval() || enemyToRemove.contains(b));
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
        
        for (itemStuff.Item item : items) {
            item.draw((Graphics2D) g);  // uses draw method inside each item class
        }

        
     // draw player bullets
        for (Bullet b : playerBullets) {
        	b.draw(g2);
        }

        // draw enemy bullets
        for (Bullet b : enemyBullets) {
        	b.draw(g2);
        }


        if (player != null) player.draw(g2);
        for (Creature e : enemies) e.draw(g2);
        
        for (FloatingText ft : activeTexts) {
            ft.draw(g2);
        }
        
        for (LightningStrike strike : lightningStrikes) {
            strike.draw((Graphics2D) g);
        }


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
    			this.walls[0] = new Walls(true,'n',true);
    			pickedFloorDoor = true;
    		} else if (dir == 'e' && !this.east.getVisable()) {
    			this.east = new FloorDoor(true,'e');
    			this.walls[1] = new Walls(true,'e',true);
    			pickedFloorDoor = true;
    		} else if (dir == 's' && !this.south.getVisable()) {
    			this.south = new FloorDoor(true,'s');
    			this.walls[2] = new Walls(true,'s',true);
    			pickedFloorDoor = true;
    		} else if (dir == 'w' && !this.west.getVisable()) {
    			this.west = new FloorDoor(true,'w');
    			this.walls[3] = new Walls(true,'w',true);
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
    
    public void roomCleared() {
    	north.openDoor();
    	east.openDoor();
    	west.openDoor();
    	south.openDoor();
    	for (int i = 0; i < walls.length; i++) {
    		walls[i].roomCleared();
    	}
    }
    
    public int getTileSize(){
    	return TILE_SIZE;
    }
    
    public void setPreBossWall(char dir) {
    	if (dir == 'n') {
    		this.walls[0] = new Walls(true, dir, true);
    	} else if (dir == 'e') {
    		this.walls[1] = new Walls(true, dir, true);
    	} else if (dir == 's') {
    		this.walls[2] = new Walls(true, dir, true);
    	} else if (dir == 'w') {
    		this.walls[3] = new Walls(true, dir, true);
    	}
    }
    
    public Walls[] getWalls() {
    	return walls;
    }
    
    public List<Bullet> getPlayerBullets() { return playerBullets; }
    public List<Bullet> getEnemyBullets() { return enemyBullets; }
    
    public void spawnText(String text, int x, int y, Color color) {
        activeTexts.add(new FloatingText(text, x, y, color));
    }
    
    public void spawnLightning(int x, int y) {
        lightningStrikes.add(new LightningStrike(x, y));
    }

    public Player getPlayer() {
        return (Player) player;
    }



}