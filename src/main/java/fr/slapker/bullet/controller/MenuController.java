package fr.slapker.bullet.controller;

import fr.slapker.bullet.launcher.BulletGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MenuController {

    private GridPane root;
    private GridPane rootLevelSelector;
    private GridPane rootSurvivalModeSelector;
    private GridPane rootGravitySim;

    public MenuController() {

    }

    public void loadMenu() {
        BulletGame.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);
        loadScene();
        BulletGame.getPrimaryStage().setFullScreen(false);
        BulletGame.getPrimaryStage().getScene().getStylesheets().add("/FXML/menu.css");
        BulletGame.getPrimaryStage().getScene().setRoot(root);

    }

    public void loadScene() {
        if (root == null) {
            try {
                root = FXMLLoader.load(getClass().getResource("/FXML/FXMLMenu.fxml"));
            } catch (IOException ioE) {
                System.out.println(ioE.getStackTrace());
            }
        }
    }

    public void loadLevelSelection() {
        if (rootLevelSelector == null) {

            try {
                rootLevelSelector = FXMLLoader.load(getClass().getResource("/FXML/FXMLMenuRescue.fxml"));
            } catch (IOException ioE) {
                System.out.println(ioE.getStackTrace());
            }
        }
        BulletGame.getPrimaryStage().setFullScreen(false);
        BulletGame.getPrimaryStage().getScene().setRoot(rootLevelSelector);
    }

    public void loadSurvivalMode() {
        if (rootSurvivalModeSelector == null) {

            try {
                rootSurvivalModeSelector = FXMLLoader.load(getClass().getResource("/FXML/FXMLMenuSurvival.fxml"));
            } catch (IOException ioE) {
                System.out.println(ioE.getStackTrace());
            }
        }
        BulletGame.getPrimaryStage().setFullScreen(false);
        BulletGame.getPrimaryStage().getScene().setRoot(rootSurvivalModeSelector);
    }

    public void loadGravityMenu() {
        if (rootGravitySim == null) {

            try {
                rootGravitySim = FXMLLoader.load(getClass().getResource("/FXML/FXMLGravitySim.fxml"));
            } catch (IOException ioE) {
                System.out.println(ioE.getStackTrace());
            }
        }
        BulletGame.getPrimaryStage().setFullScreen(false);
        BulletGame.getPrimaryStage().getScene().setRoot(rootGravitySim);
    }
}
