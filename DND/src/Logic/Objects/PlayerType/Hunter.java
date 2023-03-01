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

public class Hunter extends Player {
    private final int range;
    private int arrowsCount;
    private int ticksCount = 0;

    private static final int attackBonusUponLevelUp = 2;
    private static final int defenceBonusUponLevelUp = 1;
    private static final int arrowsBonus = 10;

    public Hunter(char tile, String name, int attackPoints, int defencePoint, Position playerPosition,
                  Health health, int range) {
        super(tile, name, attackPoints, defencePoint, playerPosition, health);
        this.arrowsCount = arrowsBonus * super.getLevel();
        this.range = range;
    }

    @Override
    public void levelUp() {
        super.levelUp();
        arrowsCount += arrowsBonus * super.getLevel();
        super.setAttackUponLevelUp(super.getLevel(), attackBonusUponLevelUp);
        super.setDefenseUponLevelUp(super.getLevel(), defenceBonusUponLevelUp);
        try {
            messageCallback.send("+" + arrowsBonus * super.getLevel() + " arrows");
        } catch (Exception e) {
            //for testing
        }
    }

    public int getArrowsForTesting() {
        return this.arrowsCount;
    }
    public int getTicksForTesting() {
        return this.ticksCount;
    }

    @Override
    public void gameTick() {
        if (ticksCount == 10) {
            arrowsCount += super.getLevel();
            ticksCount = 0;
        } else ticksCount += 1;
    }


    @Override
    public void activateSpacialAbility() {
        List<Enemy> tileList = visibleEnemies.getVisibleEnemiesInRange(this, range);
        if (arrowsCount == 0 | tileList.size() == 0) {
            messageCallback.send(describe());
            return;
        }
        arrowsCount -= 1;

        Enemy minTile = tileList.get(0);
        double minDistance = minTile.getPosition().range(this.getPosition());
        for (Enemy tile : tileList) {
            double distance = tile.getPosition().range(this.getPosition());
            if (distance < minDistance) {
                minDistance = distance;
                minTile = tile;
            }
        }
        minTile.acceptSpell(this);
        messageCallback.send(this.getName() + " casted an ability on " + minTile.getName());
        messageCallback.send(describe());
    }

    @Override
    public void castSpell(Enemy enemy) {
        enemy.damageTaken(this.getAttackPoints());
        if (enemy.getHealth().isDead())
            super.gainExperience(enemy.getExperience());
    }

    @Override
    public String describe() {
        return String.format("%s\t\tRange: %d\t\tArrows: %d", super.describe(), this.range, this.arrowsCount);
    }
}
