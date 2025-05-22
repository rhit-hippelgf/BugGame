package projectileStuff;

import java.awt.*;
import creatureStuff.Creature;

public class ZigZagBullet extends Bullet {
    private int tick = 0;

    // Controls how wide the zigzag motion is
    private double zigzagAmplitude = 4.0;

    // Controls how fast it oscillates
    private double zigzagFrequency = 0.25;

    public ZigZagBullet(double x, double y, double dx, double dy, int speed, int damage, Creature source) {
        super(x, y, dx, dy, speed, damage, source);
    }

    @Override
    public void update() {
        tick++;

        // Move forward in the direction of the angle
        double forwardX = dx * speed;
        double forwardY = dy * speed;

        // Offset perpendicular to the direction (oscillates side-to-side)
        double perpX = -dy;
        double perpY = dx;

        // Wait a few frames before applying the zigzag
        double lateralWiggle = 0;
        if (tick >= 10) {
            lateralWiggle = Math.sin(tick * zigzagFrequency) * zigzagAmplitude; // small wiggle
        }

        // Final position = forward step + lateral offset
        x += forwardX + perpX * lateralWiggle;
        y += forwardY + perpY * lateralWiggle;
    }

    @Override
    public void draw(Graphics2D g) {
    	Color old = g.getBackground();
        g.setColor(Color.MAGENTA);
        g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
        g.setColor(old);
    }
}
