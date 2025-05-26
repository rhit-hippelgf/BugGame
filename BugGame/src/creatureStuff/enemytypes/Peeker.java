//package creatureStuff.enemytypes;
//
//import java.awt.*;
//import java.util.*;
//
//import projectileStuff.Bullet;
//import projectileStuff.Normal;
//import creatureStuff.Creature;
//import creatureStuff.Enemy;
//import handlingStuff.LineOfSight;
//
//public class Peeker extends Enemy {
//    private final int TILE_SIZE = 140;
//    private final int SPRITE_RADIUS = 17; // 35x35 sprite, 17 px radius
//
//    private char[][] layout;
//    private Creature player;
//
//    private Point coverTile;         // initialize hiding tile
//    private Point hidePosition;      // optimal hide pos
//    private boolean atCover = false; // in hiding spot?
//    private boolean peeking = false; // peeking?
//    private boolean returning = false; // returning?
//    private int peekTimer = 0; // peek timer
//    private final int peekDuration = 30; 
//    private final int cooldown = 60;
//    private Point peekTarget; //target
//
//    public Peeker(int x, int y, Creature player, char[][] layout) {
//        super(x, y, 2, 8, player); //goes to creature
//        this.player = player;
//        this.layout = layout;
//        setBulletClass(Normal.class);
//    }
//
//    @Override
//    public void update() {
//        if (health <= 0) {
//            onDeath();
//            return;
//        }
//
//        // validity of current cover
//        if (atCover && LineOfSight.hasLineOfSight(hidePosition.x, hidePosition.y, player.getX(), player.getY(), layout, TILE_SIZE)) {
//            coverTile = null; // reset cover
//            atCover = false;
//            peeking = false;
//            returning = false;
//            peekTimer = 0;
//        }
//
//        // reevaluates cover if lost player
//        double distToPlayer = Math.hypot(player.getX() - x, player.getY() - y);
//        if (distToPlayer > 600) {
//            coverTile = null; // give up and follow
//            atCover = false;
//            peeking = false;
//            returning = false;
//            peekTimer = 0;
//        }
//
//        if (coverTile == null) { // find new cover tile
//            coverTile = findCoverTile();
//            if (coverTile != null) {
//                hidePosition = calculateHidingPosition(coverTile);
//            }
//            return;
//        }
//
//        if (!atCover) {
//            moveToHidePosition();
//            return;
//        }
//
//        // peeking 
//        if (!peeking && !returning && peekTimer == 0) {
//            peeking = true;
//            peekTimer = peekDuration;
//
//            // step out 40 pixels
//            double angle = Math.atan2(player.getY() - y, player.getX() - x);
//            peekTarget = new Point(
//                (int)(hidePosition.x + 40 * Math.cos(angle)),
//                (int)(hidePosition.y + 40 * Math.sin(angle))
//            );
//        }
//
//        if (peeking && peekTimer > 0) {
//            peekTimer--;
//            moveToward(peekTarget.x, peekTarget.y);
//
//            if (peekTimer == peekDuration / 2) {
//                double angle = Math.atan2(player.getY() - y, player.getX() - x);
//                shoot(angle); // shoot mid-peek
//            }
//
//            if (peekTimer == 0) {
//                peeking = false;
//                returning = true;
//                peekTimer = cooldown;
//            }
//
//        } else if (returning && peekTimer > 0) {
//            peekTimer--;
//            moveToward(hidePosition.x, hidePosition.y);
//
//            if (peekTimer == 0) {
//                returning = false;
//            }
//        }
//
//        // update bullets
//        for (Bullet b : bullets) {
//            b.update();
//        }
//    }
//
//    private void moveToHidePosition() {
//        double distance = Math.hypot(hidePosition.x - x, hidePosition.y - y);
//        if (distance < 3) {
//            atCover = true;
//        } else {
//            moveToward(hidePosition.x, hidePosition.y);
//        }
//    }
//
//    private void moveToward(int targetX, int targetY) {
//        double angle = Math.atan2(targetY - y, targetX - x);
//        super.calculateSpeeds(angle);
//        super.move();
//    }
//
//    @Override
//    public void shoot(double angle) {
//        Bullet b = createBullet(angle, 6, 1);
//        if (b != null) bullets.add(b);
//    }
//
//    private Point findCoverTile() {
//        int tileX = x / TILE_SIZE;
//        int tileY = y / TILE_SIZE;
//        Point bestTile = null;
//        double bestDist = Double.MAX_VALUE;
//
//        for (int dy = -5; dy <= 5; dy++) {
//            for (int dx = -5; dx <= 5; dx++) {
//                int tx = tileX + dx;
//                int ty = tileY + dy;
//
//                if (inBounds(tx, ty) && isWalkable(layout[ty][tx])) {
//                    Point hidingSpot = calculateHidingPosition(new Point(tx, ty));
//
//                    if (!LineOfSight.hasLineOfSight(
//                            hidingSpot.x, hidingSpot.y,
//                            player.getX(), player.getY(),
//                            layout,
//                            TILE_SIZE)) {
//                        double dist = Math.hypot(hidingSpot.x - x, hidingSpot.y - y);
//                        if (dist < bestDist) {
//                            bestDist = dist;
//                            bestTile = new Point(tx, ty);
//                        }
//                    }
//                }
//            }
//        }
//
//        return bestTile != null ? bestTile : new Point(tileX, tileY); // retake cover
//    }
//
//    private Point calculateHidingPosition(Point tile) {
//        // get tile center
//        int tileCenterX = tile.x * TILE_SIZE + TILE_SIZE / 2;
//        int tileCenterY = tile.y * TILE_SIZE + TILE_SIZE / 2;
//
//        // angle from tile to player
//        double angleToPlayer = Math.atan2(player.getY() - tileCenterY, player.getX() - tileCenterX);
//
//        // offset away from player
//        int hideX = (int)(tileCenterX - Math.cos(angleToPlayer) * (TILE_SIZE / 2 - SPRITE_RADIUS - 5));
//        int hideY = (int)(tileCenterY - Math.sin(angleToPlayer) * (TILE_SIZE / 2 - SPRITE_RADIUS - 5));
//
//        return new Point(hideX, hideY);
//    }
//
//    private boolean inBounds(int x, int y) {
//        return y >= 0 && y < layout.length && x >= 0 && x < layout[0].length;
//    }
//
//    private boolean isWalkable(char tile) {
//        return tile == '.' || tile == 'F'; // adjust for your floor types
//    }
//
//    @Override
//    public void draw(Graphics2D g) {
//        g.setColor(peeking ? Color.ORANGE : returning ? Color.DARK_GRAY : Color.GRAY);
//        g.fillRect(x - SPRITE_RADIUS, y - SPRITE_RADIUS, 35, 35);
//
//        // draw bullets
//        for (Bullet b : bullets) {
//            b.draw(g);
//        }
//    }
//
//    @Override
//    public void onDeath() {
//        System.out.println("Peeker enemy has died!");
//    }
//}
