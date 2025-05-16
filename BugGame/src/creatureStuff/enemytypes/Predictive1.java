package creatureStuff.enemytypes;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import projectileStuff.Normal;
import projectileStuff.Bullet;
//import effectStuff.burn;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class Predictive1 extends Enemy {

    public Predictive1(int x, int y, Creature target) {
        super(x, y, 3, 5, target);
        setBulletClass(Normal.class);
        this.width = 20;
        this.height = 20;
        //addEffect(new burn(2)); // burns on hit
        //addEffectChance(0.3); // 30% chance
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

        double futureX = target.getX() + 10;
        double futureY = target.getY() + 10;
        double angle = Math.atan2(futureY - y, futureX - x);
        move(angle);

        if (Math.random() < 0.01) {
            shoot(angle);
        }

        for (var b : bullets) {
            b.update();
        }
    }

    @Override
    public void shoot(double angle) {
        Bullet b = createBullet(angle, 6, 1);
        if (b != null) bullets.add(b);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(x - 10, y - 10, 20, 20); // centered

        for (var b : bullets) {
            b.draw(g);
        }
    }

    @Override
    public void onDeath() {
        System.out.println("Predictive1 enemy has died!");
    }
}
