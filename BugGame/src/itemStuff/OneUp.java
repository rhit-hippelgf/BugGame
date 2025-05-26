package itemStuff;

import creatureStuff.Player;

public class OneUp extends Item {

    private static final int STAT_BOOST = 1;

    public OneUp(int x, int y) {
        super(x, y, "assets/sprites/items/Honey.png", STAT_BOOST);  
    }
//AHHHHH
    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  
    }

    @Override
    public String getStatCat() {
        return "lives";
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
