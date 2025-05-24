package roomStuff;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.List;
import java.awt.Font;

import creatureStuff.Creature;

public class highScoreRoom extends Room {
	HighScoreManager mgr;
	List<Integer> scores;

	public highScoreRoom(boolean north, boolean east, boolean south, boolean west, int tileSize, int level,
			Creature player) {
		super(north, east, south, west, tileSize, level, player);
		FileReader pickLayout = new FileReader(north, east, south, west, false, false, false, false, true, level);
        layout = pickLayout.getLayout();
        try {
			mgr = new HighScoreManager();
			scores = mgr.getHighScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        Color old = g2.getBackground();
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, RoomLogic.getRoomWidth(), RoomLogic.getRoomHeight());
        
        g2.setColor(Color.RED);
        Font font = new Font("Times New Roman", Font.BOLD, 40);
        g2.setFont(font);
        
        g2.drawString("Highscores:", getTileSize()*55/10,getTileSize());
        
        Font font2 = new Font("Times New Roman", Font.PLAIN, 30);
        g2.setFont(font2);
        for (int i = 1; i<=5; i++) {
        	g2.drawString(i + ":     " + scores.get(i-1), getTileSize()*55/10, getTileSize()*i + getTileSize());
        }
        
        north.draw(g2);
        if (player != null) player.draw(g2);
        
   }
	

}