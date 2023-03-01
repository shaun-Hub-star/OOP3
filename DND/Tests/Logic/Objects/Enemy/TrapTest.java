package Logic.Objects.Enemy;

import FrontEnd.CLI;
import Logic.Map.*;
import Logic.Objects.PlayerType.Hunter;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.Health;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TrapTest {
    Board board;
    private Player hunter;
    private Trap trap;
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
        hunter = new Hunter('@', "hunter", 15, 1,
                new Position(1, 2), new Health(250, 250), 2);
        trap = new Trap('T', "trap", 40, 10, new Health(50, 50),
                10, 2, 1, new Position(2, 1));
        Wall wall1 = new Wall('#', new Position(0, 0));
        Wall wall2 = new Wall('#', new Position(0, 1));
        Wall wall3 = new Wall('#', new Position(0, 2));
        Wall wall4 = new Wall('#', new Position(0, 3));
        Wall wall5 = new Wall('#', new Position(1, 3));
        Wall wall6 = new Wall('#', new Position(2, 3));
        Wall wall7 = new Wall('#', new Position(3, 3));
        Wall wall8 = new Wall('#', new Position(3, 2));
        Wall wall9 = new Wall('#', new Position(3, 1));
        Wall wall10 = new Wall('#', new Position(3, 0));
        Wall wall11 = new Wall('#', new Position(2, 0));
        Wall wall12 = new Wall('#', new Position(1, 0));

        Empty empty1 = new Empty('.', new Position(1, 1));
        Empty empty2 = new Empty('.', new Position(2, 2));
        List<Tile> boardList = new ArrayList<>();

        boardList.add(wall1);
        boardList.add(wall2);
        boardList.add(wall3);
        boardList.add(wall4);
        boardList.add(wall5);
        boardList.add(wall6);
        boardList.add(wall7);
        boardList.add(wall8);
        boardList.add(wall9);
        boardList.add(wall10);
        boardList.add(wall11);
        boardList.add(wall12);
        boardList.add(empty1);
        boardList.add(empty2);
        boardList.add(hunter);
        boardList.add(trap);

        board = new Board(boardList, hunter);


    }

    @Test
    void walkToTrap() {

    }


    @Test
    void attackPlayer_test1_Success() {
        trap.combat(hunter);
        assertTrue(hunter.getHealth().getHealthAmount() < 250);
    }

    @Test
    void attackPlayer_combat_test2_Fail() {
        hunter.setDefenceForTest(100);
        trap.setAttackPointsForTest(1);
        trap.combat(hunter);
        assertFalse(hunter.getHealth().getHealthAmount() < 250);
    }

    @Test
    void visibilityTrap_test() {
        boolean invisible = false;
        boolean visible = false;
        for (int i = 0; i < 15; i++) {
            trap.setVisibility();
            if (!trap.isVisible()) {
                invisible = true;

            }
            if (invisible & trap.isVisible()) {
                visible = true;
            }
        }
        assertTrue(invisible & visible);

    }

}
