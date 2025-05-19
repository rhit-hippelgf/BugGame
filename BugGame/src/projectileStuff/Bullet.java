package projectileStuff;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import creatureStuff.Creature;
import effectStuff.Effect;
import roomStuff.RoomLogic;

public abstract class Bullet {
    protected double x, y;
    protected double dx, dy;
    protected int speed;
    protected int damage;
    protected Creature source;

    protected boolean markForRemoval = false;

    protected int roomWidth = RoomLogic.getRoomWidth();
    protected int roomHeight = RoomLogic.getRoomHeight();
    protected int width = 10;
    protected int height = 10;
    protected double angle;


    public Bullet(double x, double y, double angle, int speed, int damage, Creature source) {
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

        // Offscreen logic moved here directly:
        if (x < 0 || x > roomWidth || y < 0 || y > roomHeight) {
            markForRemoval = true;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - width / 2, (int)y - height / 2, width, height);
    }

    public void setRoomBounds(int w, int h) {
        this.roomWidth = w;
        this.roomHeight = h;
    }
    
    public boolean isMarkedForRemoval() {
        return markForRemoval;
    }
    
    public void markForRemoval() {
        this.markForRemoval = true;
    }
    
    public Creature getSource() {
        return source;
    }



    public int getDamage() {
        return damage;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void onHit(Creature target) {
    	System.out.println("Marked bullet for removal: " + this + " (" + System.identityHashCode(this) + ")");
        target.takeDamage(damage);

        if (source != null && Math.random() < source.getEffectChance()) {
            for (Effect e : source.getInherentEffects()) {
                e.apply(target);
            }
        }

        // Mark this bullet for removal after hit
        markForRemoval = true;
    }

    public abstract void draw(Graphics g);
}
