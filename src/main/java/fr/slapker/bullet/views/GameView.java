package fr.slapker.bullet.views;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.Game;
import fr.slapker.bullet.launcher.GameParam;
import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.BlackHole;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.WeaponBullet;
import fr.slapker.bullet.models.bonus.BlackHoleBonus;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;
import fr.slapker.bullet.views.bonus.AbstractBonusView;
import fr.slapker.bullet.views.bonus.BlackHoleBonusView;
import fr.slapker.bullet.views.bonus.HourglassBonusView;
import fr.slapker.bullet.views.bonus.MunitionsBonusView;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameView {
    private Group root;
    private Scene theScene;

    private Text infoText = new Text();
    private DropShadow ds = new DropShadow();
    private Game game;
    private PlayerView playerView;
    private ScoreView scoreView;
    private BulletPlaygroundView bulletPlaygroundView;
    private List<AstronautView> astronautViews = new ArrayList<>();
    private List<BulletView> bulletViews = new ArrayList<>();
    private List<WeaponBulletView> weaponBulletViews = new ArrayList<>();
    private List<AbstractBonusView> bonusViews = new ArrayList<>();
    private List<AsteroidExplosionView> asteroidExplosionViews = new ArrayList<>();
    private List<BlackHoleView> blackHoleViews = new ArrayList<>();


    public GameView(Group pRoot, Game pGame, boolean showBorder) {
        game = pGame;
        root = pRoot;

        if (showBorder) {
            root.getChildren().add(new BulletPlaygroundView());
        }


        initializeAstronautsViewList();
        initializeWeaponBulletList();
        initializeBulletViewsList();
        initializeBonusViewList();
        initializeBlackHoleViewList();

        if (GameParam.getInstance().isShowPlayer()) {
            playerView = new PlayerView(game.getPlayer());
            root.getChildren().add(playerView);

        }
        initializeScore();


        initializeInfos();
    }

    private void initializeBulletViewsList() {
        game.getBulletList().addListener((ListChangeListener<? super Bullet>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(bullet -> {
                        BulletView bulletView = new BulletView(bullet);
                        bulletViews.add(bulletView);
                        if (!root.getChildren().contains(bulletView)) {
                            root.getChildren().add(bulletView);
                        }
                    });
                }

                if (change.wasRemoved()) {
                    List<BulletView> bulletsViewsToRemove = new ArrayList<>();
                    change.getRemoved().forEach(bullet -> {
                        bulletViews.forEach(tmpBulletView -> {
                            if (tmpBulletView.getBullet().equals(bullet)) {
                                bulletsViewsToRemove.add(tmpBulletView);
                                root.getChildren().remove(tmpBulletView);
                            }
                        });
                    });
                    bulletViews.removeAll(bulletsViewsToRemove);
                }
            }
        });
    }

    private void initializeAstronautsViewList() {
        game.getAstronauts().addListener((ListChangeListener<? super Astronaut>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(astronaut -> {
                        AstronautView astronautView = new AstronautView(astronaut);
                        astronautViews.add(astronautView);
                        if (!root.getChildren().contains(astronautView)) {
                            root.getChildren().add(astronautView);
                        }
                    });
                }
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(astronaut -> {
                        List<AstronautView> astronautViewsToRemove = new ArrayList<>();
                        astronautViews.forEach(tmpAstroView -> {
                            if (tmpAstroView.getAstronaut().equals(astronaut)) {
                                astronautViewsToRemove.add(tmpAstroView);
                                root.getChildren().remove(tmpAstroView);
                            }
                        });
                        astronautViews.removeAll(astronautViewsToRemove);
                    });
                }
            }
        });
    }

    private void initializeBlackHoleViewList() {
        game.getBlackHoles().addListener((ListChangeListener<? super BlackHole>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(blackHole -> {
                        BlackHoleView blackHoleView = new BlackHoleView(blackHole);
                        blackHoleViews.add(blackHoleView);
                        if (!root.getChildren().contains(blackHoleView)) {
                            root.getChildren().add(blackHoleView);
                        }
                    });
                }
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(blackHole -> {
                        List<BlackHoleView> blackHoleViewsToRemove = new ArrayList<>();
                        blackHoleViews.forEach(blackHoleView -> {
                            if (blackHoleView.getBlackHole().equals(blackHole)) {
                                blackHoleViewsToRemove.add(blackHoleView);
                                root.getChildren().remove(blackHoleView);
                            }
                        });
                        blackHoleViews.removeAll(blackHoleViewsToRemove);
                    });
                }
            }
        });
    }

    private void initializeBonusViewList() {
        game.getBonuses().addListener((ListChangeListener<? super Bonus>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(bonus -> {
                        AbstractBonusView bonusView = null;
                        if (bonus instanceof MunitionsBonus) {
                            bonusView = new MunitionsBonusView(bonus);
                        } else if (bonus instanceof HourglassBonus) {
                            bonusView = new HourglassBonusView(bonus);
                        } else if (bonus instanceof BlackHoleBonus) {
                            bonusView = new BlackHoleBonusView(bonus);
                        }
                        bonusViews.add(bonusView);
                        if (!root.getChildren().contains(bonusView)) {
                            root.getChildren().add(bonusView);
                        }
                    });
                }
                if (change.wasRemoved()) {
                    change.getRemoved().forEach(mb -> {
                        List<AbstractBonusView> mbToRemove = new ArrayList<>();
                        bonusViews.forEach(tmpMbw -> {
                            if (tmpMbw.getBonus().equals(mb)) {
                                mbToRemove.add(tmpMbw);
                                root.getChildren().remove(tmpMbw);
                            }
                        });
                        bonusViews.removeAll(mbToRemove);
                    });
                }
            }
        });
    }

    private void initializeWeaponBulletList() {
        game.getWeaponBullets().addListener((ListChangeListener<? super WeaponBullet>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(wpb -> {
                        WeaponBulletView wpbView = new WeaponBulletView(wpb);
                        weaponBulletViews.add(wpbView);
                        if (!root.getChildren().contains(wpbView)) {
                            root.getChildren().add(wpbView);
                        }
                    });
                }
                if (change.wasRemoved()) {
                    List<WeaponBulletView> wpbwToRemove = new ArrayList<>();
                    change.getRemoved().forEach(wpb -> weaponBulletViews.forEach(wpbView -> {
                        if (wpbView.getWeaponBullet().equals(wpb)) {
                            wpbwToRemove.add(wpbView);
                            root.getChildren().remove(wpbView);
                        }
                    }));
                    weaponBulletViews.removeAll(wpbwToRemove);
                }
            }
        });
    }

    private void initializeScore() {
        scoreView = new ScoreView();
        scoreView.getScore().bind(game.getScoreProp());
        scoreView.getNbBullets().bind(game.getNbBullets());
        if (GameParam.getInstance().isShowPlayer()) {
            scoreView.getNbLives().bind(playerView.getPlayer().getNbLives());
            scoreView.getMunitions().bind(playerView.getPlayer().getMunitions());
        }
        root.getChildren().add(scoreView);
    }

    private void initializeInfos() {
        ds = new DropShadow();
        ds.setOffsetY(2.0f);
        ds.setColor(Color.WHITE);

        infoText = new Text();
        infoText.setEffect(ds);
        infoText.setCache(true);
        infoText.setTextAlignment(TextAlignment.CENTER);
        infoText.setX(0);
        infoText.setY(BulletScreenParam.getInstance().getWindow_height() / 2);
        infoText.setWrappingWidth(BulletScreenParam.getInstance().getWindow_width());
        //infoText.setFont(Font.font(null, FontWeight.BOLD, 45));
    }

    public void showInfos(String textToShow, Color color) {
        showInfos(textToShow, color, false);
    }

    public void showInfos(String textToShow, Color color, boolean important) {
        if (important) {
            Font font = Font.loadFont(getClass().getResource("/FXML/GAME_glm.ttf").toExternalForm(), 100);
            infoText.setFont(font);
        } else {
            Font font = Font.loadFont(getClass().getResource("/FXML/Gameplay.ttf").toExternalForm(), 35);
            infoText.setFont(font);
        }
        infoText.setFill(color);
        infoText.setText(textToShow);
        if (!root.getChildren().contains(infoText)) {
            root.getChildren().add(infoText);
        }
    }

    public void hideInfos() {
        root.getChildren().remove(infoText);
    }

    public void refreshGraphicsObject(double elapsedTime) {

        List<AsteroidExplosionView> astExplToRemove = new ArrayList<>();
        asteroidExplosionViews.forEach(astExpl -> {
            astExpl.refreshExplosiontView(elapsedTime);
            if (astExpl.getOndeDeChocColorOpacity() == 0 && astExpl.getOpacityParticule() == 0) {
                astExplToRemove.add(astExpl);
            }
        });
        astExplToRemove.forEach(astExplView -> {
            removeAsteroidExplosion(astExplView);
        });

        bonusViews.forEach(bonus -> bonus.refreshImage(elapsedTime));
        blackHoleViews.forEach(bhview -> bhview.refreshBhView());
        scoreView.redraw();
    }

    public void addAsteroidExplosion(AsteroidExplosionView asteroidExplosionView) {
        root.getChildren().add(asteroidExplosionView);
        asteroidExplosionViews.add(asteroidExplosionView);
    }

    public void removeAsteroidExplosion(AsteroidExplosionView asteroExplView) {
        root.getChildren().remove(asteroExplView);
        asteroidExplosionViews.remove(asteroExplView);
    }
}

