package fr.slapker.bullet.launcher;

import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.Properties;

@NoArgsConstructor
public class GameModeParamLoader {

    public GameModeParam getGameMode(GameModeEnum gameModeEnum) {
        Properties appProps = new Properties();
        try {
            InputStream input = getClass().getResourceAsStream("/mode/"+ gameModeEnum.getFile());
            appProps.load(input);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        GameModeParam gameModeParam = new GameModeParam();
        gameModeParam.setBulletMaxOnScreen(Integer.valueOf(appProps.getProperty("bulletMaxOnScreen")));
        gameModeParam.setBulletStartingNumber(Integer.valueOf(appProps.getProperty("bulletStartingNumber")));
        gameModeParam.setBulletStartVelocity(Integer.valueOf(appProps.getProperty("bulletStartVelocity")));
        gameModeParam.setInitialMunitionAmount(Integer.valueOf(appProps.getProperty("initialMunitionAmount")));
        return gameModeParam;

    }
}
