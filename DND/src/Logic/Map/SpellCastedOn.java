package Logic.Map;

import Logic.Objects.PlayerType.Player;

public interface SpellCastedOn {
    public default void acceptSpell(Player player){};

}
