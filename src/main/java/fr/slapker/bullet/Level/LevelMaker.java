package fr.slapker.bullet.Level;

import fr.slapker.bullet.models.Astronaut;
import fr.slapker.bullet.models.Bullet;
import fr.slapker.bullet.models.bonus.Bonus;
import fr.slapker.bullet.models.bonus.HourglassBonus;
import fr.slapker.bullet.models.bonus.MunitionsBonus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LevelMaker {
    private ObservableList<Bullet> bulletList = FXCollections.observableArrayList();
    private ObservableList<Astronaut> astroList = FXCollections.observableArrayList();
    private ObservableList<Bonus> bonuses = FXCollections.observableArrayList();
    private ItemLevelMakerEnum itemLevelMakerEnum;
    private LevelMakerActionEnum actionMakerEnum;
    private Bullet selectedBullet;
    private Bonus selectedBonus;
    private double actualMouseX;
    private double actualMouseY;

    public void addBullet(double x, double y) {
        bulletList.add(new Bullet(x, y, 0, 0, 20d));
    }

    public void addAstro(double x, double y) {
        astroList.add(new Astronaut(x, y));
    }

    public void addBonusHourGlass(double x, double y) {
        HourglassBonus hourglassBonus = new HourglassBonus(x, y);
        bonuses.add(hourglassBonus);
    }

    public void addMunitionBonus(double x, double y) {
        MunitionsBonus munitionsBonus = new MunitionsBonus(x, y);
        bonuses.add(munitionsBonus);
    }

}
