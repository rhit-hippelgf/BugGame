package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;
import projectileStuff.ZigZagBullet;
import projectileStuff.Bullet;
import effectStuff.poison; // default poison

public class ZigZag extends Enemy {
    private int zigzagCounter = 0;
    private boolean poisoned = false;


    public ZigZag(int x, int y, Creature target) {
        super(x, y, 2, 5, target);

        addEffect(new poison(3)); // built in poison
        addEffectChance(0.25); // 25% chance
        setBulletClass(ZigZagBullet.class);
        this.width = 20;
        this.height = 20;
    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {
        if (health <= 0) {
            onDeath();
            return;
        }

        double angle = Math.atan2(target.getY() - y, target.getX() - x);
        double offset = (zigzagCounter++ / 10 % 2 == 0) ? 0.3 : -0.3;
        move(angle + offset);

        if (zigzagCounter % 50 == 0) {
            shoot(angle);
        }

        for (var b : bullets) {
            b.update();
        }
    }

    @Override
    public void shoot(double angle) {
        Bullet b = createBullet(angle, 2, 1);
        if (b != null) bullets.add(b); 
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(poisoned ? Color.GREEN : Color.BLUE);
        g.fillOval(x - 10, y - 10, 20, 20);
        for (var b : bullets) {
            b.draw(g);
        }
    }

    @Override
    public void onDeath() {
        System.out.println("ZigZag enemy has died!");
    }
}
