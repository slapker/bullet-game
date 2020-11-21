package fr.slapker.bullet.controller;

import fr.slapker.bullet.Level.LevelMaker;
import fr.slapker.bullet.Level.LevelSerializable;
import fr.slapker.bullet.Level.LevelSerializer;
import fr.slapker.bullet.launcher.*;
import fr.slapker.bullet.models.WeaponBullet;
import fr.slapker.bullet.soundMode.AbstractSoundMode;
import fr.slapker.bullet.soundMode.StupidSoundMode;
import fr.slapker.bullet.views.GameView;
import fr.slapker.bullet.views.LevelMakerView;
import fr.slapker.bullet.views.PlayerView;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class GameController extends AnimationTimer {

    private Game game;
    private GameView gameView;
    private KeyboardController keyboardController = new KeyboardController();
    private MouseLevelMakerController mouseLevelMakerController = new MouseLevelMakerController();
    private CollisionController collisionController;
    private File lastBrowsingLevelPath;

    private AtomicLong lastNanoTime = new AtomicLong(System.nanoTime());
    private double oneFps = 1d / 300d;

    private AtomicLong startFreezingTime = new AtomicLong();
    private AtomicBoolean freeze = new AtomicBoolean(false);
    List<String> keyboardInputs;
    List<String> keyboardReleased;
    private Stage stage;
    private Scene gameScene;
    private Group root;
    private AbstractSoundMode soundMode;
    private boolean fromLevelMaker;
    private LevelSerializable actualEditingLevel;

    private Integer bulletToLoad;
    private boolean loading = false;
    private AtomicBoolean firstLoop = new AtomicBoolean(false);

    public GameController(Stage pStage) {
        stage = pStage;
        soundMode = new StupidSoundMode();
    }

    public void initWindow() {
        root = new Group();
        keyboardInputs = keyboardController.initializeInputControls(stage.getScene());
        keyboardReleased = keyboardController.getReleasedInputs();
        BulletGame.getPrimaryStage().getScene().setRoot(root);
        BulletGame.getPrimaryStage().getScene().setCursor(Cursor.NONE);
        freeze.set(false);
    }


    public void startRescueGame(LevelSerializable level) {
        GameParam.getInstance().setTimeReducerCoeff(1);
        GameParam.getInstance().setBulletCollideBorder(false);
        GameParam.getInstance().setApplyGravity(false);
        GameParam.getInstance().setShowPlayer(true);
        GameParam.getInstance().setBulletFusion(false);
        initWindow();
        game = new Game();
        game.setGameMode(GameModeEnum.RESCUE);
        gameView = new GameView(root, game, false);
        LevelSerializer.loadLevelInGame(level, game);
        collisionController = new CollisionController(gameView, game);
        stage.setFullScreen(true);
        game.getBulletList().forEach(bullet -> {
            bullet.applyInitialVelocity();
        });
        this.start();
    }

    public LevelMaker startLevelMakerFromFile(File file) {
        this.stop();
        GameParam.getInstance().setShowAttractionCircle(false);
        GameParam.getInstance().setApplyGravity(false);
        LevelMaker levelMaker = new LevelMaker();

        root = new Group();
        BulletGame.getPrimaryStage().getScene().setRoot(root);
        BulletGame.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);

        freeze.set(false);

        LevelMakerView levelMakerView = new LevelMakerView(root, levelMaker);
        keyboardInputs = mouseLevelMakerController.initializeControls(stage.getScene(), levelMaker, levelMakerView);
        stage.setFullScreen(true);
        if (file != null) {
            LevelSerializable level = LevelSerializer.loadLevelFromFile(file.getAbsolutePath());
            LevelSerializer.loadLevelInLevelMaker(level, levelMaker);
        }
        return levelMaker;

    }

    public void startActualLevelMaker() {
        LevelMaker levelMaker = startLevelMakerFromFile(null);
        LevelSerializer.loadLevelInLevelMaker(actualEditingLevel, levelMaker);
    }


    public void startSurvivalGame(GameModeEnum gameMode) {
        initWindow();
        GameParam.getInstance().setShowPlayer(true);
        game = new GameSurvival(gameMode);
        game.setGameMode(gameMode);
        gameView = new GameView(root, game, true);
        collisionController = new CollisionController(gameView, game);
        ((GameSurvival) game).generateBullet();

        this.start();
    }

    public void startGravityGame(double gravity, double nbStars, double maxStarSize, double timeSpeed) {
        initWindow();
        this.stop();
        GameParam.getInstance().setTimeReducerCoeff(timeSpeed);
        GameParam.getInstance().getG().setValue(gravity);

        game = new GameGravity();
        gameView = new GameView(root, game, false);
        collisionController = new CollisionController(gameView, game);
        ((GameGravity) game).generateBullet((int) nbStars, (int) maxStarSize);
        bulletToLoad = (int) nbStars;
        loading = true;
        this.start();
    }


    private boolean playGame(long currentNanoTime) {
        if (freeze.get()) {

            lastNanoTime.set(currentNanoTime);

        } else {

            double elapsedTime = (currentNanoTime - lastNanoTime.get()) / 1000000000.0;
            lastNanoTime.set(currentNanoTime);
            handleCollision(currentNanoTime);
            boolean playerHitBullet = false;
            if (GameParam.getInstance().isShowPlayer()) {
                playerHitBullet = collisionController.playerCollideBullets(gameView.getPlayerView(), gameView.getBulletViews(), game);
            }
            if (!game.isDemoMode() && playerHitBullet && game.getPlayer().getNbLives().get() > 0) {
                String s = "";
                if (game.getPlayer().getNbLives().getValue() > 1) {
                    s = "s";
                }
                gameView.showInfos(game.getPlayer().getNbLives().getValue() + " life" + s + " left !!", Color.RED);
                game.setLastTimeShowInfo(currentNanoTime);
            }

            if (game.getLastTimeShowInfo() != 0 && currentNanoTime - game.getLastTimeShowInfo() > 1800000000) {
                gameView.hideInfos();
                game.setLastTimeShowInfo(0);
            }

            if (game.getGameMode() != null && !game.getGameMode().equals(GameModeEnum.GRAVITY)) {
                applyControls(elapsedTime, gameView.getPlayerView());
            }

            if (GameParam.getInstance().isGenerateBonus() && game.getBonuses().size() < game.getMaxBonuses()) {
                game.generateBonus();
            }
            game.handleBonus();
            Platform.runLater((Runnable) () -> {
                game.refreshElementPosition(elapsedTime);
                gameView.refreshGraphicsObject(elapsedTime);

            });
            gameView.getScoreView().redraw();
            handleEndOfGame();
        }
        return true;
    }

    private void handleEndOfGame() {
        if (game.getPlayer() != null && game.getPlayer().getNbLives().get() == 0) {
            gameView.showInfos("Game Over ! ", Color.RED, true);
            soundMode.getEngineLoop().stop();
            soundMode.stopEngine();
            this.stop();
        }

        if (game.getGameMode() != null && game.getGameMode().equals(GameModeEnum.RESCUE) && game.getAstronauts().isEmpty()) {
            gameView.showInfos("Mission Complete ! ", Color.GREEN, true);
            soundMode.getEngineLoop().stop();
            soundMode.stopEngine();
            this.stop();
        }

        if (game.getGameMode() != null && game.getBulletList().isEmpty()
                && (game.getGameMode().equals(GameModeEnum.SURVIVAL_EASY)
                || game.getGameMode().equals(GameModeEnum.SURVIVAL_NORMAL)
                || game.getGameMode().equals(GameModeEnum.SURVIVAL_HARDCORE))) {
            gameView.showInfos("Mission Complete ! ", Color.GREEN, true);
            soundMode.getEngineLoop().stop();
            soundMode.stopEngine();
            this.stop();
        }
    }

    private void handleCollision(long currentNanoTime) {
        collisionController.playerCollideAstronauts(gameView.getPlayerView(), gameView.getAstronautViews(), game.getAstronauts(), game.getScoreProp());
        collisionController.astronautsCollidesBullets(gameView.getBulletViews(), gameView.getAstronautViews(), game.getAstronauts());
        collisionController.weaponsBulletCollidesBullet();
        collisionController.bulletCollidesBlackHole();
        collisionController.playerCollidesBonus(currentNanoTime);
        if (GameParam.getInstance().isBulletCollideBorder()) {
            collisionController.bulletCollidesBorder();
        }

        if (GameParam.getInstance().isBulletFusion()) {
            Platform.runLater((Runnable) () -> {
                collisionController.bulletCollidesBullet();
            });
        }
    }


    public void applyControls(double elapsedTime, PlayerView playerView) {
        AbstractSoundMode soundMode = BulletGame.getGameController().getSoundMode();

        if (keyboardInputs.contains("LEFT")) {
            game.getPlayer().turn(-300 * elapsedTime);
        } else if (keyboardInputs.contains("RIGHT")) {
            game.getPlayer().turn(300 * elapsedTime);
        }


        if (keyboardInputs.contains("SPACE")) {
            List<WeaponBullet> tmpWeaponBullets = game.getPlayer().fire(playerView.getShipImageView());
            game.getWeaponBullets().addAll(tmpWeaponBullets);
        }

        if (keyboardReleased.contains("UP")) {
            soundMode.playEngineOff();
            soundMode.stopEngine();
            playerView.loadImage(game.getPlayer().getShipImageOff());
            keyboardReleased.remove("UP");

        }

        if (keyboardInputs.contains("UP")) {
            soundMode.playEngine();
            game.getPlayer().moveForward(elapsedTime);
            playerView.loadImage(game.getPlayer().getShipImage());
        }


    }

    @Override
    public void handle(long currentNanoTime) {
        playGame(currentNanoTime);
    }

    public void pauseGame() {
        freeze.set(true);
    }

    public void resumeGame() {
        freeze.set(false);
    }


    public void startRescueGameFromLevelMaker() {
        this.fromLevelMaker = true;
        startRescueGame(actualEditingLevel);
    }

}
