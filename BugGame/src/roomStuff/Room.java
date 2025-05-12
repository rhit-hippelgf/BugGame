package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import creatureStuff.Enemy;
import creatureStuff.Player;
import projectileStuff.Bullet;

public class Room extends JPanel{
	

	private List<Enemy> enemies = new ArrayList<>();
	private Player player; // player reference              

	public boolean north;
	private boolean east;
	private boolean south;
	private boolean west;

	char[][][] layouts = {
			{
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'}
			}
	};
	
	// Constructor
	public Room(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}

	
	// mutators
	public void setPlayer(Player p){
		this.player = p;
	}

	public void addEnemy(Enemy e){
		enemies.add(e);
	}

	public List<Enemy> getEnemies(){
		return enemies;
	}

	//game loop tick
	public void updateEntities(){
		if(player != null) player.update();     // player moves / fires

		for(Enemy e : enemies) e.update();      // enemies & their bullets
		enemies.removeIf(e -> e.getHealth() <= 0);
	}


	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		if(player != null) player.draw(g);

		for(Enemy e : enemies){
			e.draw(g);   // each enemy already draws its bullets
		}
	}
}
