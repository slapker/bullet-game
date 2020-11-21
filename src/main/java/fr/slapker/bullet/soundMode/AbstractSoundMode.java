package fr.slapker.bullet.soundMode;

import javafx.scene.media.AudioClip;
import lombok.Getter;
import lombok.Setter;

import java.io.File;


@Setter
public abstract class AbstractSoundMode {

    protected AudioClip weaponFire;

    protected AudioClip bulletExplosion;

    @Getter
    protected AudioClip engine;
    @Getter
    protected AudioClip astroDie;
    @Getter
    protected AudioClip astroRescue;
    @Getter
    protected AudioClip slowMoBonus;
    @Getter
    protected AudioClip weaponBonus;

    protected AudioClip engineOff;
    @Getter
    protected AudioClip engineLoop;
    protected double sloMoRate = 0.5d;
    protected double defaultRate = 1d;

    protected Thread engineThread;

    protected boolean engineShouldLoop = false;


    protected AudioClip loadAudioClip(String resource) {
        AudioClip audioClip = new AudioClip(new File(resource).toURI().toString());
        return audioClip;
    }

    protected AudioClip loadAudioClipFromClassPath(String resource) {
        ;
        AudioClip audioClip = new AudioClip(getClass().getClassLoader().getResource(resource).toString());
        return audioClip;
    }

    public void enableSloMoSound() {
        changeSoundRate(sloMoRate);
    }

    public void disableSloMoSound() {
        changeSoundRate(defaultRate);
    }

    public void playEngine() {
        engineThread = new Thread(() -> {
            if (!engine.isPlaying()) {
                if (!engineShouldLoop) {
                    engineShouldLoop = true;
                    engine.play();
                } else {
                    if (!engineLoop.isPlaying()) {
                        engineLoop.play();
                    }
                }
            }
            return;
        });
        engineThread.start();
    }

    public void stopEngine() {
        new Thread(() -> {
            if (engine.isPlaying()) {
                engine.stop();
            }
            engineShouldLoop = false;
            return;
        }).start();
    }

    public void playWeaponFire() {
        new Thread((Runnable) () -> {

            weaponFire.play();
            return;
        }).start();
    }

    public void playExplosion(double volume) {
        new Thread((Runnable) () -> {
            weaponFire.setVolume(volume);
            weaponFire.play();
            return;
        }).start();
    }

    public void playEngineOff() {
        new Thread((Runnable) () -> {
            engineLoop.stop();
            engine.stop();
            engineOff.play();
            return;
        }).start();
    }

    public void playBulletExplosion(double volume) {
        new Thread(() -> {
            bulletExplosion.setVolume(0.5d);
            bulletExplosion.play();
            return;
        }).start();
    }

    private void changeSoundRate(double rate) {
        weaponFire.setRate(rate);
        bulletExplosion.setRate(rate);
        astroRescue.setRate(rate);
    }


}
