package Logic.GameEngine;

import Logic.Objects.Enemy.Enemy;
import Logic.Objects.PlayerType.Player;

import java.util.List;

public interface VisibleEnemies {
    List<Enemy> getVisibleEnemiesInRange(Player player,int range);
}
