package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import creatureStuff.Creature;

public class highScoreRoom extends Room {
	HighScoreManager mgr;

	public highScoreRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level,
			Creature player) {
		super(north, east, south, west, tileSize, level, player);
		FileReader pickLayout = new FileReader(north, east, south, west, false, false, false, false, true, level);
        layout = pickLayout.getLayout();
        try {
			mgr = new HighScoreManager();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.fillRect(0, 0, RoomLogic.getRoomWidth(), RoomLogic.getRoomHeight());
        
        north.draw(g2);
        if (player != null) player.draw(g2);
        
   }
	

}