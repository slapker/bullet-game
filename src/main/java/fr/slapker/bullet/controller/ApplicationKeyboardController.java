package fr.slapker.bullet.controller;

import fr.slapker.bullet.launcher.BulletGame;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ApplicationKeyboardController {

    public ApplicationKeyboardController(Stage stage, MenuController menuController) {
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                if (BulletGame.getGameController().isFromLevelMaker()) {
                    BulletGame.getGameController().setFromLevelMaker(false);
                    BulletGame.getGameController().startActualLevelMaker();
                } else {
                    BulletGame.getGameController().pauseGame();
                    try {
                        menuController.loadMenu();

                    } catch (Exception e) {

                    }

                }
            }
        });

    }
}
