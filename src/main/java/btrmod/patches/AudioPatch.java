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

public class AudioPatch {
    private static final Logger logger = LogManager.getLogger(AudioPatch.class);
    private static final String AUDIO_PATH = BTRMod.audioPath("");

    // ========== 音效ID常量 ==========

    public static final String CHAR_CHOOSE = "CHAR_CHOOSE";
    public static final String MULIMULI = "MuliMuli";


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
            ___map.put(CHAR_CHOOSE, load("sound/CharChoose.ogg"));
            ___map.put(MULIMULI, load("sound/MuliMuli.ogg"));

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