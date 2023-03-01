package FrontEnd;

import Logic.GameEngine.FileParser;
import Logic.GameEngine.GameLevel;
import Logic.GameEngine.LevelManager;
import Logic.GameEngine.Pair;
import Logic.Map.Position;
import Logic.Objects.PlayerType.*;
import Logic.Utility.Health;
import Logic.Utility.Mana;

import java.io.File;
import java.util.Scanner;

public class GameManager {
    public static <PLayer> void main(String[] args) {
        LevelManager levelManager = new LevelManager(args[0]);
        levelManager.runLevels();

    }
}
