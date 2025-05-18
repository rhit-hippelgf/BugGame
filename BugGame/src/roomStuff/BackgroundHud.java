package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.*;
import creatureStuff.Player;
import java.awt.Point;


public class BackgroundHud extends JComponent {

	private Player player;
	private int health;
	private int maxHealth;
	private boolean updateHud = false;
	private ArrayList<Point> exploredMap;
	private Point currentRoom;
	
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
        
        g2.drawString("Current Health: " + health, 50, 50);
        g2.drawString("Max Health: " + health, 50, 70);
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
