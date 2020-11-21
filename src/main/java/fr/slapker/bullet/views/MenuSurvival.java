package fr.slapker.bullet.views;

import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.GameModeEnum;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuSurvival {

    @FXML
    private void startEasy() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getGameController().startSurvivalGame(GameModeEnum.SURVIVAL_EASY);
    }

    @FXML
    private void startMedium() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getGameController().startSurvivalGame(GameModeEnum.SURVIVAL_NORMAL);
    }

    @FXML
    private void startHardcore() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getGameController().startSurvivalGame(GameModeEnum.SURVIVAL_HARDCORE);
    }

    @FXML
    private void back() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadMenu();
    }

}
