package Logic.Objects.Enemy;

import FrontEnd.MessageCallback;
import Logic.GameEngine.EnemyDeathCallBackBySpell;
import Logic.GameEngine.OnEnemyDeathCallBack;
import Logic.Map.*;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.Health;

public abstract class Enemy extends Unit implements Interact, InteractedWith, EnemyAI, SpellCastedOn {
    protected GetPlayer player;
    protected OnEnemyDeathCallBack deathCallBack;
    protected EnemyDeathCallBackBySpell deathCallBackBySpell;

    public Enemy(char tile, String name, int attackPoints, int defencePoints,
                 Health health, Position position, int experience) {

        super(tile, name, attackPoints, defencePoints, health, position);
        super.experience = experience;
    }

    public void initialize(GetTileInPosition tilePosition, GetPlayer player, OnEnemyDeathCallBack deathCallBack,
                           MessageCallback messageCallback, EnemyDeathCallBackBySpell deathCallBackBySpell) {
        super.initialize(tilePosition, messageCallback);
        this.player = player;
        this.deathCallBack = deathCallBack;
        this.deathCallBackBySpell = deathCallBackBySpell;
    }

    @Override
    public void visit(Player player) {
        Position playerLastPosition = new Position(player.getPosition().getX(), player.getPosition().getY());
        combat(player);

        if (super.isUnitDead()) {
            deathCallBack.onEnemyDeath(this, playerLastPosition);
        }
    }


    @Override
    public void visit(Enemy enemy) {
        //if 2 enemies run into each other
    }

    @Override
    public void visit(Empty empty) {

    }

    @Override
    public void visit(Wall wall) {
        //default implementation is empty - trap doesnt need interaction with a wall and so does monster just need to stay in the same place

    }

    @Override
    public void accept(Enemy enemy) {
        //will act like when facing a wall
        enemy.visit(this);
    }

    @Override
    public void accept(Player player) {
        Position playerLastPosition = new Position(player.getPosition().getX(), player.getPosition().getY());
        player.visit(this);
        if (super.isUnitDead()) {
            deathCallBack.onEnemyDeath(this, playerLastPosition);
        }
    }

    @Override
    public boolean isEnemy() {
        return true;
    }

    @Override
    public String describe() {
        return String.format("%s\t\tExperience: %d", super.describe(), getExperience());
    }

    @Override
    public void acceptSpell(Player player) {
        player.castSpell(this);
        messageCallback.send(describe());
        if (super.isUnitDead()) {
            deathCallBackBySpell.onEnemyDeathBySpell(this);
        }
    }
    public void damageTaken(int damage){
        this.setHealth(damage);
  //      deathCallBackBySpell.onEnemyDeathBySpell(this);
    }
}
