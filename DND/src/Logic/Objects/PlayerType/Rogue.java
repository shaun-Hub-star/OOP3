package Logic.Objects.PlayerType;

import Logic.GameEngine.OnPlayerDeathCallBack;
import Logic.Map.Board;
import Logic.Map.GetTileInPosition;
import Logic.Map.Position;
import Logic.Map.Tile;
import Logic.Objects.Enemy.Enemy;
import Logic.Utility.DiceRoll;
import Logic.Utility.Energy;
import Logic.Utility.Health;

import java.util.List;

public class Rogue extends Player {
    private int cost;
    private static final int RogueAbilityRange = 2;
    Energy currentEnergy;

    public Rogue(char tile, String name, int attackPoints, int defencePoint, Position playerPosition,
                 Health health, int cost) {
        super(tile, name, attackPoints, defencePoint, playerPosition, health);
        this.cost = cost;
        currentEnergy = new Energy();
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.currentEnergy.setCurrentEnergyUponLevelUp();
    }

    @Override
    public void gameTick() {
        this.currentEnergy.setCurrentEnergyOnGameTick();
    }


    @Override
    public void activateSpacialAbility() {
        if (!currentEnergy.canCastAbility(cost)) {
            messageCallback.send(describe());
            return; //should be a message;
        }
        this.currentEnergy.setCurrentEnergyOnAbilityCast(cost);
        //TODO hit every enemy within range<2
        List<Enemy> enemiesInRange = visibleEnemies.getVisibleEnemiesInRange(this, RogueAbilityRange);
        int i = 0;
        for (Tile tile : enemiesInRange) {
            tile.acceptSpell(this);
            messageCallback.send(this.getName() + " casted an ability on " + enemiesInRange.get(i++).getName());
            messageCallback.send(describe());
        }
        if (enemiesInRange.size() == 0) messageCallback.send(describe());


        //need to set the position as it was before the visits
    }

    @Override
    public void castSpell(Enemy enemy) {
        enemy.damageTaken(this.getAttackPoints());
        if (enemy.getHealth().isDead())
            super.gainExperience(enemy.getExperience());
    }

    @Override
    public String describe() {
        return String.format("%s\t\tEnergy: %s", super.describe(), this.currentEnergy.toString());
    }
}
