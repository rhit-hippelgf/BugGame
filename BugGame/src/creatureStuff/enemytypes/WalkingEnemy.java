package creatureStuff.enemytypes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;

import creatureStuff.Creature;
import creatureStuff.Enemy;
import projectileStuff.Normal;
import roomStuff.RoomLogic;
import roomStuff.Room;
import projectileStuff.Bullet;

public class WalkingEnemy extends Enemy {

	private boolean spriteLoaded;
	private int frameCount;
	private File file1;
	private File file2;
	private File file3;
	private File file4;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;

	private Room room;

	public WalkingEnemy(int x, int y, Creature target, Room room) {
		super(x, y, 2, 10, target);
		setBulletClass(Normal.class);
		drawWidth = RoomLogic.getTileSize()-10;
		drawHeight = RoomLogic.getTileSize()-10;
		this.width = drawWidth/2+10;
		this.height = drawHeight - 10;
		this.room = room; // Store reference to the room

		file1 = new File("assets/sprites/creatures/BulletAnt1.png");
		file2 = new File("assets/sprites/creatures/BulletAnt2.png");
		file3 = new File("assets/sprites/creatures/BulletAnt3.png");
		file4 = new File("assets/sprites/creatures/BulletAnt4.png");

		try {
			image1 = ImageIO.read(file1);
			image2 = ImageIO.read(file2);
			image3 = ImageIO.read(file3);
			image4 = ImageIO.read(file4);
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - width/2, y - width/2, width, height);
	}

	@Override
	public void update() {
		if (health <= 0) {
			onDeath();
			return;
		}
		

		// use center of both enemy and target
		double tx = target.getX();
		double ty = target.getY();
		double ex = this.getX();
		double ey = this.getY();

		double xTemp = tx - ex;
		double yTemp = ty - ey;
		double mag = Math.sqrt(xTemp*xTemp + yTemp*yTemp);
		if (mag < 0.00001) {
			mag = 0.00001;
		}
		double dx = xTemp/mag;
		double dy = yTemp/mag;

		// apply a slight angle variation to simulate imperfect tracking

		// update speed vector using calculated angle
		super.calculateSpeeds(dx, dy);

		// move based on calculated vector
//		super.move();

		// occasionally shoot at the player
		if (Math.random() < 0.01) {
			shoot(dx, dy);
		}

		// bullet updates are now handled by Room, not here
	}

	@Override
	public void shoot(double dx, double dy) {
		Bullet b = createBullet(dx, dy, 5, 1);
		if (b != null && room != null) {
			room.getEnemyBullets().add(b);
		}
	}

	@Override
	public void draw(Graphics2D g) {
//		int scaleWidth = RoomLogic.getTileSize();
		frameCount += 1;

		if (spriteLoaded == true) {
			if (frameCount <= 10) {
				g.drawImage(image1, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight,
						null);
			} else if (frameCount > 10 && frameCount <= 20) {
				g.drawImage(image2, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight,
						null);
			} else if (frameCount > 20 && frameCount <= 30) {
				g.drawImage(image3, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight,
						null);
			} else if (frameCount > 30 && frameCount <= 40) {
				g.drawImage(image4, this.getX() - drawWidth / 2, this.getY() - drawHeight / 2, drawWidth, drawHeight,
						null);
				if (frameCount >= 40)
					frameCount = 0;
			}
		} else {
			// fallback rendering if sprite is missing
			g.setColor(Color.MAGENTA);
			g.fillRect(x - 10, y - 10, 20, 20); // center the enemy
		}

		// draw a red line toward the target (player)
		g.drawRect(x-width/2, y-height/2, width, height);
		g.setColor(Color.RED);
		int ex = this.getX();
		int ey = this.getY();
		int tx = target.getX();
		int ty = target.getY();
		g.drawLine(ex, ey, tx, ty);

		// bullet drawing is now handled by Room, not here
	}

	@Override
	public void onDeath() {
		System.out.println("WalkingEnemy has died!");
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
