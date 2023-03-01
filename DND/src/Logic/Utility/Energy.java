package Logic.Utility;

public class Energy {
    public static final int MAX_ENERGY = 100;
    private static final int bonusEnergyOnGameTick=10;
    private int currentEnergy;

    public Energy(){
        this.currentEnergy = MAX_ENERGY;
    }
    public void setCurrentEnergyOnGameTick(){
        this.currentEnergy = Math.min(currentEnergy+bonusEnergyOnGameTick,MAX_ENERGY);
    }
    public void setCurrentEnergyOnAbilityCast(int cost){
        this.currentEnergy -= cost;
    }


    public boolean canCastAbility(int cost){
        return currentEnergy >= cost;
    }


    public void setCurrentEnergyUponLevelUp(){
        this.currentEnergy = MAX_ENERGY;
    }
    public String toString(){
        return currentEnergy+"/"+MAX_ENERGY;
    }
}
