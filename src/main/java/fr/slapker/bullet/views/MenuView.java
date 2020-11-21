package fr.slapker.bullet.views;

import fr.slapker.bullet.launcher.BulletGame;
import javafx.fxml.FXML;

public class MenuView {

    @FXML
    private void startGame() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadLevelSelection();
    }

    @FXML
    private void levelMaker() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getGameController().startLevelMakerFromFile(null);
    }

    @FXML
    private void resumeGame() {
        if (BulletGame.getGameController().getRoot() != null) {
            BulletGame.getPrimaryStage().getScene().setRoot(BulletGame.getGameController().getRoot());
            BulletGame.getGameController().resumeGame();
        }
    }

    @FXML
    private void startSurvival() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadSurvivalMode();

    }

    @FXML
    private void startGravityGame() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadGravityMenu();

    }

    @FXML
    private void quitGame() {
        BulletGame.getPrimaryStage().close();
    }

}
