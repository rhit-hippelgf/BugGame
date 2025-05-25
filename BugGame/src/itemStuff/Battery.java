package itemStuff;

import creatureStuff.Player;

public class Battery extends Item {

    private static final int STAT_BOOST = 2;

    public Battery(int x, int y) {
        super(x, y, "assets/sprites/items/battery.png", STAT_BOOST);  // Ensure this sprite exists
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  // Player will interpret fireRate
    }

    @Override
    public String getStatCat() {
        return "fireRate";
    }

    @Override
    public int getStatBoost() {
        return STAT_BOOST;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
}
