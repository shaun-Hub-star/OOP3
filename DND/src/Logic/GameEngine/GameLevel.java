package Logic.GameEngine;

import FrontEnd.MessageCallback;
import FrontEnd.PrintBoard;
import Logic.Map.Board;
import Logic.Map.Position;
import Logic.Map.Tile;
import Logic.Objects.Enemy.Enemy;
import Logic.Objects.PlayerType.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class GameLevel implements OnEnemyDeathCallBack, OnPlayerDeathCallBack, VisibleEnemies, EnemyDeathCallBackBySpell {
    private Board gameBoard;
    private Player player;
    private List<Enemy> enemies;
    private MessageCallback messageCallback;
    private PrintBoard printBoard;
    private boolean isPlayerDead = false;
    private int height;
    private int width;

    public GameLevel(int height, int width) {
        this.height = height;
        this.width = width;
    }

    //gameLevel constructor
    public GameLevel(Board gameBoard, Player player, List<Enemy> enemies) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.enemies = enemies;
    }

    public void initialize(Board gameBoard, Player player, List<Enemy> enemies, MessageCallback messageCallback, PrintBoard printBoard) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.enemies = enemies;
        this.messageCallback = messageCallback;
        this.printBoard = printBoard;
    }

    @Override
    public void onEnemyDeathBySpell(Enemy enemy) {
        enemies.remove(enemy);
        gameBoard.removeEnemy(enemy);
        gameBoard.addEmpty(enemy.getPosition());
    }

    @Override
    public void onPlayerDeath(Player player) {
        //end game
        this.isPlayerDead = true;
        printLostScreen();
    }

    @Override
    public void onEnemyDeath(Enemy enemy, Position playerLastPosition) {
        enemies.remove(enemy);
        gameBoard.removeEnemy(enemy);
        gameBoard.addEmpty(playerLastPosition);
    }

    public boolean isLevelFinished() {
        return enemies.size() == 0;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s", gameBoard, player.describe());
    }


    @Override
    public List<Enemy> getVisibleEnemiesInRange(Player player, int range) {
        List<Enemy> enemyList = new ArrayList<>();

        for (Enemy enemy : enemies) {
            if (enemy.getPosition().range(player.getPosition()) < range) {
                enemyList.add(enemy);
            }
        }
        return enemyList;
    }


    public void printBoardProvider() {
        printBoard.print(gameBoard.getBoard(height, width));
    }

    public void notifyAllEnemies() {
        for (Enemy enemy : enemies) {
            if (!isPlayerDead)
                enemy.makeTurn();
        }
        //enemies.stream().forEach(x -> x.makeTurn());

        printBoardProvider();

    }

    public Tile getTileInPosition(Position position) {
        return this.gameBoard.tileInPosition(position);
    }

    public boolean getIsPlayerDead() {
        return isPlayerDead;
    }

    private void printLostScreen() {
        messageCallback.send("\n" +
                "_|    _|        _|_|        _|    _|      _| \n" +
                "_|    _|      _|    _|      _|    _|      _| \n" +
                "_|_|_|_|      _|_|_|_|      _|_|_|_|      _| \n" +
                "_|    _|      _|    _|      _|    _|         \n" +
                "_|    _|      _|    _|      _|    _|      _| \n\n" +

                "_|      _|        _|_|        _|    _|          _|_|_|        _|_|_|_|      _|_|_|            _|    _|      _|_|_|      _|_|_|          _|_|        _|  \n" +
                "  _|  _|        _|    _|      _|    _|          _|    _|      _|            _|    _|          _|  _|          _|        _|    _|      _|    _|      _|  \n" +
                "    _|          _|    _|      _|    _|          _|    _|      _|_|_|        _|    _|          _|_|            _|        _|    _|      _|    _|      _|  \n" +
                "    _|          _|    _|      _|    _|          _|    _|      _|            _|    _|          _|  _|          _|        _|    _|      _|    _|          \n" +
                "    _|            _|_|          _|_|            _|_|_|        _|_|_|_|      _|_|_|            _|    _|      _|_|_|      _|_|_|          _|_|        _|  \n");
    }
}
