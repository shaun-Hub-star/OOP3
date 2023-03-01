package Logic.Objects.PlayerType;

import FrontEnd.CLI;
import FrontEnd.MessageCallback;
import Logic.Map.Position;
import Logic.Objects.Enemy.Enemy;
import Logic.Objects.Enemy.Monster;
import Logic.Utility.Health;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HunterTest {
    private Hunter hunter;
    private Enemy monster;
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
        hunter = new Hunter('@', "hunter", 15, 15,
                new Position(0, 0), new Health(250, 250), 2);
        monster = new Monster('M', "monster", new Health(50, 50),
                10, 10, 50, 2, new Position(3, 3));
        hunter.gainExperience(250);
    }


    @Test
    void levelUp_Health_Test_success() {
        //hunter.levelUp();
        assertEquals(250+healthBonusUponLevelUp*hunter.getLevel(),hunter.getHealth().getHealthPool());
    }

    @Test
    void levelUp_Experience_Test_success() {
        assertEquals(250-experienceConst*hunter.getLevel(),hunter.getExperience());
    }

    @Test
    void levelUp_Attack_Test_success() {
        assertEquals(15+attackBonusUponLevelUp*hunter.getLevel()+
                             attackBonusUponLevelUpHunter*hunter.getLevel()
                ,hunter.getAttackForTesting());

    }

    @Test
    void levelUp_Defence_Test_success() {
        assertEquals(15+defenceBonusUponLevelUp*hunter.getLevel()+
                        defenceBonusUponLevelUpHunter*hunter.getLevel()
                ,hunter.getDefencePoints());
    }

    @Test
    void levelUp_ArrowsCount_Test_success() {
        assertEquals(10+arrowsBonus*hunter.getLevel()
                ,hunter.getArrowsForTesting());

    }

    @Test
    void gameTick_Test1() {
        int arrowsCount=hunter.getArrowsForTesting();
        int gameTick=0;
        boolean arrowsCountCheck=true;
        boolean gameTickCheck=true;
        for (int i=1;i<=100;i++){
            hunter.gameTick();
            if(gameTick==10) {
                gameTick=0;
                arrowsCount += hunter.getLevel();
                if(arrowsCount!=hunter.getArrowsForTesting())
                    arrowsCountCheck=false;
                if(hunter.getTicksForTesting()!=gameTick)
                    gameTickCheck=false;
            }
            else {
                gameTick++;
                if(hunter.getTicksForTesting()!=gameTick)
                    gameTickCheck=false;
            }
        }
        assertTrue(gameTickCheck&arrowsCountCheck);
    }

    @Test
    void gainExperience_Test1() {
        int experience=hunter.getExperience();
        int level=hunter.getLevel();
        boolean experienceCheck=true;
        boolean levelCheck=true;
        for(int i=1;i<=100;i++){
            experience+=i;
            hunter.gainExperience(i);
            if(experience >= 50 * level){
                level++;
                experience -= 50 * level;
                if(hunter.getLevel()!=level){
                    levelCheck=false;
                }
                if(experience!=hunter.getExperience())
                    experienceCheck=false;
            }
            else {
                if(experience!=hunter.getExperience())
                    experienceCheck=false;
            }
        }
        assertTrue(levelCheck&experienceCheck);
    }

    @Test
    void acceptEnemy_Test1() {

    }

    @Test
    void visitEnemy_Test1() {

    }

    @Test
    void move_Test1() {

    }

    @Test
    void levelUpRequirement_Test1() {

    }

    @Test
    void activateSpacialAbility_Test1() {

    }

}