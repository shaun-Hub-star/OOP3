package Logic.Objects.Enemy;

import Logic.GameEngine.OnEnemyDeathCallBack;
import Logic.Map.*;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.DiceRoll;
import Logic.Utility.Health;

public class Monster extends Enemy {
    private int visionRange;


    public Monster(char tile, String name, Health health, int attackPoints, int defencePoints,
                   int experience, int visionRange, Position position) {
        super(tile, name, attackPoints, defencePoints, health, position, experience);
        this.visionRange = visionRange;
    }

    enum Directions {
        STAY,
        UP,
        DOWN,
        RIGHT,
        LEFT
    }


    public Position getPosition() {
        return super.getPosition();
    }

    @Override
    public boolean isEnemy() {
        return true;
    }


    /*@Override
    public void accept(Player player) {
        //combat with player
        player.visit(this);
    }*/

    @Override
    public void accept(Enemy enemy) {
        super.accept(enemy);
    }


    @Override
    public void visit(Wall wall) {
        super.visit(wall);
    }

    @Override
    public void visit(Empty empty) {
        move(empty);
    }


    @Override
    public void makeTurn() {
        Tile tile;
        if (this.getPosition().range(player.getPlayer().getPosition()) < this.visionRange) {
            if (monsterCastAbility()) {
                int dx = this.getPosition().getX() - player.getPlayer().getPosition().getX();
                int dy = this.getPosition().getY() - player.getPlayer().getPosition().getY();
                if (Math.abs(dx) > Math.abs(dy)) {
                    if (dx > 0) {
                        tile = tilePosition.tileInPosition(new Position(this.getPosition().getX() - 1, this.getPosition().getY()));

                    } else {
                        tile = tilePosition.tileInPosition(new Position(this.getPosition().getX() + 1, this.getPosition().getY()));

                    }

                } else {
                    if (dy > 0) {
                        tile = tilePosition.tileInPosition(new Position(this.getPosition().getX(), this.getPosition().getY() - 1));

                    } else {
                        tile = tilePosition.tileInPosition(new Position(this.getPosition().getX(), this.getPosition().getY() + 1));
                    }

                }
                tile.accept(this);
            }
        }
        else {
            //default cases - out of vision
            DiceRoll diceRoll = DiceRoll.getInstance();
            int k = diceRoll.roll(5);
            switch (Directions.values()[k]) {
                case UP:
                    tile = tilePosition.tileInPosition(new Position(this.getPosition().getX(), this.getPosition().getY() - 1));
                    tile.accept(this);
                    break;
                case DOWN:
                    tile = tilePosition.tileInPosition(new Position(this.getPosition().getX(), this.getPosition().getY() + 1));
                    tile.accept(this);
                    break;
                case RIGHT:
                    tile = tilePosition.tileInPosition(new Position(this.getPosition().getX() + 1, this.getPosition().getY() - 1));
                    tile.accept(this);
                    break;
                case LEFT:
                    tile = tilePosition.tileInPosition(new Position(this.getPosition().getX() - 1, this.getPosition().getY()));
                    tile.accept(this);
                    break;
                case STAY:

                    break;
            }
        }
    }

    protected boolean monsterCastAbility() {
        return true;
    }


}
