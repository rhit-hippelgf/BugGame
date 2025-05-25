package roomStuff;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class FloatingText {
    private String text;
    private int x, y;
    private Color color;
    private int lifespan = 60; 

    public FloatingText(String text, int x, int y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public void update() {
        y -= 1;         
        lifespan--;     
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString(text, x, y);
    }

    public boolean isExpired() {
        return lifespan <= 0;
    }
}