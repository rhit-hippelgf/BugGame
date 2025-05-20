package itemStuff;

import creatureStuff.Player;

public class SpeedShoes extends Item {
    public SpeedShoes(int x, int y) {
        super(x, y, "assets/sprites/items/speedShoes.png");
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
        return 5;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
}
