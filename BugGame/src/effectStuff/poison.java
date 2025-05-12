package effectStuff;

import creatureStuff.Creature;

public class poison implements Effect {
    private int poisonDamage;

    public poison(int poisonDamage) {
        this.poisonDamage = poisonDamage;
    }

    @Override
    public void apply(Creature target) {
        target.takeDamage(poisonDamage);
    }

    @Override
    public String getName() {
        return "Poison";
    }

    @Override
    public void stack(Effect other) {
        if (other instanceof poison) {
            this.poisonDamage += ((poison) other).poisonDamage;
        }
    }
}
