package projectileStuff;

import java.awt.*;
import creatureStuff.Creature;
import creatureStuff.Player;

public class Normal extends Bullet {
	private Color color;

	public Normal(double x, double y, double dx, double dy, int speed, int damage, Creature source) {
	    super(x, y, dx, dy, speed, damage, source);

	    if (source instanceof Player) {
	        color = isCrit ? Color.YELLOW : Color.BLUE;  
	    } else {
	        color = Color.RED;
	    }
	}



    // Optional overloaded constructor (e.g., for effects)
    public Normal(double x, double y, double dx, double dy, int speed, int damage) {
        this(x, y, dx, dy, speed, damage, null);
    }

    @Override
    public void draw(Graphics2D g) {
        Color old = g.getColor();

        if (isCrit) {
            g.setColor(Color.YELLOW); 
        } else {
            g.setColor(color); 
        }

        g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
        g.setColor(old);
    }

}
