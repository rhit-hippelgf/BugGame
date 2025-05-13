package roomStuff;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import controllerStuff.Controller;
import creatureStuff.Enemy;
import creatureStuff.Player;
import java.awt.Dimension;
import java.awt.Color;

public class Room extends JComponent {

    private List<Enemy> enemies = new ArrayList<>();
    private Player player;

    private boolean north, east, south, west;
    private Controller control;
    private char[][] layout;

    private final int TILE_SIZE;

    // construc accepts dynamic square tile size
    public Room(char[][] layout, boolean north, boolean east, boolean south, boolean west, int tileSize) {
        this.setFocusable(true);
        this.setOpaque(true);
        this.layout = layout;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.TILE_SIZE = tileSize;
    }

    public void setPlayer(Player p) {
        this.player = p;
        this.control = new Controller(this, player);
        this.player.setRoomBounds(getWidth(), getHeight());
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Dimension getRoomSize() {
        int width = layout[0].length * TILE_SIZE;
        int height = layout.length * TILE_SIZE;
        return new Dimension(width, height);
    }

    public void updateEntities() {
        if (player != null) {
            player.update();
            control.moveIfPress();
        }
        for (Enemy e : enemies) e.update();
        enemies.removeIf(e -> e.getHealth() <= 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[0].length; col++) {
                int x = col * TILE_SIZE;
                int y = row * TILE_SIZE;

                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                g2.setColor(Color.BLACK);
                g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        if (player != null) player.draw(g2);
        for (Enemy e : enemies) e.draw(g);
    }

    public void drawScreen() {
        this.repaint();
    }
}