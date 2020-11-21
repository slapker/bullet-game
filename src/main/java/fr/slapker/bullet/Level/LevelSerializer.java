package fr.slapker.bullet.Level;

import fr.slapker.bullet.constants.BulletScreenParam;
import fr.slapker.bullet.launcher.Game;
import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class LevelSerializer {
    public static LevelSerializable loadLevelFromClassPathFile(String resourcePath) {
        try {
            InputStream inputStream = LevelSerializer.class.getClassLoader().getResourceAsStream(resourcePath);
            ObjectInputStream oi = new ObjectInputStream(inputStream);
            LevelSerializable level = (LevelSerializable) oi.readObject();
            return level;
        } catch (Exception e) {
            System.out.println("erreur deserialize");

        }
        return null;
    }

    public static LevelSerializable loadLevelFromFile(String filePath) {
        System.out.println("Filepath = " + filePath);
        try {
            File file = new File(filePath);

            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);
            LevelSerializable level = (LevelSerializable) oi.readObject();
            return level;
        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }

    public static LevelSerializable serializeLeve(LevelMaker levelMaker) {

        LevelSerializable level = new LevelSerializable();
        levelMaker.getAstroList().stream().forEach(astro -> {
            level.addAstronaut(astro);
        });
        levelMaker.getBulletList().stream().forEach(bullet -> {
            level.addBullet(bullet);
        });
        levelMaker.getBonuses().stream().forEach(bonus -> {
            level.addBonus(bonus);
        });

        return level;
    }

    public static void serializeLevelAndSaveToFile(LevelMaker levelMaker, File file) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        String date = formatter.format(currentDateTime);
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(serializeLeve(levelMaker));
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public static void loadLevelInGame(LevelSerializable level, Game game) {
        double screenRatioX = BulletScreenParam.getInstance().getScreenRatioX();
        double screenRatioY = BulletScreenParam.getInstance().getScreenRatioY();

        for (int i = 0; i < level.getAstronautList().size(); i++) {
            Astronaut astronaut = new Astronaut();
            Random r = new Random();
            double defaultInertie = 1 + r.nextInt(70);
            if (defaultInertie % 2 == 0) {
                defaultInertie = -defaultInertie;
            }
            astronaut.setRotationInertie(defaultInertie);
            astronaut.getPosX().setValue(level.getAstronautList().get(i).getPosX() * screenRatioX);
            astronaut.getPosY().setValue(level.getAstronautList().get(i).getPosY() * screenRatioY);
            game.getAstronauts().add(astronaut);
        }

        level.getBulletList().forEach(bulletSerial -> {
            Bullet bullet = new Bullet(bulletSerial.getPosX() * screenRatioX, bulletSerial.getPosY() * screenRatioY, 0, 0);
            bullet.setRotationCenterX(bulletSerial.getRotationCenterPosX() * screenRatioX);
            bullet.setRotationCenterY(bulletSerial.getRotationCenterPosY() * screenRatioY);
            bullet.setMouvementEnum(bulletSerial.getMouvementEnum());
            bullet.setRotationClockwise(bulletSerial.isRotationClockwise());
            bullet.setRotationSpeed(bulletSerial.getRotationSpeed());
            bullet.setInitialDirectionX(bulletSerial.getInitialDirectionX());
            bullet.setInitialDirectionY(bulletSerial.getInitialDirectionY());
            bullet.getBulletRadius().setValue(bulletSerial.getBulletRadius());
            game.getBulletList().add(bullet);
        });

        level.getBonusList().forEach(bonusSerial -> {
            if (bonusSerial.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_MUNITIONS)) {
                MunitionsBonus munitionsBonus = new MunitionsBonus(bonusSerial.getPosX() * screenRatioX, bonusSerial.getPosY() * screenRatioY, bonusSerial.getNbMunitions());
                game.getBonuses().add(munitionsBonus);
            } else if (bonusSerial.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_HOURGLASS)) {
                HourglassBonus hourglassBonus = new HourglassBonus(bonusSerial.getPosX() * screenRatioX, bonusSerial.getPosY() * screenRatioY, bonusSerial.getDurationSecondes());
                game.getBonuses().add(hourglassBonus);
            }
        });

    }

    public static void loadLevelInLevelMaker(LevelSerializable level, LevelMaker levelMaker) {
        double screenRatioX = BulletScreenParam.getInstance().getScreenRatioX();
        double screenRatioY = BulletScreenParam.getInstance().getScreenRatioY();

        for (int i = 0; i < level.getAstronautList().size(); i++) {
            Astronaut astronaut = new Astronaut();
            Random r = new Random();
            double defaultInertie = 1 + r.nextInt(70);
            if (defaultInertie % 2 == 0) {
                defaultInertie = -defaultInertie;
            }
            astronaut.setRotationInertie(defaultInertie);
            astronaut.getPosX().setValue(level.getAstronautList().get(i).getPosX() * screenRatioX);
            astronaut.getPosY().setValue(level.getAstronautList().get(i).getPosY() * screenRatioY);
            levelMaker.getAstroList().add(astronaut);
        }

        level.getBulletList().forEach(bulletSerial -> {
            Bullet bullet = new Bullet(bulletSerial.getPosX() * screenRatioX, bulletSerial.getPosY() * screenRatioY, 0, 0);
            bullet.setRotationCenterX(bulletSerial.getRotationCenterPosX() * screenRatioX);
            bullet.setRotationCenterY(bulletSerial.getRotationCenterPosY() * screenRatioY);
            bullet.setMouvementEnum(bulletSerial.getMouvementEnum());
            bullet.setRotationClockwise(bulletSerial.isRotationClockwise());
            bullet.setRotationSpeed(bulletSerial.getRotationSpeed());
            bullet.setInitialDirectionY(bulletSerial.getInitialDirectionY());
            bullet.setInitialDirectionX(bulletSerial.getInitialDirectionX());
            bullet.getBulletRadius().setValue(bulletSerial.getBulletRadius());
            levelMaker.getBulletList().add(bullet);
        });

        level.getBonusList().forEach(bonusSerial -> {
            if (bonusSerial.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_MUNITIONS)) {
                MunitionsBonus munitionsBonus = new MunitionsBonus(bonusSerial.getPosX() * screenRatioX, bonusSerial.getPosY() * screenRatioY, bonusSerial.getNbMunitions());
                levelMaker.getBonuses().add(munitionsBonus);
            } else if (bonusSerial.getItemLevelMakerEnum().equals(ItemLevelMakerEnum.BONUS_HOURGLASS)) {
                HourglassBonus hourglassBonus = new HourglassBonus(bonusSerial.getPosX() * screenRatioX, bonusSerial.getPosY() * screenRatioY, bonusSerial.getDurationSecondes());
                levelMaker.getBonuses().add(hourglassBonus);
            }
        });

    }
}
