package itemStuff;

import creatureStuff.Player;

public class Ace extends Item {

    private static final int STAT_BOOST = 10;  

    public Ace(int x, int y) {
        super(x, y, "assets/sprites/items/Ace.png", STAT_BOOST); 
    }

    @Override
    public void applyEffect(Player player) {
        player.addItem(this);  
    }

    @Override
    public String getStatCat() {
        return "blockChance";
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
