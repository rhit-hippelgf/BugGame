package itemStuff;

import creatureStuff.Player;

public class Lightning extends Item {
    private static final int STAT_BOOST = 1;

    public Lightning(int x, int y) {
        super(x, y, "assets/sprites/items/Lightbulb.png", STAT_BOOST);
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);
    }

    @Override
    public String getStatCat() {
        return "lightingChance";
    }

    @Override
    public int getStatBoost() {
        return STAT_BOOST;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.EPIC;
    }
}
