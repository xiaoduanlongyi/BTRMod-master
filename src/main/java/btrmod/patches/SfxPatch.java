package btrmod.patches;

import btrmod.BTRMod;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.evacipated.cardcrawl.modthespire.lib.*;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class SfxPatch {
    private static final Logger logger = LogManager.getLogger(SfxPatch.class);
    private static final String AUDIO_PATH = BTRMod.audioPath("");

    // ========== 音效ID常量 ==========
    // SFX
    public static final String CHAR_CHOOSE = "CHAR_CHOOSE";
    public static final String MULIMULI = "MuliMuli";
    public static final String ESCAPE_GUITAR = "EscapedGuitar";
    public static final String NIJIKA_FOLLOWUP = "NijikaFollowUp";
    public static final String DISTORTED_BOCCHI = "DistortedBocchi";
    public static final String BOCCHI_GHOST = "BocchiGhost";
    public static final String KITAKITA1 = "KitaKita1";
    public static final String KITAKITA2 = "KitaKita2";
    public static final String NIJIKA_SHAKEHEAD = "NijikaShakeHead";
    public static final String RYO_EATGRASS = "RyoEatGrass";
    public static final String SHIMOKITA_ANGEL = "ShimokitaAngel";
    public static final String FADE_AWAY = "FadeAway";
    public static final String BOCCHI_GAMBADE = "BocchiGambade";
    public static final String LEAP_OF_FAITH = "LeapOfFaith";
    public static final String MUSCLE_NIJIKA = "MuscleNijika";
    public static final String RYO_TOUCHFACE = "RyoTouchFace";
    public static final String BANDMAN = "BandMan";
    public static final String BOCCHI_SHOWHEAD = "BocchiShowHead";
    public static final String SWING = "Swing";
    public static final String RYO_EATEAT = "RyoEatEat";
    public static final String PINK_BOCCHI = "PinkBocchi";
    public static final String NIJIKA_GAMBADE = "NijikaGambade";
    public static final String KITA_RAP = "KitaRap";
    public static final String KITA_OMELETTE = "KitaOmelette";
    public static final String KITA_GONE = "KitaGone";
    public static final String FIRE_BOCCHI = "FireBocchi";
    public static final String FALLEN_ANGEL = "FallenAngel";
    public static final String BOCCHI_OMELETTE = "BocchiOmelete";

    // ========== Patch原版SoundMaster ==========
    @SpirePatch(
            clz = SoundMaster.class,
            method = SpirePatch.CONSTRUCTOR
    )
    public static class AddCustomSoundsPatch {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(SoundMaster __instance, HashMap<String, Sfx> ___map) {
            logger.info("Adding BTR custom sounds to SoundMaster...");

            // ======= 此处注册音频 =========
            // SFX
            ___map.put(CHAR_CHOOSE, load("sound/CharChoose.ogg"));
            ___map.put(MULIMULI, load("sound/MuliMuli.ogg"));
            ___map.put(ESCAPE_GUITAR, load("sound/EscapedGuitar.ogg"));
            ___map.put(NIJIKA_FOLLOWUP, load("sound/NijikaFollowUp.ogg"));
            ___map.put(DISTORTED_BOCCHI, load("sound/DistortedBocchi.ogg"));
            ___map.put(BOCCHI_GHOST, load("sound/BocchiGhost.ogg"));
            ___map.put(KITAKITA1, load("sound/KitaKita1.ogg"));
            ___map.put(KITAKITA2, load("sound/KitaKita2.ogg"));
            ___map.put(NIJIKA_SHAKEHEAD, load("sound/NijikaShakeHead.ogg"));
            ___map.put(RYO_EATGRASS, load("sound/RyoEatGrass.ogg"));
            ___map.put(SHIMOKITA_ANGEL, load("sound/ShimokitaAngel.ogg"));
            ___map.put(FADE_AWAY, load("sound/FadeAway.ogg"));  
            ___map.put(BOCCHI_GAMBADE, load("sound/BocchiGambade.ogg"));
            ___map.put(LEAP_OF_FAITH, load("sound/LeapOfFaith.ogg"));
            ___map.put(MUSCLE_NIJIKA, load("sound/MuscleNijika.ogg"));
            ___map.put(RYO_TOUCHFACE, load("sound/RyoTouchFace.ogg"));
            ___map.put(BANDMAN, load("sound/BandMan.ogg"));
            ___map.put(BOCCHI_SHOWHEAD, load("sound/BocchiShowHead.ogg"));
            ___map.put(SWING, load("sound/Swing.ogg"));
            ___map.put(RYO_EATEAT, load("sound/RyoEatEat.ogg"));
            ___map.put(PINK_BOCCHI, load("sound/PinkBocchi.ogg"));
            ___map.put(NIJIKA_GAMBADE, load("sound/NijikaGambade.ogg"));
            ___map.put(KITA_RAP, load("sound/KitaRap.ogg"));
            ___map.put(KITA_OMELETTE, load("sound/KitaOmelette.ogg"));
            ___map.put(KITA_GONE, load("sound/KitaGone.ogg"));
            ___map.put(FIRE_BOCCHI, load("sound/FireBocchi.ogg"));
            ___map.put(FALLEN_ANGEL, load("sound/FallenAngel.ogg"));
            ___map.put(BOCCHI_OMELETTE, load("sound/BocchiOmelette.ogg"));

            logger.info("BTR sounds added successfully!");
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                // 在构造函数的最后插入我们的代码
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(
                        SoundMaster.class, "map"
                );
                int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
                return new int[]{found[found.length - 1]};
            }
        }

        private static Sfx load(String filename) {
            return new Sfx(AUDIO_PATH + filename, false);
        }
    }

    // ========== 便捷播放方法 ==========

    /**
     * 播放音效
     */
    public static void play(String key) {
        CardCrawlGame.sound.play(key);
    }

    /**
     * 播放音效（带音调变化）
     */
    public static void play(String key, float pitch) {
        CardCrawlGame.sound.play(key, pitch);
    }

    /**
     * 播放音效（带音量调整）
     */
    public static void playV(String key, float volume) {
        CardCrawlGame.sound.playV(key, volume);
    }

    /**
     * 播放音效（带音调和音量调整）
     */
    public static void playAV(String key, float pitch, float volume) {
        CardCrawlGame.sound.playAV(key, pitch, volume);
    }
}