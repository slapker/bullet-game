package fr.slapker.bullet.views.bonus;

import fr.slapker.bullet.models.bonus.Bonus;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlackHoleBonusView extends AbstractBonusView {


    public BlackHoleBonusView(Bonus pMunitionsBonus) {
        super(pMunitionsBonus);
        imageBonus = new Image(getClass().getClassLoader().getResource("images/blackHole.png").toExternalForm(), image_size, image_size, false, false);
        bonus = pMunitionsBonus;

        imageView.setImage(imageBonus);

    }


}
