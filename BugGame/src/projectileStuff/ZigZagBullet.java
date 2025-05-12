package projectileStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import creatureStuff.Creature;
import effectStuff.Effect;

public class ZigZagBullet extends Bullet {

    private int frameCount = 0;
    private final double baseAngle;
    private final double amplitude = 5;
    private final double frequency = 0.3;

    public ZigZagBullet(int x, int y, double angle, int speed, int damage, Creature source) {
        super(x, y, angle, speed, damage, source);
        this.baseAngle = angle;
    }

    @Override
    public void update() {
        frameCount++;

        double forwardX = speed * Math.cos(baseAngle);
        double forwardY = speed * Math.sin(baseAngle);

        double perpAngle = baseAngle + Math.PI / 2;
        double wiggleOffset = amplitude * Math.sin(frameCount * frequency);
        double wiggleX = wiggleOffset * Math.cos(perpAngle);
        double wiggleY = wiggleOffset * Math.sin(perpAngle);

        x += (int)(forwardX + wiggleX);
        y += (int)(forwardY + wiggleY);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(x - 3, y - 3, 6, 6);
    }
}
