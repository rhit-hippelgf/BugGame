package creatureStuff;

import java.awt.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import handlingStuff.RngHandler;
import itemStuff.Item;
import effectStuff.poison;
import projectileStuff.Bullet;
import projectileStuff.Normal;
import projectileStuff.ZigZagBullet;
import roomStuff.Room; // needed to track room position/dimensions
import roomStuff.RoomLogic;

public class Player extends Creature {
    private int critChance = 0;
    private int fireRate = 1;
    private int dodgeChance = 0;
    private int lives = 1;
    //private String damageType = "normal";
    private int lightingChance = 0;
    private boolean triShot = false;

    // ref to Room component for screen coordinates and dimensions
    private Room currentRoom;

    public Player(int startX, int startY, int startSpeed, int startHealth) {
        super(startX, startY, startSpeed, startHealth, startHealth);
        width = RoomLogic.getTileSize()/2;
        height = RoomLogic.getTileSize();
        setBulletClass(Normal.class);
    }

    // linking player to its room for despawn bounds
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void addItem(Item item) {
        switch (item.getStatCat()) {
            case "health":
                health += item.getStatBoost();
                break;
            case "speed":
                speed += 5;
                break;
            case "healthCap":
                healthCap += item.getStatBoost();
                break;
            case "critChance":
                critChance += item.getStatBoost();
                break;
            case "fireRate":
                fireRate += item.getStatBoost();
                break;
            case "dodgeChance":
                dodgeChance += item.getStatBoost();
                break;
            case "lives":
                lives += item.getStatBoost();
                break;
            case "poison":
                //damageType = "poison";
                addEffect(new poison(3));
                break;
            case "lightingChance":
                lightingChance += item.getStatBoost();
                break;
            case "triShot":
                triShot = true;
                break;
            case "normalBullet":
                setBulletClass(Normal.class);
                break;
            case "zigzagBullet":
                setBulletClass(ZigZagBullet.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK); // draw player sprite
        g.fillOval(x - width/2, y - height/2, width, height);

        // draw all bullets not marked for removal
        for (Bullet b : bullets) {
            if (!b.markForRemoval) {
                b.draw(g);
            }
        }
    }

    @Override
    public void shoot(double angle) {
//        System.out.println("Shooting angle: " + angle);
//        System.out.println("Current bullet count: " + bullets.size());

        int baseDamage = 1;

        if (new RngHandler().handleCheck(critChance)) {
            baseDamage *= 2;
        }

        // cerate/fire a bullet from player's center
        Bullet b = createBullet(angle, 8, baseDamage);
        if (b == null) System.out.println("Bullet creation failed!");

        if (b != null) bullets.add(b);
    }

    public void update() {
//        System.out.println("Player update called, bullets = " + bullets.size());

        List<Bullet> toRemove = new ArrayList<>();

        for (Bullet b : bullets) {
            b.update(); // always update bullets
        }

        // do bounds check if currentRoom is valid
        if (currentRoom != null) {
            int roomX = currentRoom.getX();
            int roomY = currentRoom.getY();
            int roomW = currentRoom.getWidth();
            int roomH = currentRoom.getHeight();

            int minX = roomX - 50;
            int maxX = roomX + roomW + 50;
            int minY = roomY - 50;
            int maxY = roomY + roomH + 50;

            for (Bullet b : bullets) {
                if (b.getX() < minX || b.getX() > maxX || b.getY() < minY || b.getY() > maxY) {
                    System.out.println("Despawning bullet at: " + b.getX() + ", " + b.getY());
                    toRemove.add(b);
                }
            }
        }

        bullets.removeAll(toRemove);
    }


//    @Override
//    public int getX() {
//        return x;
//    }
//
//    @Override
//    public int getY() {
//        return y;
//    }
}
