package fr.slapker.bullet.models;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.BulletGame;
import fr.slapker.bullet.launcher.GameParam;
import fr.slapker.bullet.models.bonus.MunitionsBonus;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player extends MovingObject {

    private static final Double DEFAULT_ANGLE = 315d;
    private double VELOCITY_MAX = 500 * BulletScreenParam.getInstance().getScreenRatioY();
    private double COEFF_REDUCTEUR = 8d * BulletScreenParam.getInstance().getScreenRatioY();
    private double COEFF_WIND_BRAKE = 0.7d;
    private static final double SHIP_SIZE = 60;

    private SimpleDoubleProperty rotation = new SimpleDoubleProperty();

    private SimpleObjectProperty<Rectangle2D> viewPort = new SimpleObjectProperty<>();

    private SimpleIntegerProperty nbLives = new SimpleIntegerProperty();
    private LocalDateTime lastTimeHittingBullet = null;
    private long lastFireTime = 0l;
    private SimpleIntegerProperty munitions = new SimpleIntegerProperty(0);
    ;
    double screenRatio = BulletScreenParam.getInstance().getScreenRatioY();
    Image shipImage = new Image(getClass().getClassLoader().getResource("images/ship.png").toExternalForm(), SHIP_SIZE * screenRatio, SHIP_SIZE * screenRatio, false, false);
    Image shipImageOff = new Image(getClass().getClassLoader().getResource("images/ship_off.png").toExternalForm(), SHIP_SIZE * screenRatio, SHIP_SIZE * screenRatio, false, false);

    List<MediaPlayer> mediaPlayerList;
    int nbMediaPlayerFire = 15;
    int currentMedia = 0;

    public Player(double pPosX, double pPosY) {
        if (GameParam.getInstance().isKidMode()) {
            /*COEFF_REDUCTEUR = 4d;*/
        }

        mediaPlayerList = new ArrayList<MediaPlayer>();
        bulletRadius.setValue(10);
        posX.setValue(pPosX);
        posY.setValue(pPosY);
        nbLives.setValue(3);
    }


    private double getRealAngle() {
        double realAngle = rotation.getValue() + DEFAULT_ANGLE;
        if (realAngle > 360) {
            realAngle = realAngle - 360;
        }
        return realAngle;
    }

    public void moveForward(double elapsedTime) {
        double realAngle = getRealAngle();
        double realAngleModulo = realAngle % 90;
        //System.out.println("Real angle : " + realAngle + ", Module : " +realAngle%90);

        double newVelX = 0;
        double newVelY = 0;
        if (realAngle >= 0 && realAngle <= 90) {
            newVelX = velocityX + (90 - realAngleModulo) * COEFF_REDUCTEUR * elapsedTime;
            newVelY = velocityY + (realAngleModulo) * COEFF_REDUCTEUR * elapsedTime;
        }

        if (realAngle > 90 && realAngle <= 180) {
            newVelX = velocityX - realAngleModulo * COEFF_REDUCTEUR * elapsedTime;
            newVelY = velocityY + (90 - realAngleModulo) * COEFF_REDUCTEUR * elapsedTime;
        }

        if (realAngle > 180 && realAngle <= 270) {
            newVelX = velocityX - (90 - realAngleModulo) * COEFF_REDUCTEUR * elapsedTime;
            newVelY = velocityY - realAngleModulo * COEFF_REDUCTEUR * elapsedTime;
        }

        if (realAngle > 270 && realAngle <= 360) {
            newVelX = velocityX + realAngleModulo * COEFF_REDUCTEUR * elapsedTime;
            newVelY = velocityY - (90 - realAngleModulo) * COEFF_REDUCTEUR * elapsedTime;
        }

        if (!(Math.abs(newVelX) > VELOCITY_MAX)) {
            velocityX = newVelX;
        }

        if (!(Math.abs(newVelY) > VELOCITY_MAX)) {
            velocityY = newVelY;
        }
    }

    public void refreshPosition(double elapsedTime) {
        posX.setValue(posX.getValue() + velocityX * elapsedTime);
        posY.setValue(posY.getValue() + velocityY * elapsedTime);
        if (velocityX > 0) {
            velocityX = velocityX - (1 * COEFF_WIND_BRAKE);
        } else if (velocityX < 0) {
            velocityX = velocityX + (1 * COEFF_WIND_BRAKE);
        }
        if (velocityY > 0) {
            velocityY = velocityY - (1 * COEFF_WIND_BRAKE);
        } else if (velocityY < 0) {
            velocityY = velocityY + (1 * COEFF_WIND_BRAKE);
        }
    }

    public void turn(double angle) {
        if (rotation.getValue() + angle > 360) {
            rotation.setValue((rotation.getValue() + angle) - 360);
        } else if (rotation.getValue() + angle < 0) {
            rotation.setValue(360 + (rotation.getValue() + angle));
        } else {
            rotation.setValue(rotation.getValue() + angle);
        }
    }

    public void looseLife() {
        nbLives.set(nbLives.get() - 1);
        lastTimeHittingBullet = LocalDateTime.now();
    }


    public List<WeaponBullet> fire(ImageView shipImageView) {
        List<WeaponBullet> weaponBullets = new ArrayList<>();
        if (munitions.get() > 0 && (lastFireTime == 0l || (System.currentTimeMillis() - lastFireTime > 50))) {
            //boolean playing = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
            lastFireTime = System.currentTimeMillis();
            weaponBullets.add(new WeaponBullet(getRealAngle(), shipImageView, velocityX, velocityY));
            munitions.set(munitions.get() - 1);
            BulletGame.getGameController().getSoundMode().playWeaponFire();

        }
        return weaponBullets;
    }

    public void consumeBonus(MunitionsBonus munitionsBonus) {
        this.munitions.set(this.munitions.add(munitionsBonus.getNbMunitions()).get());
    }

    public double getMassValue() {
        return mass.getValue();
    }

    @Override
    public void fusion(MovingObject movingObject) {

    }

}
