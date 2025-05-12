package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import creatureStuff.Enemy;


public class Room extends JPanel{
	
	private List<Enemy> enemies = new ArrayList<>();


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
				{'T','T','T','T','T','T','T','T','T','T','T','T','T'},
			}
	};
	
	public Room(boolean north, boolean east, boolean south, boolean west) {
		this.north = north;
		this.east = east;
		this.south = south;
		this.west = west;
	}
	
	 @Override
	    protected void paintComponent(Graphics g) {
	      super.paintComponent(g);       
	      Graphics2D g2 = (Graphics2D) g;                 
	      for (Enemy e : enemies) {
	          e.draw(g2);
	      }
	    }

	public void updateEntities() {
		for (Enemy e : enemies) {
			e.update();
		}
		enemies.removeIf(e -> e.getHealth() <= 0);
	}
	
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}
}
