package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;
import creatureStuff.Player;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.Color;

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

	
	public BackgroundHud(Player player) {
		this.player = player;
		this.health = player.getCurrentHealth();
		this.score = player.getScore();
		this.maxHealth = player.getMaxHealth();
		this.setBounds(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());

		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		if (this.loading) {
			g2.fillRect(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());
			g2.setColor(Color.WHITE);
			g2.drawString("Loading...", 400,400);
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
			g2.drawRect(sideGapX-1, sideGapY-1, RoomLogic.getRoomWidth()+2, RoomLogic.getRoomHeight()+2);
			g2.setColor(old);
			
	        
	        g2.drawString("Current Health: " + health, 50, 50);
	        g2.drawString("Max Health: " + maxHealth, 50, 70);
	        g2.drawString("Level: " + level, 500, 50);
	        g2.drawString("Score: " + score, 500, 70);
	        
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
