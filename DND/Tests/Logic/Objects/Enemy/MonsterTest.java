package Logic.Objects.Enemy;

import FrontEnd.CLI;
import Logic.Map.Position;
import Logic.Objects.PlayerType.Hunter;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.Health;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonsterTest {
    private Player hunter;
    private Monster monster;
    private int experienceConst = 50;
    private int healthBonusUponLevelUp = 10;
    private int attackBonusUponLevelUp = 4;
    private int defenceBonusUponLevelUp = 1;
    private int attackBonusUponLevelUpHunter = 2;
    private int defenceBonusUponLevelUpHunter = 1;
    private int arrowsBonus = 10;
    @BeforeEach
    void setUp() {
        CLI cli = new CLI();
        int level = 1;
        hunter = new Hunter('@', "hunter", 15, 0,
                new Position(0, 0), new Health(250, 250), 2);
        monster = new Monster('M', "monster", new Health(50, 50),
                20, 10, 50, 2, new Position(0, 2));
    }

    @Test
    void walkToPlayer(){
        hunter.gameTick();
        assertEquals(1,monster.getPosition().range(hunter.getPosition()));
    }


    @Test
    void attackPlayer(){

        //monster.getPosition().setPosition();
        assertEquals(1,monster.getPosition().range(hunter.getPosition()));
    }



}