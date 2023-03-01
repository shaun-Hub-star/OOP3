package Logic.Map;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double range(Position p) {
        return Math.sqrt(Math.pow((p.getX() - x), 2) + Math.pow((p.getY() - y), 2));
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y -= dy;
    }

    public void setPosition(Position position) {
        this.x = position.getX();
        this.y = position.getY();
    }


    public boolean equals(Position position) {
        return x == position.x &&
                y == position.y;
    }

}
