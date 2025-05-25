package itemStuff;

import creatureStuff.Player;

public class BugEyeGoggles extends Item {

    private static final int STAT_BOOST = 10;

    public BugEyeGoggles(int x, int y) {
        super(x, y, "assets/sprites/items/bugEyeGoggles.png", STAT_BOOST);
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  // Uses stat logic in Player.addItem
    }

    @Override
    public String getStatCat() {
        return "critChance";
    }

    @Override
    public int getStatBoost() {
        return STAT_BOOST;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }
}
