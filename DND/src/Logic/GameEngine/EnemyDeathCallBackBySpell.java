package Logic.GameEngine;

import Logic.Map.Position;
import Logic.Objects.Enemy.Enemy;

public interface EnemyDeathCallBackBySpell {

    void onEnemyDeathBySpell(Enemy enemy);
}
