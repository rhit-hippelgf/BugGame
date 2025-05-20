package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;
import creatureStuff.Player;
import java.awt.Point;
import java.awt.Color;

public class BackgroundHud extends JComponent {

	private Player player;
	private int health;
	private int maxHealth;
	private int level;
	private boolean updateHud = false;
	private ArrayList<Point> exploredMap = new ArrayList<>();
	private Point currentRoom;
	private int mapPaintWidth = 32;
	private int mapPaintHeight = 16;
	private int sideGapX = (RoomLogic.getScreenWidth()-RoomLogic.getRoomWidth())/2;
	private int sideGapY = (RoomLogic.getScreenHeight()-RoomLogic.getRoomHeight())/2;
	private int mapPaintOX = RoomLogic.getRoomWidth() + sideGapX + mapPaintWidth/2;
	private int mapPaintOY = sideGapY/2 - mapPaintHeight/2;
	private boolean loading = false;

	
	public BackgroundHud(Player player) {
		this.player = player;
		this.health = player.getCurrentHealth();
		this.maxHealth = player.getMaxHealth();
		this.setBounds(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());

		
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
		if (this.loading) {
			g2.fillRect(0, 0, RoomLogic.getScreenWidth(), RoomLogic.getScreenHeight());
			g2.drawString("Loading...", 400,400);
		} else {
	        
	        g2.drawString("Current Health: " + health, 50, 50);
	        g2.drawString("Max Health: " + health, 50, 70);
	        g2.drawString("Level: " + level, 500, 70);
	        
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
	
	public void updateRoom(Point currLoc) {
		this.currentRoom = currLoc;
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
		this.updateRoom(new Point(0,0));
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
		if (updateHud) {
			this.repaint();
			updateHud = false;
		}
	}
	
}
