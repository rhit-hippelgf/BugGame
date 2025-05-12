package creatureStuff;

import java.awt.Graphics;

public abstract class Enemy extends Creature {
    protected Creature target; // usually the player

    public Enemy(int startX, int startY, int startSpeed, int startHealth, Creature target) {
        super(startX, startY, startSpeed, startHealth, startHealth);
        this.target = target;
    }


    public abstract void update();

    public abstract void draw(Graphics g);
}
