package Logic.Map;

import FrontEnd.MessageCallback;
import Logic.Objects.Enemy.Trap;
import Logic.Objects.PlayerType.Player;

import java.util.*;

public class Board implements GetTileInPosition {
    //we may only have 1 board each time anyway
    private List<Tile> board;
    private MessageCallback messageCallback;

    public Board() {
    }

    public Board(List<Tile> board, Player player) {
        this.board = board;
    }

    public void initialize(List<Tile> board, Player player, MessageCallback messageCallback) {
        this.board = board;
        this.messageCallback = messageCallback;
    }

    public void removeEnemy(Tile tile) {
        board.remove(tile);
    }

    @Override
    public Tile tileInPosition(Position position) {
        for (Tile tile : board) {
            if (tile.getPosition().equals(position))
                return tile;
        }
        return null;
    }


    public char[][] getBoard(int height, int width) {
        char[][] charBoard = new char[height][width];

        for (Tile tile : board) {

            charBoard[tile.getPosition().getY()][tile.getPosition().getX()] = tile.getTile();
        }
        return charBoard;
    }

    public void addEmpty(Position playerLastPosition) {
        board.add(new Empty('.',playerLastPosition));
    }
}

