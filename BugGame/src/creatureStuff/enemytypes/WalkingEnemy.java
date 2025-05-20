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
		this.width = RoomLogic.getTileSize();
		this.height = RoomLogic.getTileSize();
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

		double angle = Math.atan2(ty - ey, tx - ex);

		// apply a slight angle variation to simulate imperfect tracking
		//angle += (Math.random() - 0.5) * 0.4;

		// update speed vector using calculated angle
		super.calculateSpeeds(angle);

		// move based on calculated vector
		super.move();

		// occasionally shoot at the player
		if (Math.random() < 0.005) {
			shoot(angle);
		}

		// bullet updates are now handled by Room, not here
	}

	@Override
	public void shoot(double angle) {
		Bullet b = createBullet(angle, 2, 1);
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
				g.drawImage(image1, this.getX() - width / 2, this.getY() - height / 2, width, height,
						null);
			} else if (frameCount > 10 && frameCount <= 20) {
				g.drawImage(image2, this.getX() - width / 2, this.getY() - height / 2, width, height,
						null);
			} else if (frameCount > 20 && frameCount <= 30) {
				g.drawImage(image3, this.getX() - width / 2, this.getY() - height / 2, width, height,
						null);
			} else if (frameCount > 30 && frameCount <= 40) {
				g.drawImage(image4, this.getX() - width / 2, this.getY() - height / 2, width, height,
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
