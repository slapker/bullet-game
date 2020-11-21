package fr.slapker.bullet.Level;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LevelMakerActionEnum {
    ROTATION(1),
    INITIAL_FORCE(2),
    CHANGE_MASS(3);

    private int code;

}
