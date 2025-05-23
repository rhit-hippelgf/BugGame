package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import creatureStuff.Creature;

public class highScoreRoom extends Room {

	public highScoreRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level,
			Creature player) {
		super(north, east, south, west, tileSize, level, player);
		// TODO Auto-generated constructor stub
	}

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
   }
	

}