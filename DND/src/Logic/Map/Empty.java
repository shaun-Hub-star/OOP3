package Logic.Map;

import Logic.Objects.Enemy.Enemy;
import Logic.Objects.PlayerType.Player;

public class Empty extends Tile {
    public Empty(char tile, Position position) {
        super(tile,position);
    }

    @Override
    public void accept(Enemy enemy) {
        enemy.visit(this);
    }

    @Override
    public void accept(Player player) {
        player.visit(this);
    }

    @Override
    public boolean isEnemy() {
        return false;
    }
}
