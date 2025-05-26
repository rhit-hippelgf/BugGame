package itemStuff;

import java.util.*;
import itemStuff.ItemDropper.ItemFactory;

public class ItemDropper {
    private static final List<ItemFactory> common = new ArrayList<>();
    private static final List<ItemFactory> rare = new ArrayList<>();
    private static final List<ItemFactory> epic = new ArrayList<>();
    private static final List<ItemFactory> legendary = new ArrayList<>();

    static {
        // Common Items
        common.add((x, y) -> new SpeedShoes(x, y));
        common.add((x, y) -> new Battery(x, y));

        // common.add((x, y) -> new SomeOtherCommonItem(x, y));

        // Rare Items
        rare.add((x, y) -> new BugEyeGoggles(x, y));
        rare.add((x, y) -> new Ace(x, y));


        // Epic Items
        // epic.add((x, y) -> new Poison(x, y)); // uncomment when real class exists

        // Legendary Items
        // legendary.add((x, y) -> new TriShot(x, y)); // uncomment when real class exists
    }

    public static Item getRandomItem(Rarity rarity, int x, int y) {
        List<ItemFactory> pool;
        switch (rarity) {
            case COMMON: pool = common; break;
            case RARE: pool = rare; break;
            case EPIC: pool = epic; break;
            case LEGENDARY: pool = legendary; break;
            default: return null;
        }

        if (pool.isEmpty()) return null;

        return pool.get(new Random().nextInt(pool.size())).create(x, y);
    }

    @FunctionalInterface
    public interface ItemFactory {
        Item create(int x, int y);
    }
}
