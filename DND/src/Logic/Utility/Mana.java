package Logic.Utility;

public class Mana {
    private int currentMana;
    private int manaPool;


    public static final int MANA_BONUS_UPON_LEVEL_UP = 25;

    public Mana(int manaPool) {
        this.currentMana = manaPool / 4;
        this.manaPool = manaPool;
    }

    public void setManaOnGameTick(int level) {
        currentMana = Math.min(manaPool, currentMana + level);
    }

    public void setManaOnLevelUp(int level) {
        currentMana = Math.min(currentMana + manaPool / 4, manaPool);
        manaPool += MANA_BONUS_UPON_LEVEL_UP * level;
    }

    public void setCurrentManaOnAbilityCast(int spellCost) {
        currentMana -= spellCost;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public String toString() {
        return currentMana + "/" + manaPool;
    }

}
