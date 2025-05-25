package itemStuff;

import creatureStuff.Player;

public class SpeedShoes extends Item {

    private static final int STAT_BOOST = 5;

    public SpeedShoes(int x, int y) {
        super(x, y, "assets/sprites/items/Bread.png", STAT_BOOST);
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  // Triggers stat logic in Player
    }

    @Override
    public String getStatCat() {
        return "speed";
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
