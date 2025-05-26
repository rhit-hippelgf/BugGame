package effectStuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class LightningStrike {
    private int x, y;
    private int timer = 25; 

    public LightningStrike(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        timer--;
    }

    public void draw(Graphics2D g2) {
        // core bolt
        g2.setColor(Color.YELLOW);
        g2.setStroke(new BasicStroke(8)); 
        g2.drawLine(x, y - 150, x, y + 150);

        // aura around the bolt
        g2.setColor(new Color(255, 255, 0, 100));  // yellow glow
        g2.setStroke(new BasicStroke(16));
        g2.drawLine(x, y - 150, x, y + 150);

        // blue/white energy core
        g2.setColor(new Color(100, 200, 255, 180));
        g2.setStroke(new BasicStroke(12));
        g2.drawLine(x, y - 150, x, y + 150);

        // lightning forks
        g2.setStroke(new BasicStroke(4));
        g2.setColor(Color.WHITE);
        g2.drawLine(x - 40, y - 120, x + 60, y + 60);
        g2.drawLine(x + 50, y - 100, x - 50, y + 80);
        g2.drawLine(x - 60, y - 80, x + 80, y + 100);
        g2.drawLine(x + 30, y - 140, x - 30, y + 120);

        // reset stroke
        g2.setStroke(new BasicStroke(1));
    }

    public boolean isExpired() {
        return timer <= 0;
    }
}
