package projectileStuff;

import java.awt.*;
import creatureStuff.Creature;

public class Normal extends Bullet {

    public Normal(double x, double y, double angle, int speed, int damage, Creature source) {
        super(x, y, angle, speed, damage, source);
    }

    // Optional overloaded constructor (e.g., for effects)
    public Normal(double x, double y, double angle, int speed, int damage) {
        this(x, y, angle, speed, damage, null);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
    }
}
