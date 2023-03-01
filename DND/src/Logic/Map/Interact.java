package Logic.Map;

import Logic.Objects.Enemy.Enemy;
import Logic.Objects.PlayerType.Player;

public interface Interact {

    public void visit(Enemy enemy);

    public void visit(Empty empty);

    public void visit(Wall wall);

    public void visit(Player player);
}
