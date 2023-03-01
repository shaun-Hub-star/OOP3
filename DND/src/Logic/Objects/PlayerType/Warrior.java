package Logic.Objects.PlayerType;

import Logic.GameEngine.OnPlayerDeathCallBack;
import Logic.Map.Board;
import Logic.Map.GetTileInPosition;
import Logic.Map.Position;
import Logic.Map.Tile;
import Logic.Objects.Enemy.Enemy;
import Logic.Utility.DiceRoll;
import Logic.Utility.Health;

import java.util.List;

public class Warrior extends Player {
    private int abilityCooldown;
    private int remainingCooldown;
    private static final int abilityRange = 3;
    private static final int healthBonusUponLevelUp = 5;
    private static final int attackBonusUponLevelUp = 2;
    private static final int defenceBonusUponLevelUp = 1;


    public Warrior(char tile, String name, int attackPoints, int defencePoint, Position playerPosition,
                   Health health, int remainingCooldown) {
        super(tile, name, attackPoints, defencePoint, playerPosition, health);
        this.abilityCooldown = remainingCooldown;
        this.remainingCooldown = 0;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        this.remainingCooldown = 0;
        super.getHealth().setHealthUponLevelUp(super.getLevel(), healthBonusUponLevelUp);
        super.setAttackUponLevelUp(super.getLevel(), attackBonusUponLevelUp);
        super.setDefenseUponLevelUp(super.getLevel(), defenceBonusUponLevelUp);
    }

    @Override
    public void gameTick() {
        if (remainingCooldown > 0)
            this.remainingCooldown -= 1;
    }

    @Override
    public void activateSpacialAbility() {
        if (remainingCooldown != 0) {
            messageCallback.send(describe());
            return;
        }
        remainingCooldown = abilityCooldown;
        Health health = super.getHealth();
        int firstPart = health.getHealthAmount() + 10 * super.getDefencePoints();
        int secondPart = health.getHealthPool();

        health.setHealthAmount(Math.min(firstPart, secondPart));
        //TODO hit an random enemy in range of 3.
        List<Enemy> tileList = visibleEnemies.getVisibleEnemiesInRange(this, abilityRange);
        DiceRoll diceRoll = DiceRoll.getInstance();
        int roll = diceRoll.roll(tileList.size() );
        Tile tile = tileList.get(roll);//random place
        tile.acceptSpell(this);
        messageCallback.send(this.getName() + " casted an ability on " + tileList.get(roll).getName());
        messageCallback.send(describe());

    }

    @Override
    public void castSpell(Enemy enemy) {
        enemy.damageTaken((int) (0.1 * this.getHealth().getHealthPool()));
        if (enemy.getHealth().isDead())
            super.gainExperience(enemy.getExperience());
    }

    @Override
    public String describe() {
        return String.format("%s\t\tCooldown: %d/%d", super.describe(), this.remainingCooldown, this.abilityCooldown);
    }
}
