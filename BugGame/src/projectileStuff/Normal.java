package projectileStuff;

import java.awt.Color;
import java.awt.Graphics;
import creatureStuff.Creature;

public class Normal extends Bullet {

    public Normal(int x, int y, double angle, int speed, int damage, Creature source) {
        super(x, y, angle, speed, damage, source);
    }

    // null if no effects
    public Normal(int x, int y, double angle, int speed, int damage) {
        this(x, y, angle, speed, damage, null);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x - width / 2, y - height / 2, width, height);
    }


}
