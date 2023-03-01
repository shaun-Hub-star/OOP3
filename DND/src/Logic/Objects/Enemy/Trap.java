package Logic.Objects.Enemy;

import Logic.GameEngine.OnEnemyDeathCallBack;
import Logic.Map.*;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.Health;
import org.w3c.dom.ranges.Range;

import java.util.Random;

public class Trap extends Enemy implements Movement {
    private boolean visible;
    private int visibilityTime;
    private int invisibilityTime;
    private int ticksCount;

    public Trap(char tile, String name, int attackPoints, int defencePoints, Health health,int experience,
                int visibilityTime, int invisibilityTime,  Position position) {
        super(tile, name, attackPoints, defencePoints, health, position, experience);
        this.visible = true;
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
    }


    @Override
    public boolean isEnemy() {
        return visible;
    }

    public void setVisibility(){
        visible = ticksCount < visibilityTime;
        if (ticksCount == (visibilityTime + invisibilityTime)) {
            ticksCount = 0;
        } else {
            ticksCount = ticksCount + 1;
        }
    }
    public boolean isVisible(){
        return visible;
    }
    @Override
    public void makeTurn() {
        setVisibility();
        if (player.getPlayer().getPosition().range(super.getPosition()) < 2) {
            super.visit(player.getPlayer());
        }
    }


    @Override
    public char getTile() {
        if(visible)
            return super.getTile();
        return '.';
    }


}
