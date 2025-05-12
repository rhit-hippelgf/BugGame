package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics;
import java.awt.Color;


public class Suicide extends Enemy {
    private int detonationRange = 30;

    public Suicide(int x, int y, Creature target) {
        super(x, y, 3, 5, target);
    }

    @Override
    public void update() {
        double dist = Math.hypot(target.getX() - x, target.getY() - y);
        if (dist <= detonationRange) {
            target.takeDamage(10);
            this.health = 0; // kys
        } else {
            double angle = Math.atan2(target.getY() - y, target.getX() - x);
            move(angle);
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 20, 20);
    }
}
