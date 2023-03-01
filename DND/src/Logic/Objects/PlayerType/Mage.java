package Logic.Objects.PlayerType;

import Logic.GameEngine.OnPlayerDeathCallBack;
import Logic.Map.Board;
import Logic.Map.GetTileInPosition;
import Logic.Map.Position;
import Logic.Map.Tile;
import Logic.Objects.Enemy.Enemy;
import Logic.Utility.Health;
import Logic.Utility.Mana;

import java.util.List;

public class Mage extends Player {

    private int spellPower;
    private Mana mana;
    private int hitCount;
    private int abilityRange;
    private int manaCost;
    private static final int levelUpSpellPowerBonus = 10;

    public Mage(char tile, String name, int attackPoints, int defencePoint, Position playerPosition,
                Health health, Mana mana, int spellPower, int hitCount, int abilityRange, int manaCost) {

        super(tile, name, attackPoints, defencePoint, playerPosition, health);
        this.mana = mana;
        this.spellPower = spellPower;
        this.hitCount = hitCount;
        this.abilityRange = abilityRange;
        this.manaCost = manaCost;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        mana.setManaOnLevelUp(super.getLevel());
        this.spellPower += levelUpSpellPowerBonus * super.getLevel();
        messageCallback.send("+" + Mana.MANA_BONUS_UPON_LEVEL_UP * super.getLevel()+" maximum mana, +" + spellPower+" spell power");
    }

    @Override
    public void gameTick() {
        mana.setManaOnGameTick(super.getLevel());
    }


    @Override
    public void castSpell(Enemy enemy) {
        enemy.damageTaken(spellPower);
        if (enemy.getHealth().isDead())
            super.gainExperience(enemy.getExperience());
    }


    @Override
    public void activateSpacialAbility() {
        if (this.mana.getCurrentMana() < this.manaCost) {
            messageCallback.send(describe());
            return;
        }
        int hits = 0;
        //TODO the while algorithm
        mana.setCurrentManaOnAbilityCast(this.manaCost);
        List<Enemy> tileList = visibleEnemies.getVisibleEnemiesInRange(this, abilityRange);
        if (tileList.size() == 0) messageCallback.send(describe());
        while (hits < hitCount & tileList.size() > 0) {
            //select random//
            tileList.get(hits).acceptSpell(this);//should be random
            messageCallback.send(this.getName()+" casted an ability on "+tileList.get(hits).getName());
            messageCallback.send(describe());
            tileList.remove(hits);
            hits += 1;
        }
        if (tileList.size() == 0) messageCallback.send(describe());
    }

    @Override
    public String describe() {
        return String.format("%s\t\tSpell Power: %d\t\tMana: %s", super.describe(), this.spellPower, this.mana.toString());
    }
}

