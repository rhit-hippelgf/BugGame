package effectStuff;

import creatureStuff.Creature;
import java.util.ArrayList;
import java.util.List;

public class effectHelper {
    private List<Effect> effects;

    public effectHelper() {
        this.effects = new ArrayList<>();
    }

    public void addEffect(Effect e) {
        effects.add(e);
    }

    public void applyAll(Creature target) {
        for (Effect e : effects) {
            e.apply(target);
        }
    }

    public List<Effect> getAll() {
        return effects;
    }
}
