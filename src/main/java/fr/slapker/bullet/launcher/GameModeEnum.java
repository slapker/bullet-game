package fr.slapker.bullet.launcher;

import lombok.Getter;

@Getter
public enum GameModeEnum {
    SURVIVAL_EASY("survival_easy.properties"),
    SURVIVAL_NORMAL("survival_normal.properties"),
    SURVIVAL_HARDCORE("survival_hardcore.properties"),

    RESCUE("rescue.properties"),

    GRAVITY("");

    private String file;

    GameModeEnum(String file) {
        this.file = file;
    }
}
