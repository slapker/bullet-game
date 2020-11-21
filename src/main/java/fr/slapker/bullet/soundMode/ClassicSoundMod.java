package fr.slapker.bullet.soundMode;

public class ClassicSoundMod extends AbstractSoundMode {

    public ClassicSoundMod() {
        super();
        weaponFire = loadAudioClipFromClassPath("sounds/laserShot.wav");
        bulletExplosion = loadAudioClipFromClassPath("sounds/explosion.wav");
        weaponBonus = loadAudioClipFromClassPath("sounds/munition_reload.wav");
        slowMoBonus = loadAudioClipFromClassPath("sounds/slowMo.wav");
        astroRescue = loadAudioClipFromClassPath("sounds/astro_rescue.wav");
        astroDie = loadAudioClipFromClassPath("sounds/astro_die.wav");
        engine = loadAudioClipFromClassPath("sounds/engine2.wav");
        engineLoop = loadAudioClipFromClassPath("sounds/engineLoop.wav");
        engineOff = loadAudioClipFromClassPath("sounds/engineOff.wav");

    }
}
