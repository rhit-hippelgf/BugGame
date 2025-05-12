package projectileStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import creatureStuff.Creature;
import effectStuff.Effect;

public class Normal extends Bullet {

    public Normal(int x, int y, double angle, int speed, int damage, List<Effect> effects) {
        super(x, y, angle, speed, damage, effects);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 6, 6);
    }
}
