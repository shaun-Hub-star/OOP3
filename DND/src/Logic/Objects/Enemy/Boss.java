package Logic.Objects.Enemy;

import Logic.Map.Position;
import Logic.Utility.Health;

public class Boss extends Monster implements HeroicUnit {
    private int abilityFrequency;
    private int combatTicks;

    public Boss(char tile, String name, Health health, int attackPoints, int defencePoints,
                int experience, int visionRange, Position position, int abilityFrequency) {
        super(tile, name, health, attackPoints, defencePoints, experience, visionRange, position);
        this.combatTicks = 0;
        this.abilityFrequency = abilityFrequency;
    }

    @Override
    protected boolean monsterCastAbility() {
        combatTicks();
        if (combatTicks == abilityFrequency) {
            combatTicks = 0;
            super.combat(super.player.getPlayer());
            return false;
        }
        return true;
    }

    private void combatTicks() {
        combatTicks+=1;
    }
}
