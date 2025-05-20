package itemStuff;

import java.util.*;

public class ItemDropper {
    private static final List<ItemFactory> common = new ArrayList<>();
    private static final List<ItemFactory> rare = new ArrayList<>();
    private static final List<ItemFactory> epic = new ArrayList<>();
    private static final List<ItemFactory> legendary = new ArrayList<>();

    static {
        // Register items for each rarity using lambdas
        common.add((x, y) -> new BugEyeGoggles(x, y));
        common.add((x, y) -> new something(x, y));
        common.add((x, y) -> new something(x, y));
        common.add((x, y) -> new something(x, y));
        common.add((x, y) -> new something(x, y));
        common.add((x, y) -> new something(x, y));
        
        
        // common.add((x, y) -> new SpeedBoots(x, y));
        rare.add((x, y) -> new STFU(x, y));
        
        epic.add((x, y) -> new poison(x, y));
        
        legendary.add((x, y) -> new trishot(x, y));
    }

    public static Item getRandomItem(Rarity rarity, int x, int y) {
        Random rand = new Random();
        List<ItemFactory> pool;

        switch (rarity) {
            case COMMON: pool = common; break;
            case RARE: pool = rare; break;
            case EPIC: pool = epic; break;
            case LEGENDARY: pool = legendary; break;
            default: return null;
        }

        if (pool.isEmpty()) return null;
        return pool.get(rand.nextInt(pool.size())).create(x, y);
    }

    public interface ItemFactory {
        Item create(int x, int y);
    }
}
