package fr.slapker.bullet.launcher;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.controller.ApplicationKeyboardController;
import fr.slapker.bullet.controller.GameController;
import fr.slapker.bullet.controller.MenuController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BulletGame extends Application {

    private static Stage primaryStage;

    private static GameController gameController;

    private static MenuController menuController;

    @Override
    public void start(Stage pPrimaryStage) throws Exception {

        setResolution();
        primaryStage = pPrimaryStage;
        primaryStage.setTitle("Bullet");
        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);


        Scene mainScene = new Scene(new Pane(), Color.BLACK);
        primaryStage.setScene(mainScene);
        gameController = new GameController(primaryStage);
        menuController = new MenuController();
        menuController.loadMenu();
/*        GameParam.getInstance().setWindowWidth(primaryStage.getWidth());
        GameParam.getInstance().setWindowHeight(primaryStage.getHeight());*/
        ApplicationKeyboardController applicationKeyboardController = new ApplicationKeyboardController(pPrimaryStage, menuController);

        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("quantum.multithreaded", "false");
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static MenuController getMenu() {
        return menuController;
    }

    public static GameController getGameController() {
        return gameController;
    }

    private void setResolution() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        BulletScreenParam.getInstance().setWindow_width((int) screenBounds.getMaxX());
        BulletScreenParam.getInstance().setWindow_height((int) screenBounds.getMaxY());

    }
}
