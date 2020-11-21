package fr.slapker.bullet.models;

import fr.slapker.bullet.constants.BulletScreenParam;

import java.util.Random;

public class BulletGenerator {

    public static Bullet makeBullet(Bullet fromBullet, Integer bulletStartingVelocity) {
        Random random = new Random();
        double randomVelocityY = random.nextInt(bulletStartingVelocity);
        double randomVelocityX = random.nextInt(bulletStartingVelocity);
        if (fromBullet.getVelocityY() < 0) {
            randomVelocityY = -randomVelocityY;
        }
        if (fromBullet.getVelocityX() < 0) {
            randomVelocityX = -randomVelocityX;
        }

        return new Bullet(fromBullet.getPosX().getValue(), fromBullet.getPosY().getValue(), randomVelocityX, randomVelocityY);
    }

    public static Bullet makeBullet(Integer bulletStartingVelocity) {
        Random random = new Random();
        double randomVelocityY = getRandomVel(bulletStartingVelocity);
        double randomVelocityX = getRandomVel(bulletStartingVelocity);

        double randomPosX = BulletScreenParam.getInstance().getWindowBulletX() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletWidth());
        double randomPosY = BulletScreenParam.getInstance().getWindowBulletY() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletHeight());

        return new Bullet(randomPosX, randomPosY, randomVelocityY, randomVelocityX);
    }

/*    public static Bullet makeBullet(GameMode gameMode,Integer bulletStartingVelocity) {
        Random random = new Random();
        double randomVelocityY;
        double randomVelocityX;

        randomVelocityY = getRandomVel(bulletStartingVelocity);
        randomVelocityX = getRandomVel(bulletStartingVelocity);

        double randomPosX = BulletConstants.getWindowBulletX() + random.nextInt(BulletConstants.WINDOW_BULLET_WIDTH);
        double randomPosY = BulletConstants.getWindowBulletY() + random.nextInt(BulletConstants.WINDOW_BULLET_HEIGHT);

        return new Bullet(randomPosX,randomPosY,randomVelocityY,randomVelocityX, BulletConstants.getBulletStartingRadius());
    }*/

    public static Integer getRandomVel(Integer bulletStartingVelocity) {
        Random random = new Random();
        Integer randomVelc = random.nextInt(bulletStartingVelocity);
        if (randomVelc % 2 == 0) {
            randomVelc = -randomVelc;
        }
        return randomVelc;
    }

    public static Bullet makeGravityBullet(int maxStarSize) {
        Random random = new Random();

        double randomVelocityY = getRandomVel(2);
        double randomVelocityX = getRandomVel(2);

        double randomPosX = BulletScreenParam.getInstance().getWindowBulletX() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletWidth());
        double randomPosY = BulletScreenParam.getInstance().getWindowBulletY() + random.nextInt(BulletScreenParam.getInstance().getWindowBulletHeight());
        double radiusBullet = (double) (random.nextInt(maxStarSize)) / (double) (10);
        return new Bullet(randomPosX, randomPosY, randomVelocityY, randomVelocityX, radiusBullet);
    }
}
