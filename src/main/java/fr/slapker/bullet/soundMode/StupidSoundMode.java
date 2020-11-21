package fr.slapker.bullet.soundMode;

public class StupidSoundMode extends AbstractSoundMode {

    public StupidSoundMode() {
        super();
        weaponFire = loadAudioClipFromClassPath("sounds/piou.wav");
        weaponBonus = loadAudioClipFromClassPath("sounds/munition_reload.wav");
        slowMoBonus = loadAudioClipFromClassPath("sounds/slowMo.wav");
        bulletExplosion = loadAudioClipFromClassPath("sounds/explose.wav");
        astroRescue = loadAudioClipFromClassPath("sounds/astro_rescue.wav");
        astroDie = loadAudioClipFromClassPath("sounds/astro_die.wav");
        engine = loadAudioClipFromClassPath("sounds/engine2.wav");
        engineLoop = loadAudioClipFromClassPath("sounds/engineLoop.wav");
        engineOff = loadAudioClipFromClassPath("sounds/engineOff.wav");

        bulletExplosion.setVolume(0.1d);
        weaponFire.setVolume(0.5d);
    }
}
