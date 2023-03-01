package Logic.Objects.PlayerType;

import FrontEnd.MessageCallback;
import Logic.GameEngine.OnEnemyDeathCallBack;
import Logic.GameEngine.OnPlayerDeathCallBack;
import Logic.GameEngine.VisibleEnemies;
import Logic.Map.*;
import Logic.Objects.Enemy.Boss;
import Logic.Objects.Enemy.Enemy;
import Logic.Utility.DiceRoll;
import Logic.Utility.Health;


public abstract class Player extends Unit implements SpecialAbility, InteractedWith, Interact, CastSpell, Movement {

    protected VisibleEnemies visibleEnemies;
    protected OnPlayerDeathCallBack deathCallBack;
    private int level;
    private static final int experienceConst = 50;
    private static final int healthBonusUponLevelUp = 10;
    private static final int attackBonusUponLevelUp = 4;
    private static final int defenceBonusUponLevelUp = 1;

    public Player(char tile, String name, int attackPoints, int defencePoint, Position playerPosition,
                  Health health) {
        super(tile, name, attackPoints, defencePoint, health, playerPosition);
        this.level = 1;
        super.experience = 0;
    }

    public void initialize(OnPlayerDeathCallBack deathCallBack, GetTileInPosition tileInPosition, VisibleEnemies visibleEnemies, MessageCallback messageCallback) {
        super.initialize(tileInPosition, messageCallback);
        this.deathCallBack = deathCallBack;
        this.visibleEnemies = visibleEnemies;
    }

    private boolean isLevelUp() {
        return experience >= 50 * level;
    }

    public void levelUp() {
        level += 1;
        experience -= experienceConst * level;
        super.getHealth().setHealthUponLevelUp(level, healthBonusUponLevelUp);
        super.setAttackUponLevelUp(level, attackBonusUponLevelUp);
        super.setDefenseUponLevelUp(level, defenceBonusUponLevelUp);
        try {
            messageCallback.send(super.getName() + " reached level " +
                    this.level + ": +" + healthBonusUponLevelUp * level +
                    " health, +" + attackBonusUponLevelUp * level + " attack points, +" + defenceBonusUponLevelUp * level + " defence points");
        }catch (Exception e){
            //for testing
        }
    }
    public abstract void gameTick();

    protected void gainExperience(int experience) {
        this.experience += experience;
        if (isLevelUp()) levelUp();
    }

    public boolean isPlayerDead() {
        return super.isUnitDead();
    }

    protected int getLevel() {
        return level;
    }
    public int getAttackForTesting(){
        return super.getAttackPoints();
    }
    @Override
    public void accept(Player player) {
        //left here in case of adding option for multiplayer
    }

    @Override
    public void accept(Enemy enemy) {
        //attacked by an enemy
        enemy.visit(this);
        if (isPlayerDead()) {
            super.tile = 'X';
            deathCallBack.onPlayerDeath(this);
        }
    }

    @Override
    public void visit(Enemy enemy) {
        //enemy roll defence
        //we roll attack
        super.combat(enemy);
        if (enemy.getHealth().isDead()) {
            this.gainExperience(enemy.getExperience());

            this.setPosition(new Position(enemy.getPosition().getX(),enemy.getPosition().getY()));
        }
    }


    @Override
    public void visit(Player player) {
        //here in case of adding player vs player mode
    }

    @Override
    public void visit(Wall wall) {
        return;
    }

    @Override
    public void visit(Empty empty) {
        move(empty);
    }

    @Override
    public boolean isEnemy() {
        return false;
    }

    @Override
    public void move(Tile tile) {
        Position position = new Position(tile.getPosition().getX(), tile.getPosition().getY());
        tile.setPosition(this.getPosition());
        this.setPosition(position);
        messageCallback.send(describe());
        gameTick();
    }

    public String levelUpRequirement() {
        Integer x = level * 50;
        return x.toString();
    }

    //TODO:
    @Override
    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%s", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }

}
