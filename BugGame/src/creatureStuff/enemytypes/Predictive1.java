package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics;
import java.awt.Color;


public class Predictive1 extends Enemy {
    private int detonationRange = 30;

    public Predictive1(int x, int y, Creature target) {
        super(x, y, 3, 5, target);
    }

    @Override
    public void update() {
    	double futureX = target.getX() + 10; // fake lead
    	double futureY = target.getY() + 10;
    	double angle = Math.atan2(futureY - y, futureX - x);
    	move(angle);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 20, 20);
    }
}
