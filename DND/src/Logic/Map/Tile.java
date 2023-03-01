package Logic.Map;

public abstract class Tile implements InteractedWith, IsEnemy, SpellCastedOn {
    protected char tile;
    private Position position;

    public Tile(char tile, Position position) {
        this.tile = tile;
        this.position = position;
    }

    public String toString() {
        return "" + tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public char getTile() {
        return this.tile;
    }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", tile);
    }
}
