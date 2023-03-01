package Logic.GameEngine;

import FrontEnd.CLI;
import FrontEnd.MessageCallback;
import FrontEnd.PrintBoard;
import Logic.Map.Board;
import Logic.Map.Position;
import Logic.Objects.PlayerType.*;
import Logic.Utility.Health;
import Logic.Utility.Mana;

import java.io.File;
import java.util.Scanner;

public class LevelManager {
    private File[] files;
    private MessageCallback messageCallback;
    private FileParser fileParser;
    private Scanner scanner = new Scanner(System.in);
    private Player player;

    public LevelManager(String path) {
        File directoryPath = new File(path);
        files = directoryPath.listFiles();
        CLI cli = new CLI();
        this.messageCallback = cli;
        messageCallback.send("Which player would you like to play?\n");
        showPlayersChoices();
        this.player = selectPlayer(playerValidation(cli));
        fileParser = new FileParser(player, cli, messageCallback);

    }

    public void runLevels() {
        for (File file : files) {
            GameLevel gameLevel = fileParser.parseLevel(file);
            if (!runLevel(gameLevel, player)) break;
            if (!file.equals(files[files.length - 1]))
                printLevelUpScreen();
            else
                printWinScreen();
        }
    }

    private boolean runLevel(GameLevel gameLevel, Player player) {
        gameLevel.printBoardProvider();
        Position position;
        while (!gameLevel.isLevelFinished() && !gameLevel.getIsPlayerDead()) {
            char move = scanner.next().charAt(0);

            Pair<Boolean, Boolean> pair = new Pair<Boolean, Boolean>(true, true);
            switch (move) {
                case 'w':
                    position = new Position(player.getPosition().getX(), player.getPosition().getY() - 1);
                    gameLevel.getTileInPosition(position).accept(player);
                    gameLevel.notifyAllEnemies();
                    break;
                case 'a':
                    position = new Position(player.getPosition().getX() - 1, player.getPosition().getY());
                    gameLevel.getTileInPosition(position).accept(player);
                    gameLevel.notifyAllEnemies();

                    break;
                case 'd':
                    position = new Position(player.getPosition().getX() + 1, player.getPosition().getY());
                    gameLevel.getTileInPosition(position).accept(player);
                    gameLevel.notifyAllEnemies();
                    break;
                case 's':
                    position = new Position(player.getPosition().getX(), player.getPosition().getY() + 1);
                    gameLevel.getTileInPosition(position).accept(player);
                    gameLevel.notifyAllEnemies();
                    break;
                case 'e':
                    player.activateSpacialAbility();
                    gameLevel.notifyAllEnemies();

                    break;
                case 'q':
                    gameLevel.notifyAllEnemies();
                    break;
                default:
                    messageCallback.send("please choose one of the following key's:\n" +
                            "w: up\n" +
                            "d: right\n" +
                            "s: down\n" +
                            "a: left\n" +
                            "e: ability\n" +
                            "q: stay");
                    break;

            }
        }
        return gameLevel.isLevelFinished() && !gameLevel.getIsPlayerDead();
    }

    private Player selectPlayer(int number) {
        while (true) {
            switch (number) {
                case 1:
                    return new Warrior('@', "Jon Snow", 30, 4, null, new Health(300, 300), 3);
                case 2:
                    return new Warrior('@', "The Hound", 20, 6, null, new Health(400, 400), 5);
                case 3:
                    return new Mage('@', "Melisandre", 5, 1, null, new Health(100, 100), new Mana(300), 15, 5, 6, 30);
                case 4:
                    return new Mage('@', "Thoros of Myr", 25, 4, null, new Health(250, 250), new Mana(150), 20, 3, 4, 20);
                case 5:
                    return new Rogue('@', "Arya Stark", 40, 2, null, new Health(150, 150), 20);
                case 6:
                    return new Rogue('@', "Bronn", 35, 3, null, new Health(250, 250), 50);
                case 7:
                    return new Hunter('@', "Ygritte", 30, 2, null, new Health(220, 220), 6);
                case 8:
                    return new Hunter('@', "SHAUN", 3000, 2000, null, new Health(2200, 2200), 60);
                default:
                    break;
            }
        }
    }

    private void showPlayersChoices() {
        System.out.println("" +
                "1. Jon Snow             Health: 300/300         Attack: 30              Defense: 4              Level: 1                Experience: 0/50                Cooldown: 0/3\n" +
                "2. The Hound            Health: 400/400         Attack: 20              Defense: 6              Level: 1                Experience: 0/50                Cooldown: 0/5\n" +
                "3. Melisandre           Health: 100/100         Attack: 5               Defense: 1              Level: 1                Experience: 0/50                Mana: 75/300            Spell Power: 15\n" +
                "4. Thoros of Myr        Health: 250/250         Attack: 25              Defense: 4              Level: 1                Experience: 0/50                Mana: 37/150            Spell Power: 20\n" +
                "5. Arya Stark           Health: 150/150         Attack: 40              Defense: 2              Level: 1                Experience: 0/50                Energy: 100/100\n" +
                "6. Bronn                Health: 250/250         Attack: 35              Defense: 3              Level: 1                Experience: 0/50                Energy: 100/100\n" +
                "7. Ygritte              Health: 220/220         Attack: 30              Defense: 2              Level: 1                Experience: 0/50                Arrows: 10              Range: 6");
    }

    private int playerValidation(MessageCallback messageCallback) {
        String exclamationMark = "";
        int playerCharacter = -1;
        do {

            while (!scanner.hasNextInt()) {
                scanner.next();
                messageCallback.send("please enter a player from the given list" + exclamationMark);
                exclamationMark += "!";
            }
            playerCharacter = scanner.nextInt();
            if (!(playerCharacter >= 0 & playerCharacter <= 8)) {
                messageCallback.send("please enter a player from the given list" + exclamationMark);
                exclamationMark += "!";
            }
        } while (!(playerCharacter >= 0 & playerCharacter <= 8));
        return playerCharacter;
    }

    private void printWinScreen() {
        messageCallback.send("\n" +
                " #####  ####### ####### ######                 # ####### ######           #     # ####### #     #          #     # ####### #     #    ## \n" +
                "#     # #     # #     # #     #                # #     # #     #           #   #  #     # #     #          #  #  # #     # ##    #    ## \n" +
                "#       #     # #     # #     #                # #     # #     #            # #   #     # #     #          #  #  # #     # # #   #    ## \n" +
                "#  #### #     # #     # #     #                # #     # ######              #    #     # #     #          #  #  # #     # #  #  #    ## \n" +
                "#     # #     # #     # #     #          #     # #     # #     #             #    #     # #     #          #  #  # #     # #   # #    ## \n" +
                "#     # #     # #     # #     #          #     # #     # #     #             #    #     # #     #          #  #  # #     # #    ##       \n" +
                " #####  ####### ####### ######            #####  ####### ######              #    #######  #####            ## ##  ####### #     #    ## ");
    }
    private void printLevelUpScreen(){
        messageCallback.send("\n" +
                "#          #######    #     #    #######    #             #     #    ######     ## \n" +
                "#          #          #     #    #          #             #     #    #     #    ## \n" +
                "#          #          #     #    #          #             #     #    #     #    ## \n" +
                "#          #####      #     #    #####      #             #     #    ######     ## \n" +
                "#          #           #   #     #          #             #     #    #          ## \n" +
                "#          #            # #      #          #             #     #    #             \n" +
                "#######    #######       #       #######    #######        #####     #          ## \n");
    }

}
