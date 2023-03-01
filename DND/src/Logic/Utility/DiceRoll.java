package Logic.Utility;


import java.util.Random;

public class DiceRoll {

    private static DiceRoll instance = null;
    //private int diceRollValue;
    private Random rand;

    private DiceRoll() {
        //this.diceRollValue = 1;
        this.rand=new Random();
    }

    public static DiceRoll getInstance() {
        if (instance == null) {
            instance = new DiceRoll();
        }
        return instance;
    }


    public int roll(int max) {
        return this.rand.nextInt(max);
//        this.diceRollValue += 1;
//        return (diceRollValue % 6) + 1;
    }
}
