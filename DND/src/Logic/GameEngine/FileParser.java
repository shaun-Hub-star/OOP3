package Logic.GameEngine;

import FrontEnd.MessageCallback;
import FrontEnd.PrintBoard;
import Logic.Map.*;
import Logic.Objects.Enemy.Boss;
import Logic.Objects.Enemy.Enemy;
import Logic.Objects.Enemy.Monster;
import Logic.Objects.Enemy.Trap;
import Logic.Objects.PlayerType.Player;
import Logic.Utility.Health;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileParser implements GetPlayer {
    private Player player;
    private PrintBoard printBoard;
    private MessageCallback messageCallBack;
    private GameLevel gameLevel;

    public FileParser(Player player, PrintBoard printBoard, MessageCallback messageCallback) {
        this.player = player;
        this.printBoard = printBoard;
        this.messageCallBack = messageCallback;
    }

    public GameLevel parseLevel(File levelFile) {
        List<String> lines = readAllLines(levelFile.getPath());
        char[][] boardToPrint = new char[lines.size()][lines.get(0).length()];
        GameLevel gameLevel = new GameLevel(lines.size(), lines.get(0).length());
        this.gameLevel = gameLevel;
        Board board = new Board();
        Pair<List<Tile>, List<Enemy>> pair = convertListStringToTile(lines);
        board.initialize(pair.getItem1(), player, messageCallBack);
        this.gameLevel.initialize(board, player, pair.getItem2(), messageCallBack, printBoard);
        player.initialize(gameLevel, board, this.gameLevel, messageCallBack);
        initializeEnemies(pair.getItem2(), board, this, this.gameLevel, messageCallBack, this.gameLevel);
        return this.gameLevel;
    }


    private Pair<List<Tile>, List<Enemy>> convertListStringToTile(List<String> lines) {
        List<Tile> tileList = new ArrayList<>();
        List<Enemy> enemyList = new ArrayList<>();
        Enemy enemy;
        int x = 0, y = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                Position currentPosition = new Position(x, y);
                char character = line.charAt(i);
                switch (character) {
                    case '.':
                        tileList.add(new Empty(character, currentPosition));
                        break;
                    case '#':
                        tileList.add(new Wall(character, currentPosition));
                        break;
                    case '@':
                        player.setPosition(currentPosition);
                        tileList.add(this.player);
                        break;
                    case 's':
                        enemy = new Monster('s', "Lannister Solider", new Health(80, 80),
                                8, 3, 25, 3, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'k':
                        enemy = new Monster('k', "Lannister Knight", new Health(200, 200),
                                14, 8, 50, 4, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'q':
                        enemy = new Monster('q', "Queen's Guard", new Health(400, 400),
                                20, 15, 100, 5, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'z':
                        enemy = new Monster('z', "Wright", new Health(600, 600),
                                30, 15, 100, 3, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'b':
                        enemy = new Monster('b', "Bear-Wright", new Health(1000, 1000),
                                75, 30, 250, 4, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'g':
                        enemy = new Monster('g', "Giant-Wright", new Health(1500, 1500),
                                100, 40, 500, 5, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'w':
                        enemy = new Monster('w', "White Walker", new Health(2000, 2000),
                                150, 50, 1000, 6, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'B':
                        enemy = new Trap('B', "Bonus Trap", 1, 1,
                                new Health(1, 1), 250, 1, 5, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'Q':
                        enemy = new Trap('Q', "Queen's Trap", 250, 50,
                                new Health(10, 10), 100, 3, 7, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'D':
                        enemy = new Trap('D', "Death Trap", 500, 100,
                                new Health(20, 20), 250, 1, 10, currentPosition);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'M':
                        enemy = new Boss('M', "The mountain", new Health(1000, 1000),
                                60, 25, 500, 6, currentPosition, 5);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'C':
                        enemy = new Boss('C', "Queen Cersei", new Health(100, 100),
                                10, 10, 1000, 1, currentPosition, 8);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                    case 'K':
                        enemy = new Boss('K', "Night's King", new Health(5000, 5000),
                                300, 150, 5000, 8, currentPosition, 3);
                        tileList.add(enemy);
                        enemyList.add(enemy);
                        break;
                }
                x += 1;
            }
            x = 0;
            y += 1;
        }
        return new Pair(tileList, enemyList);
    }

    private void initializeEnemies(List<Enemy> enemyList, GetTileInPosition tilePosition,
                                   GetPlayer player, OnEnemyDeathCallBack deathCallBack,
                                   MessageCallback messageCallback, EnemyDeathCallBackBySpell enemyDeathCallBackBySpell) {
        for (Enemy enemy : enemyList)
            enemy.initialize(tilePosition, player, deathCallBack, messageCallback, enemyDeathCallBackBySpell);
    }

    public List<String> readAllLines(String path) {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\n" +
                    e.getStackTrace());
        }
        return lines;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

}
