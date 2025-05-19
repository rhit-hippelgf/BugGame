package projectileStuff;

import java.awt.*;
import creatureStuff.Creature;

public class ZigZagBullet extends Bullet {
    private int tick = 0;
    private double zigzagAmplitude = 10.0;
    private double zigzagFrequency = 0.3;

    public ZigZagBullet(double x, double y, double angle, int speed, int damage, Creature source) {
        super(x, y, angle, speed, damage, source);
    }

    @Override
    public void update() {
        tick++;

        // Move forward
        double forwardX = Math.cos(angle) * speed;
        double forwardY = Math.sin(angle) * speed;

        // Offset perpendicular to direction (oscillates)
        double offset = Math.sin(tick * zigzagFrequency) * zigzagAmplitude;
        double perpX = -Math.sin(angle);
        double perpY = Math.cos(angle);

        // Final position
        x += forwardX;
        y += forwardY;

        // Apply lateral wiggle as displacement from the center path
        x += perpX * Math.cos(tick * zigzagFrequency) * 0.5;
        y += perpY * Math.cos(tick * zigzagFrequency) * 0.5;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval((int)x - width / 2, (int)y - height / 2, width, height);
    }
}
