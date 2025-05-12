package projectileStuff;

import java.awt.Graphics;
import java.util.List;
import creatureStuff.Creature;
import effectStuff.Effect;

public abstract class Bullet {
    protected int x, y;
    protected double dx, dy;
    protected int speed;
    protected int damage;
    protected Creature source;
	public boolean markForRemoval = false;

    public Bullet(int x, int y, double angle, int speed, int damage, Creature source) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.source = source;

        this.dx = speed * Math.cos(angle);
        this.dy = speed * Math.sin(angle);
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }


    public void onHit(Creature target) {
        target.takeDamage(damage);

        if (source != null && Math.random() < source.getEffectChance()) {
            for (Effect e : source.getInherentEffects()) {
                e.apply(target);
            }
        }
    }

    public abstract void draw(Graphics g);
}
