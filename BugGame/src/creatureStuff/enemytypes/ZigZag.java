package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics;
import java.awt.Color;


public class ZigZag extends Enemy {
	private int zigzagCounter = 0;

    public ZigZag(int x, int y, Creature target) {
        super(x, y, 3, 5, target);
    }

    @Override
    public void update() {
        double angle = Math.atan2(target.getY() - y, target.getX() - x);
        double offset = (zigzagCounter++ / 10 % 2 == 0) ? 0.3 : -0.3;
        move(angle + offset);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 20, 20);
    }
}
