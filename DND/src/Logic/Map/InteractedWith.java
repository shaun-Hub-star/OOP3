package Logic.Map;

import Logic.Objects.Enemy.Enemy;
import Logic.Objects.PlayerType.Player;

public interface InteractedWith {
    public void accept(Enemy enemy);
    public void accept(Player player);
}
