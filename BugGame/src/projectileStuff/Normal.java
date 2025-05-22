package projectileStuff;

import java.awt.*;
import creatureStuff.Creature;

public class Normal extends Bullet {

    public Normal(double x, double y, double dx, double dy, int speed, int damage, Creature source) {
        super(x, y, dx, dy, speed, damage, source);
    }

    // Optional overloaded constructor (e.g., for effects)
    public Normal(double x, double y, double dx, double dy, int speed, int damage) {
        this(x, y, dx, dy, speed, damage, null);
    }

    @Override
    public void draw(Graphics2D g) {
    	Color old = g.getColor();
        g.setColor(Color.BLUE);
        g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
        g.setColor(old);
    }
}
