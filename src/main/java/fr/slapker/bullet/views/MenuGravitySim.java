package fr.slapker.bullet.views;

import fr.slapker.bullet.launcher.BulletGame;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class MenuGravitySim {

    @FXML
    Slider gravitySlider;
    @FXML
    Slider starSizeSlider;

    @FXML
    Slider starNumberSlider;


    @FXML
    Slider timeSlider;

    @FXML
    Label labelGravity;

    @FXML
    Label infoTimeSpeed;

    @FXML
    Label infoStarSize;

    @FXML
    Label infoStartNumber;

    @FXML
    public void initialize() {
        gravitySlider.setMin(0.1d);
        gravitySlider.setMax(400);
        gravitySlider.setValue(1);
        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(labelGravity.textProperty(), gravitySlider.valueProperty(), converter);
        Bindings.bindBidirectional(infoTimeSpeed.textProperty(), timeSlider.valueProperty(), converter);

        starNumberSlider.setMin(1);
        starNumberSlider.setMax(5000);
        starNumberSlider.setValue(1000);
        starNumberSlider.valueProperty().addListener((obs, oldval, newVal) ->
                starNumberSlider.setValue(newVal.intValue()));
        Bindings.bindBidirectional(infoStartNumber.textProperty(), starNumberSlider.valueProperty(), converter);

        starSizeSlider.setMin(1);
        starSizeSlider.setMax(1000);
        starSizeSlider.setValue(80);
        starSizeSlider.valueProperty().addListener((obs, oldval, newVal) ->
                starSizeSlider.setValue(newVal.intValue()));
        Bindings.bindBidirectional(infoStarSize.textProperty(), starSizeSlider.valueProperty(), converter);

        timeSlider.setMin(0.1d);
        timeSlider.setMax(4d);
        timeSlider.setValue(1d);

    }

    @FXML
    private void startGravitySim() {
        BulletGame.getPrimaryStage().setFullScreen(true);

        BulletGame.getGameController().startGravityGame(gravitySlider.getValue(), starNumberSlider.getValue(), starSizeSlider.getValue(),
                timeSlider.getValue());
    }


    @FXML
    private void back() {
        BulletGame.getPrimaryStage().setFullScreen(true);
        BulletGame.getMenu().loadMenu();
    }

}
