package creatureStuff;

import java.awt.Graphics;
import java.awt.Rectangle;


public abstract class Enemy extends Creature {
    protected Creature target; // usually the player

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
        System.out.println("Enemy took " + dmg + " damage");
        health -= dmg;
        System.out.println("Enemy health now: " + health);
    }



	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
}
