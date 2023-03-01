package Logic.Map;

import FrontEnd.MessageCallback;
import Logic.Utility.DiceRoll;
import Logic.Utility.Health;

public abstract class Unit extends Tile implements Interact,Movement {
    private String name;
    private int attackPoints;
    private int defencePoints;
    private Health health;
    protected int experience;
    protected GetTileInPosition tilePosition;
    protected MessageCallback messageCallback;


    public Unit(char tile, String name, int attackPoints, int defencePoints,
                Health health, Position position) {
        super(tile, position);
        this.name = name;
        this.attackPoints = attackPoints;
        this.defencePoints = defencePoints;
        this.health = health;

    }

    protected void initialize(GetTileInPosition tilePosition, MessageCallback messageCallback) {
        this.tilePosition = tilePosition;
        this.messageCallback = messageCallback;
    }

    public String getName() {
        return name;
    }


    public Health getHealth() {
        return health;
    }

    public void setHealth(int dealtHealth) {
        health.setHealthAmount(health.getHealthAmount() - dealtHealth);
    }

    protected void setAttackUponLevelUp(int level, int scalar) {
        this.attackPoints += scalar * level;
    }

    protected void setDefenseUponLevelUp(int level, int scalar) {
        this.defencePoints += level * scalar;
    }

    public int getDefencePoints() {
        return defencePoints;
    }

    protected int getAttackPoints() {
        return attackPoints;
    }

    public int getExperience() {
        return this.experience;
    }

    //if player won its true
    public void combat(Unit unit) {
        //enemy roll defence
        //we roll attack
        try {
            messageCallback.send(this.getName() + " engaged in combat with " + unit.getName() + ".");
            messageCallback.send(this.describe());
            messageCallback.send(unit.describe());
        }catch (Exception e){
            //for testing
        }
        DiceRoll diceRoll = DiceRoll.getInstance();
        int attack = diceRoll.roll(getAttackPoints());
        int defence = diceRoll.roll(unit.getDefencePoints());
        try {
            messageCallback.send(this.getName() + " rolled " + attack + " attack points.");
            messageCallback.send(unit.getName() + " rolled " + defence + " defence points.");
        }catch (Exception e){
            //for testing
        }
        if (attack > defence) {
            unit.setHealth(attack - defence);
        }
        try {
            messageCallback.send(this.getName() + " dealt " + ((attack - defence) > 0 ? (attack - defence) : "0")
                    + " damage points to " + unit.getName() + ".");
        }catch (Exception e){
            //for testing
        }
    }

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().toString(),
                getAttackPoints(), getDefencePoints());
    }


    public boolean isUnitDead() {
        return getHealth().isDead();
    }

    @Override
    public void move(Tile tile) {
        Position position = new Position(tile.getPosition().getX(), tile.getPosition().getY());
        tile.setPosition(this.getPosition());
        this.setPosition(position);
    }
    public void setDefenceForTest(int defence){
        this.defencePoints = defence;
    }
    public void setAttackPointsForTest(int attackPoints){
        this.attackPoints = attackPoints;
    }
}
