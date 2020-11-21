package fr.slapker.bullet.launcher;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.models.*;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.views.AstronautView;
import fr.slapker.bullet.views.BlackHoleView;
import fr.slapker.bullet.views.ScoreView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Game {
    protected GameModeParam gameModeParam;
    protected int astronautsNbStart = 20;
    protected GameModeEnum gameMode;

    private LocalDateTime startBonusTimeReduceStart;
    private int slowMoDurationSeconds;
    private LocalDateTime lastTimeBonus = LocalDateTime.now();
    private Player player;
    private SimpleIntegerProperty scoreProp = new SimpleIntegerProperty();
    private SimpleIntegerProperty nbBullets = new SimpleIntegerProperty();
    private ScoreView score;
    protected ObservableList<Bullet> bulletList = FXCollections.observableArrayList();
    private ObservableList<WeaponBullet> weaponBullets = FXCollections.observableArrayList();
    private ObservableList<Bonus> bonuses = FXCollections.observableArrayList();
    private ObservableList<BlackHole> blackHoles = FXCollections.observableArrayList();
    private ObservableList<AsteroidExplosion> asteroidExplosions = FXCollections.observableArrayList();

    private BonusGenerator bonusGenerator = new BonusGenerator();
    private ArrayList<String> input = new ArrayList<String>();
    private ObservableList<Astronaut> astronauts = FXCollections.observableArrayList();
    private ObservableList<AstronautView> astronautsViews = FXCollections.observableArrayList();
    private AstronautGenerator astroGenerator = new AstronautGenerator();
    private boolean demoMode = BulletScreenParam.getInstance().DEMO_MODE;
    private long lastTimeShowInfo = 0;
    private int maxBonuses = 0;


    private List<String> keyboardsInput;


    public Game() {
        GameModeParamLoader gameModeParamLoader = new GameModeParamLoader();
        gameModeParam = gameModeParamLoader.getGameMode(GameModeEnum.RESCUE);
        //Initializing
        if (GameParam.getInstance().isShowPlayer()) {
            player = new Player(40, 40);

        }
        intializeBullets();
    }

    public void refreshElementPosition(double elapsedTime) {
        if (GameParam.getInstance().isShowPlayer()) {
            player.refreshPosition(elapsedTime);

        }
        astronauts.forEach(a -> a.refreshPosition(elapsedTime));
        weaponBullets.forEach(wpb -> {
            wpb.move(elapsedTime);
        });

        if (GameParam.getInstance().applyGravity) {
            bulletList.forEach(bullet -> {
                if (GameParam.getInstance().isShowPlayer()) {
                    weaponBullets.forEach(weaponBullet -> {
                        weaponBullet.applyGravityFromObject(bullet);
                    });
                    player.applyGravityFromObject(bullet);
                }
                bulletList.forEach(bullet2 -> {
                    bullet.applyGravityFromObject(bullet2);
                });
            });
        }

        if (!blackHoles.isEmpty()) {

            bulletList.forEach(bullet -> {
                bullet.applyGravityFromObject(blackHoles.get(0));
            });
        }

        bulletList.forEach(bullet -> bullet.move(elapsedTime));
    }

    private void intializeBullets() {
        //Initiliazing Bullet List
        bulletList.addListener(new ListChangeListener<Bullet>() {
            @Override
            public void onChanged(Change<? extends Bullet> change) {
                while (change.next()) {
                    if (change.wasAdded()) {
                        nbBullets.setValue(bulletList.size());
                    }
                    if (change.wasRemoved()) {
                        nbBullets.setValue(bulletList.size());
                    }
                }
            }
        });
    }

    public void generateBonus() {
        Duration duration = Duration.between(lastTimeBonus, LocalDateTime.now());
        if (!demoMode && duration.getSeconds() > 3 && bonuses.size() < 5) {
            getBonuses().add(bonusGenerator.makeBonus());
            lastTimeBonus = LocalDateTime.now();
        }

    }

    public void handleBonus() {
        Duration duration = Duration.between(lastTimeBonus, LocalDateTime.now());

        if (startBonusTimeReduceStart != null) {
            duration = Duration.between(startBonusTimeReduceStart, LocalDateTime.now());
            if (duration.getSeconds() > slowMoDurationSeconds) {
                GameParam.getInstance().setTimeReducerCoeff(1);
                BulletGame.getGameController().getSoundMode().disableSloMoSound();
                startBonusTimeReduceStart = null;
            }
        }
        if (!blackHoles.isEmpty()) {
            List<BlackHole> blackHolesToKill = new ArrayList<>();
            List<BlackHole> blackHolesToRemove = new ArrayList<>();
            blackHoles.forEach(blackHole -> {
                if (Duration.between(blackHole.getCreationTime(), LocalDateTime.now()).toSeconds() > blackHole.getDurationInSeconds() && !blackHole.isDying() && !blackHole.isShouldRemove()) {
                    blackHolesToKill.add(blackHole);
                }
                if (blackHole.isShouldRemove()) {
                    blackHolesToRemove.add(blackHole);
                }
            });

            if (!blackHolesToRemove.isEmpty()) {
                blackHoles.removeAll(blackHoles);
                double maxVelocity = gameModeParam.getBulletStartVelocity();
                bulletList.forEach(bullet -> {
                    if (bullet.getVelocityY() < -maxVelocity) {
                        bullet.setVelocityY(-maxVelocity);
                    }
                    if (bullet.getVelocityX() < -maxVelocity) {
                        bullet.setVelocityX(-maxVelocity);
                    }
                    if (bullet.getVelocityX() > maxVelocity) {
                        bullet.setVelocityX(maxVelocity);
                    }
                    if (bullet.getVelocityY() > maxVelocity) {
                        bullet.setVelocityY(maxVelocity);
                    }
                });
            }

            if (!blackHolesToKill.isEmpty()) {
                blackHolesToKill.forEach(blackHole -> {
                    BlackHoleView bhView = BulletGame.getGameController().getGameView().getBlackHoleViews().stream().filter(bhViewTmp -> bhViewTmp.getBlackHole() == blackHole).findFirst().get();
                    bhView.getBlackHole().setDying(true);
                });

            }
        }
    }

}
