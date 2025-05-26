package creatureStuff;
/**
 * Abstract class: Enemy is a creature and a super class to many different specific enemies.
 * All enemies will act in this general manner but the specific implementation is done in each enemy.
 * This class exists to remove duplication of code to not deal with the 3 subclasses and allows
 * for generic methods from this class.
 * 
 * @author team 1
 */
import java.awt.Graphics;
import java.awt.*;
import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import roomStuff.Room;
import roomStuff.ViewerMain;


public abstract class Enemy extends Creature {
    protected Creature target; // usually the player
    protected int recentlyHitPlayerTick;
    protected int score;

    public Enemy(int startX, int startY, int startSpeed, int startHealth, Creature target) {
        super(startX, startY, startSpeed, startHealth);
        this.target = target;
    }

    public void onDeath() {
        // Default: no action
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public abstract void update();

    public void takeDamage(int dmg) {
        health -= dmg;
    }
    
    @Override
    public void setRoom(Room r) {
        super.setRoom(r);
    }
    
    public void bounceOffPlayer() {
    	if (this.getBounds().intersects(target.getBounds())) this.recentlyHitPlayerTick = 1;
    }
    
    public boolean healPlayer() {
    	if (Math.random() <= 0.05) return true;
    	else return false;
    }
    
    public abstract int getScore();
}
