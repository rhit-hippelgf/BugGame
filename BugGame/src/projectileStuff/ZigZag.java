package projectileStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import creatureStuff.Creature;
import effectStuff.Effect;

public class ZigZag extends Bullet {

    private double baseAngle;
    private int frameCount = 0;
    private double amplitude = 5;
    private double frequency = 0.3;

    public ZigZag(int x, int y, double angle, int speed, int damage, List<Effect> effects) {
        super(x, y, angle, speed, damage, effects);
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
        g.fillOval(x, y, 6, 6);
    }
}
