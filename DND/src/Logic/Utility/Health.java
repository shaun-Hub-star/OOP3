package Logic.Utility;

public class Health {
    private int healthPool;
    private int healthAmount;

    public Health(int healthPool, int healthAmount) {
        this.healthPool = healthPool;
        this.healthAmount = healthAmount;
    }

    public void setHealthUponLevelUp(int level, int scalar) {
        healthPool += scalar * level;
        healthAmount = healthPool;
    }

    public void setHealthAmount(int newHealthAmount) {
        this.healthAmount = newHealthAmount;
    }

    public int getHealthAmount() {
        return this.healthAmount;
    }

    public int getHealthPool() {
        return this.healthPool;
    }

    public boolean isDead() {
        return healthAmount <= 0;
    }

    public String toString() {
        return healthAmount + "/" + healthPool;
    }

}
