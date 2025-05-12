import java.awt.Graphics;

import creatureStuff.Creature;
import creatureStuff.Enemy;

public class WalkingEnemy extends Enemy {
    public WalkingEnemy(int x, int y, Creature target) {
        super(x, y, 2, 10, target);
    }

    @Override
    public void update() {
    	double angle = Math.atan2(target.getY() - y, target.getX() - x);
    	angle += (Math.random() - 0.5) * 0.4; //wobble
    	move(angle);
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, 20, 20); //temp
    }
}
