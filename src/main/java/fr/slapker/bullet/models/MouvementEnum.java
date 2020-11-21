package fr.slapker.bullet.models;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public enum MouvementEnum implements Serializable {
    ROTATION(1),
    INITIAL_FORCE(2);

    private int code;
}
