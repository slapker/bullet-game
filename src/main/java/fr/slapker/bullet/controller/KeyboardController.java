package fr.slapker.bullet.controller;

import javafx.scene.Scene;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KeyboardController {

    private ArrayList<String> inputs = new ArrayList<String>();
    private ArrayList<String> releasedInputs = new ArrayList<String>();

    public List<String> initializeInputControls(Scene theScene) {
        theScene.setOnKeyPressed(e -> {
            String code = e.getCode().toString();
            if (!inputs.contains(code))
                inputs.add(code);
        });

        theScene.setOnKeyReleased(e -> {
            String code = e.getCode().toString();
            inputs.remove(code);
            releasedInputs.add(code);
        });

        return inputs;
    }
}
