package fr.slapker.bullet.controller;

import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.Game;
import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.BulletGenerator;
import fr.slapker.bullet.models.WeaponBullet;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.views.*;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CollisionController {

    private Game game;
    private GameView gameView;

    public CollisionController(GameView pGameView, Game pGame) {
        game = pGame;
        gameView = pGameView;
    }

    public void bulletCollidesBorder() {
        List<Bullet> addedBullet = new ArrayList<>();
        gameView.getBulletViews().forEach(bulletView -> {
            if (bulletView.checkHittingBorder() && game.getBulletList().size() < game.getGameModeParam().getBulletMaxOnScreen()) {
                addedBullet.add(BulletGenerator.makeBullet(bulletView.getBullet(), game.getGameModeParam().getBulletStartVelocity()));
            }
        });
        game.getBulletList().addAll(addedBullet);
    }

    public void bulletCollidesBlackHole() {
        List<Bullet> bulletToRemove = new ArrayList<>();
        if (game.getBlackHoles() != null && !game.getBlackHoles().isEmpty()) {
            gameView.getBlackHoleViews().forEach(blackHoleView -> {
                gameView.getBulletViews().forEach(bulletView -> {
                    if (bulletView.getCircle().getLayoutBounds().intersects(blackHoleView.getCircle().getLayoutBounds())) {
                        bulletToRemove.add(bulletView.getBullet());
                    }
                });
            });
        }
        game.getBulletList().removeAll(bulletToRemove);
    }

    public void bulletCollidesBullet() {
        List<BulletView> deadBulletList = new ArrayList<>();
        gameView.getBulletViews().forEach(bulletView -> {
            gameView.getBulletViews().forEach(bulletView2 -> {
                if (bulletView != bulletView2 && bulletView.getBounds().intersects(bulletView2.getBounds())) {
                    if (bulletView.getBullet().getMassValue() < bulletView2.getBullet().getMassValue()) {
                        bulletView2.getBullet().fusion(bulletView.getBullet());
                        if (!deadBulletList.contains(bulletView)) {
                            deadBulletList.add(bulletView);

                        }
                    } else {
                        bulletView.getBullet().fusion(bulletView2.getBullet());
                        if (!deadBulletList.contains(bulletView2)) {
                            deadBulletList.add(bulletView2);
                        }
                    }
                }
            });
        });
        deadBulletList.forEach(bulletView -> game.getBulletList().remove(bulletView.getBullet()));
        //bulletExplosionForGravitySim(deadBulletList);
    }

    public boolean playerCollideBullets(PlayerView playerView, List<BulletView> bulletViews, Game game) {

        final AtomicBoolean loosingLife = new AtomicBoolean(false);
        List<BulletView> bulletsToDestroy = new ArrayList<>();

        bulletViews.forEach(tmpBulletView -> {
            if (playerView.getBounds().intersects(tmpBulletView.getBounds())) {
                bulletsToDestroy.add(tmpBulletView);
                LocalDateTime lastTimeHittingBullet = playerView.getPlayer().getLastTimeHittingBullet();
                if (lastTimeHittingBullet != null) {
                    Duration duration = Duration.between(lastTimeHittingBullet, LocalDateTime.now());
                    if (duration.getSeconds() >= 1) {
                        playerView.getPlayer().looseLife();
                        loosingLife.set(true);
                    }
                } else {
                    playerView.getPlayer().looseLife();
                    loosingLife.set(true);
                }
            }
        });

        bulletsToDestroy.forEach(bulletView -> {
            game.getBulletList().remove(bulletView.getBullet());
        });
        bulletExplosion(bulletsToDestroy);

        return loosingLife.get();
    }

    public void playerCollideAstronauts(PlayerView player, List<AstronautView> astronautViews, List<Astronaut> astronauts, IntegerProperty scoreProp) {
        List<Astronaut> astronautToCatch = new ArrayList<>();
        astronautViews.forEach(astronautView -> {
            if (astronautView.getBounds() != null && astronautView.getBounds().intersects(player.getBounds())) {
                astronautToCatch.add(astronautView.getAstronaut());
            }
        });

        astronautToCatch.forEach(astroToCatch -> {
            game.getAstronauts().remove(astroToCatch);
            scoreProp.setValue(scoreProp.getValue() + 1);
            BulletGame.getGameController().getSoundMode().getAstroRescue().play();
        });
    }

    public void astronautsCollidesBullets(List<BulletView> bulletViews, List<AstronautView> astronautViews, List<Astronaut> astronauts) {
        List<Astronaut> astronautsToKills = new ArrayList<>();
        List<BulletView> bulletViewsToDestroy = new ArrayList<>();
        astronautViews.forEach(astroView -> {
            bulletViews.forEach(bulletView -> {
                if (astroView.getBounds().intersects(bulletView.getCircle().getLayoutBounds())) {
                    BulletGame.getGameController().getSoundMode().getAstroDie().play();
                    bulletViewsToDestroy.add(bulletView);
                    astronautsToKills.add(astroView.getAstronaut());
                }
            });
        });

        astronautsToKills.forEach(astroToKill -> {
            astronauts.remove(astroToKill);
        });

        bulletViewsToDestroy.forEach(bulletViewToDestroy -> {
            game.getBulletList().remove(bulletViewToDestroy.getBullet());
        });

        bulletExplosion(bulletViewsToDestroy);
    }

    public void weaponsBulletCollidesBullet() {
        List<BulletView> deadBulletsView = new ArrayList<>();
        List<WeaponBullet> deadWeaponBullet = new ArrayList<>();


        gameView.getBulletViews().forEach(tmpBulletView -> {
            gameView.getWeaponBulletViews().forEach(wpb -> {
                if (tmpBulletView.getCircle().getLayoutBounds().intersects(wpb.getBoundsInLocal())) {
                    deadBulletsView.add(tmpBulletView);
                    deadWeaponBullet.add(wpb.getWeaponBullet());
                }
            });
        });
        game.getWeaponBullets().removeAll(deadWeaponBullet);
        deadBulletsView.forEach(bulletView -> {
            game.getBulletList().remove(bulletView.getBullet());
        });
        bulletExplosion(deadBulletsView);
    }

    private void bulletExplosion(List<BulletView> bulletViews) {

        bulletViews.forEach(bulletView -> {
            BulletGame.getGameController().getSoundMode().playBulletExplosion(bulletView.getBullet().getBulletRadius().getValue() / 50d);
            AsteroidExplosionView asteroidExplosionView = new AsteroidExplosionView(bulletView.getBullet(), bulletView.getColor());
            gameView.addAsteroidExplosion(asteroidExplosionView);
        });
    }

    private void bulletExplosionForGravitySim(List<BulletView> bulletViews) {

        bulletViews.forEach(bulletView -> {
            Platform.runLater((Runnable) () -> {
                AsteroidExplosionView asteroidExplosionView = new AsteroidExplosionView(bulletView.getBullet(), bulletView.getColor());
                gameView.addAsteroidExplosion(asteroidExplosionView);
            });

        });
    }

    public void playerCollidesBonus(long currentNanoTime) {

        AtomicBoolean isHitting = new AtomicBoolean(false);
        List<Bonus> bonusToConsume = new ArrayList<>();
        gameView.getBonusViews().forEach(bonusView -> {
            if (bonusView.getImageView().getBoundsInLocal().intersects(gameView.getPlayerView().getBounds())) {
                bonusToConsume.add(bonusView.getBonus());
                isHitting.set(true);
            }
        });

        StringBuilder bonusMessagesStr = new StringBuilder();
        bonusToConsume.forEach(bonus -> {
            if (bonusMessagesStr.length() != 0) {
                bonusMessagesStr.append(" / ");
            }
            bonusMessagesStr.append(bonus.consumeBonus(game));
        });

        if (!bonusToConsume.isEmpty()) {
            game.setLastTimeBonus(LocalDateTime.now());
            game.setLastTimeShowInfo(currentNanoTime);
            gameView.showInfos(bonusMessagesStr.toString(), Color.GREEN);
        }
        ;

        game.getBonuses().removeAll(bonusToConsume);
    }
}
