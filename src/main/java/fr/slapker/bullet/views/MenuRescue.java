package fr.slapker.bullet.views;

import fr.slapker.bullet.Level.LevelSerializable;
import fr.slapker.bullet.Level.LevelSerializer;
import fr.slapker.bullet.launcher.BulletGame;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuRescue {

    @FXML
    private void startLevel1() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        String fileName = "levels/ronde_de_lespace.txt";
        LevelSerializable level = LevelSerializer.loadLevelFromClassPathFile(fileName);
        BulletGame.getGameController().startRescueGame(level);
    }

    @FXML
    private void startLevel2() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        String fileName = "levels/easy.txt";
        LevelSerializable level = LevelSerializer.loadLevelFromClassPathFile(fileName);
        BulletGame.getGameController().startRescueGame(level);
    }

    @FXML
    private void startLevel3() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        String fileName = "levels/test.txt";
        LevelSerializable level = LevelSerializer.loadLevelFromClassPathFile(fileName);
        BulletGame.getGameController().startRescueGame(level);
    }

    @FXML
    private void startLoadYouOwn() {
        BulletGame.getPrimaryStage().setFullScreen(false);
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);


        if (BulletGame.getGameController().getLastBrowsingLevelPath() != null) {
            fileChooser.setInitialDirectory(BulletGame.getGameController().getLastBrowsingLevelPath());
        }

        //Show save file dialog
        File file = fileChooser.showOpenDialog(BulletGame.getPrimaryStage());


        if (file != null) {
            BulletGame.getGameController().setLastBrowsingLevelPath(file.getParentFile());
            LevelSerializable level = LevelSerializer.loadLevelFromFile(file.getAbsolutePath());
            BulletGame.getGameController().startRescueGame(level);
        }
    }

    @FXML
    private void back() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadMenu();
    }

}
