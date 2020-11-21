package fr.slapker.bullet.views.bonus;

import fr.slapker.bullet.models.bonus.Bonus;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MunitionsBonusView extends AbstractBonusView {


    public MunitionsBonusView(Bonus pMunitionsBonus) {
        super(pMunitionsBonus);
        imageBonus = new Image(getClass().getClassLoader().getResource("images/bonus_munition.png").toExternalForm(), image_size, image_size, false, false);
        bonus = pMunitionsBonus;

        imageView.setImage(imageBonus);

    }


}
