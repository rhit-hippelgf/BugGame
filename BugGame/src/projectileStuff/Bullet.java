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
    protected List<Effect> effects;

    public Bullet(int x, int y, double angle, int speed, int damage, List<Effect> effects) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.effects = effects;

        this.dx = speed * Math.cos(angle);
        this.dy = speed * Math.sin(angle);
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public List<Effect> getEffects() {
        return effects;
    }

    public void onHit(Creature target) {
        for (Effect e : effects) {
            e.apply(target);
        }
        target.takeDamage(damage);
    }

    public abstract void draw(Graphics g);
}
