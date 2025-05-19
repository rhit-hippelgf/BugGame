package creatureStuff.enemytypes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import projectileStuff.Normal;
import projectileStuff.Bullet;

public class WalkingEnemy extends Enemy {

    public WalkingEnemy(int x, int y, Creature target) {
        super(x, y, 2, 10, target);
        setBulletClass(Normal.class);
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
        angle += (Math.random() - 0.5) * 0.4; // slight wobble
        move(angle);

        // shoot occasionally
        if (Math.random() < 0.005) {
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
    public void draw(Graphics2D g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x - 10, y - 10, 20, 20); // center the enemy

        for (var b : bullets) {
            b.draw(g);
        }
    }

    @Override
    public void onDeath() {
        System.out.println("WalkingEnemy has died!");
    }
}
