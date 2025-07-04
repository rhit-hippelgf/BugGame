package projectileStuff;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import creatureStuff.Creature;
import creatureStuff.Player;
import effectStuff.Effect;
import handlingStuff.RngHandler;
import roomStuff.RoomLogic;

public abstract class Bullet {
    protected double x, y;
    protected double dx, dy;
    protected double xspeed, yspeed;
    protected int speed;
    protected int damage;
    protected Creature source;
    protected boolean isCrit = false;

    protected boolean markForRemoval = false;

    protected static int roomWidth = RoomLogic.getRoomWidth();
    protected static int roomHeight = RoomLogic.getRoomHeight();
    protected int width = 20;
    protected int height = 20;
//    protected double angle;


    public Bullet(double x, double y, double dx, double dy, int speed, int damage, Creature source) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.speed = speed;
        this.damage = damage;
        this.source = source;

        this.xspeed = speed * this.dx;
        this.yspeed = speed * this.dy;
    }

    public void update() {
        x += xspeed;
        y += yspeed;

        // offscreen logic moved here directly:
        if (x < 0 || x > roomWidth || y < 0 || y > roomHeight) {
            markForRemoval = true;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - width / 2, (int)y - height / 2, width, height);
    }

//    public void setRoomBounds(int w, int h) {
//        this.roomWidth = w;
//        this.roomHeight = h;
//    }
    
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
        if (source instanceof Player player) {
            // roll for lightning chanc
            if (new RngHandler().handleCheck(player.getLightingChance())) {
                target.takeDamage(Integer.MAX_VALUE);  // instakill
                if (player.getRoom() != null) {
                    player.getRoom().spawnText("ZAP!", target.getX(), target.getY(), Color.YELLOW);
                    player.getRoom().spawnLightning(target.getX(), target.getY());
                }
                // spawn lightning visual here
                markForRemoval = true;
                return;
            }
        }

        target.takeDamage(damage);

        if (source != null && Math.random() < source.getEffectChance()) {
            for (Effect e : source.getInherentEffects()) {
                e.apply(target);
            }
        }

        markForRemoval = true;
    }

    
    public void setCrit(boolean crit) {
        this.isCrit = crit;
    }

    public boolean isCrit() {
        return isCrit;
    }
    
    public abstract void draw(Graphics2D g);
    
    
}
