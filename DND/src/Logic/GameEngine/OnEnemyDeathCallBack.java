package Logic.GameEngine;

import Logic.Map.Position;
import Logic.Objects.Enemy.Enemy;

public interface OnEnemyDeathCallBack {
    void onEnemyDeath(Enemy enemy, Position playerLastPosition);
}
