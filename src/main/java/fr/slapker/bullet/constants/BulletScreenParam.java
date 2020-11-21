package fr.slapker.bullet.constants;

import lombok.Getter;
import lombok.Setter;

public class BulletScreenParam {
    private static BulletScreenParam instance;

    private BulletScreenParam() {

    }

    public static BulletScreenParam getInstance() {
        if (instance == null) {
            instance = new BulletScreenParam();
        }
        return instance;
    }

    public boolean DEMO_MODE = false;

    @Getter
    @Setter
    private Integer window_width;

    @Getter
    @Setter
    private Integer window_height;

    private static final Integer bullet_defaut_radius = 20;

    @Getter
    private double weapon_bullet_radius = 2;

    @Getter
    private Integer windowBulletX = 40;

    @Getter
    private Integer windowBulletY = 40;


    public Integer getBulletStartingRadiusSurivalMode() {
        double ratio = window_width / (double) 1920;
        double newRadius = (double) bullet_defaut_radius * ratio;

        return Math.toIntExact(Math.round(newRadius));
    }

    public Integer getWindowBulletWidth() {
        return window_width - (windowBulletX * 2);
    }

    public Integer getWindowBulletHeight() {
        return window_height - (windowBulletY * 2);
    }

    public Integer getWindowBulletMaxX() {
        return windowBulletX + getWindowBulletWidth();
    }

    public Integer getWindowBulletMaxY() {
        return windowBulletY + getWindowBulletHeight();
    }


    public double getScreenRatioX() {
        return (double) window_width / (double) 1920;
    }

    public double getScreenRatioY() {
        return (double) window_height / (double) 1080;
    }
}
