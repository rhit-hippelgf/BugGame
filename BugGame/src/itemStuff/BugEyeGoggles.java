package itemStuff;

import creatureStuff.Player;

public class BugEyeGoggles extends Item {
    public BugEyeGoggles(int x, int y) {
        super(x, y, "assets/sprites/items/bugEyeGoggles.png", statBoost);
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  // Defers effect logic to Player.addItem()
    }

    @Override
    public String getStatCat() {
        return "critChance";
    }

    @Override
    public int getStatBoost() {
        return 10;
    }
}
