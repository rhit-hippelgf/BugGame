package effectStuff;

import creatureStuff.Creature;

public interface Effect {
    void apply(Creature target);
    String getName(); //for debug
    void stack(Effect other);
}
