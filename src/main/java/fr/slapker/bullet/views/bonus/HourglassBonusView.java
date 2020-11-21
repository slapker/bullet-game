package fr.slapker.bullet.views.bonus;

import fr.slapker.bullet.models.bonus.Bonus;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourglassBonusView extends AbstractBonusView {


    public HourglassBonusView(Bonus hourglassBonus) {
        super(hourglassBonus);
        imageBonus = new Image(getClass().getClassLoader().getResource("images/hourglass.png").toExternalForm(), image_size, image_size, false, false);
        bonus = hourglassBonus;

        imageView.setImage(imageBonus);

    }


}
