package fr.slapker.bullet.Level;

public enum ItemLevelMakerEnum {
    ASTRONAUT(1),
    BULLET(2),
    BONUS_HOURGLASS(3),
    BONUS_MUNITIONS(4);

    private int code;

    ItemLevelMakerEnum(int code) {
        this.code=code;
    }
}
