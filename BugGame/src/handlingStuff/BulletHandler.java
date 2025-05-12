package handlingStuff;

import creatureStuff.Player;
import creatureStuff.Enemy;
import creatureStuff.Creature;
import projectileStuff.Bullet;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

public class BulletHandler {

    private static final int BULLET_HITBOX_RADIUS = 6;
    private static final int SCREEN_WIDTH = 1920;
    private static final int SCREEN_HEIGHT = 1080;

    public static void handleCollisions(Player player, List<Enemy> enemies) {
        List<Bullet> playerBullets = player.getBullets();

        // layer bullet hitting enemy check
        for (Bullet b : playerBullets) {
            for (Enemy e : enemies) {
                if (collides(b, e)) {
                    b.onHit(e);
                    b.markForRemoval = true;
                }
            }
        }

        // enemy bullets hitting player check
        for (Enemy e : enemies) {
            List<Bullet> enemyBullets = e.getBullets();
            for (Bullet b : enemyBullets) {
                if (collides(b, player)) {
                    b.onHit(player);
                    b.markForRemoval = true;
                }
            }
        }

        // despawn out-of-bounds or marked bullets
        cleanupBullets(playerBullets);
        for (Enemy e : enemies) {
            cleanupBullets(e.getBullets());
        }
    }

    private static boolean collides(Bullet b, Creature c) {
        int dx = b.getX() - c.getX();
        int dy = b.getY() - c.getY();
        double dist = Math.hypot(dx, dy);
        return dist < BULLET_HITBOX_RADIUS + 10; // 10 is creature's approx. radius
    }

    private static void cleanupBullets(List<Bullet> bullets) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            if (b.markForRemoval || isOutOfBounds(b)) {
                it.remove();
            }
        }
    }

    private static boolean isOutOfBounds(Bullet b) {
        int x = b.getX();
        int y = b.getY();
        return x < 0 || y < 0 || x > SCREEN_WIDTH || y > SCREEN_HEIGHT;
    }
}
