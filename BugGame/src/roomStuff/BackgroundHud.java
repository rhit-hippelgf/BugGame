package roomStuff;
/**
 * Class: BackgroundHud, this class is responsible for painting everything outside of the Room
 * Binding box. It takes in the player instance to detect changes and runs a 2 second timer
 * to ensure states are proper. It generates a map layout depending on visited rooms and gives
 * a visual indicator of visited rooms, and current room.
 * 
 * @author team 1
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import creatureStuff.Player;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class BackgroundHud extends JComponent {

	private Player player;
	private int health;
	private int maxHealth;
	private int level;
	private int score;
	private boolean updateHud = false;
	private ArrayList<Point> exploredMap = new ArrayList<>();
	private Point currentRoom;
	private int mapPaintWidth = 32;
	private int mapPaintHeight = 16;
	private int sideGapX = (RoomLogic.getScreenWidth()-RoomLogic.getRoomWidth())/2;
	private int sideGapY = (RoomLogic.getScreenHeight()-RoomLogic.getRoomHeight())/2;
	private int mapPaintOX = RoomLogic.getRoomWidth() + sideGapX + mapPaintWidth/2;
	private int mapPaintOY = sideGapY/2 - mapPaintHeight/2;
	private Walls[] walls;
	private boolean loading = false;
	private int autoUpdate = 0;
	private int autoUpdateMax = 120;
	private BufferedImage heartCase;
	private BufferedImage heartL;
	private BufferedImage heartR;

	
	public BackgroundHud(Player player) {
		this.player = player;
		this.health = player.getCurrentHealth();
		this.score = player.getScore();
		this.maxHealth = player.getMaxHealth();
		this.setBounds(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());

		try {
			heartCase = ImageIO.read(new File("assets/sprites/ui/Heart.png"));
			heartL = ImageIO.read(new File("assets/sprites/ui/HeartL.png"));
			heartR = ImageIO.read(new File("assets/sprites/ui/HeartR.png"));
		} catch (IOException e) {
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		if (this.loading) {
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Calibri", Font.PLAIN, 100)); 
			g2.drawString("Loading...", RoomLogic.getScreenWidth()/2-200,RoomLogic.getScreenHeight()/2-50);
			g2.setColor(Color.BLACK);
		} else {
			Color old = g2.getBackground();
			g2.setColor(new Color(87, 66, 60));
			g2.fillRect(0, 0, RoomLogic.getScreenWidth(), sideGapY);
			g2.fillRect(0, RoomLogic.getScreenHeight()-sideGapY, RoomLogic.getScreenWidth(), sideGapY);
			
			for (int i = 0; i < walls.length; i++) {
				walls[i].draw(g2);
			}
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(4));
			g2.drawRect(sideGapX-2, sideGapY-2, RoomLogic.getRoomWidth()+3, RoomLogic.getRoomHeight()+3);
			g2.setColor(old);
			
	        
			for (int i =0; i<(Math.ceilDiv(maxHealth,2));i++) {
				g2.drawImage(heartCase, 30+60*i, 30, 60, 60, null);
			}
			for (int i = 0; i<(Math.ceilDiv(health,2));i++) {
				g2.drawImage(heartL, 30+60*i, 30, 60, 60, null);
			}
			for (int i = 0; i<(Math.floorDiv(health,2));i++) {
				g2.drawImage(heartR, 30+60*i, 30, 60, 60, null);
			}
	        
	        g2.setFont(new Font("Calibri", Font.PLAIN, 25)); 
	        g2.drawString("Level: " + level, 604, 50);
	        g2.drawString("Score: " + score, 600, 100);
	        
	        for (Point point : exploredMap) {
	        	if (!point.equals(currentRoom)) {
		        	int x = point.x;
		        	int y = point.y;
		        	g2.setColor(Color.BLUE);
		        	g2.fillRect(mapPaintOX + mapPaintWidth*x, mapPaintOY - mapPaintHeight*y, mapPaintWidth, mapPaintHeight);
		        }
	        }
	        g2.setColor(Color.RED);
	        g2.fillRect(mapPaintOX + mapPaintWidth*currentRoom.x, mapPaintOY - mapPaintHeight*currentRoom.y, mapPaintWidth, mapPaintHeight);
		}
        
        
	}
	
	public void updateRoom(Point currLoc, Walls[] walls) {
		this.currentRoom = currLoc;
		this.walls = walls;
		if (!exploredMap.contains(currLoc)) {
			exploredMap.add(currLoc);
		}
		this.repaint();
	}
	
	public void switchLoading() {
		this.loading = !this.loading;
		this.repaint();
	}
	
	public boolean getLoading() {
		return this.loading;
	}
	
	public void switchFloor() {
		level++;
		exploredMap.clear();
		this.updateRoom(new Point(0,0), walls);
		this.switchLoading();
	}
	
	public void detectChange() {
		if (this.health != player.getCurrentHealth()) {
			updateHud = true;
			health = player.getCurrentHealth();
		}
		if (this.maxHealth != player.getMaxHealth()) {
			updateHud = true;
			maxHealth = player.getMaxHealth();
		}
		if (this.score != player.getScore()) {
			updateHud = true;
			score = player.getScore();
		}
		if (autoUpdate >= autoUpdateMax) {
			updateHud = true;
			autoUpdate = 0;
		} else {
			autoUpdate++;
		}
		if (updateHud) {
			this.repaint();
			updateHud = false;
		}
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
		this.repaint();
	}
	
}
