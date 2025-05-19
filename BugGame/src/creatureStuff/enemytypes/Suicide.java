package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

public class Suicide extends Enemy {
    private int detonationRange = 30;
    private boolean detonated = false;
    private int explosionX = 0;
    private int explosionY = 0;
    

    public Suicide(int x, int y, Creature target) {
        super(x, y, 3, 5, target);
        this.width = 20;
        this.height = 20;

    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    @Override
    public void update() {
        if (health <= 0) {
            onDeath();
            return;
        }

        if (detonated) return; 

        double dist = Math.hypot(target.getX() - x, target.getY() - y);

        if (dist <= detonationRange) {
            target.takeDamage(10);
            explosionX = x;
            explosionY = y;
            this.health = 0;       // suicide
            this.detonated = true; // explosion effect
        } else {
            double angle = Math.atan2(target.getY() - y, target.getX() - x);
            move(angle);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (detonated) {
            g.setColor(new Color(255, 0, 0, 100)); // see through red
            g.fillOval(explosionX - detonationRange, explosionY - detonationRange, 
                       detonationRange * 2, detonationRange * 2);
        }

        g.setColor(Color.RED);
        g.fillOval(x - 10, y - 10, 20, 20); // enemy body
    }

    @Override
    public void shoot(double angle) {
        // doesnt shoot
    }

    @Override
    public void onDeath() {
        System.out.println("Suicide enemy has detonated and died!");
    }
}
