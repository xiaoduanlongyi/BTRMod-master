package btrmod.patches;

import btrmod.util.BgmManager;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;

import static basemod.BaseMod.logger;
import static btrmod.BTRMod.audioPath;

public class BgmPatch {

    // Patch TempMusic的getSong方法 - 使用SpireInstrumentPatch来处理private方法
    @SpirePatch(
            clz = TempMusic.class,
            method = "getSong",
            paramtypez = {String.class}
    )
    public static class CustomBGMInTempMusic {
        @SpirePrefixPatch  //
        public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {  //
            if (key != null && key.startsWith("CUSTOM_")) {
                // 处理自定义BGM路径
                String cleanPath = key.substring(7) // 移除 "CUSTOM_"
                        .replace("_mp3", ".mp3")
                        .replace("_ogg", ".ogg")
                        .replace("_", "/");

                String fullPath = audioPath(cleanPath);
                try {
                    Music music = MainMusic.newMusic(fullPath);
                    return SpireReturn.Return(music);  // 直接返回Music对象
                } catch (Exception e) {
                    logger.error("Failed to load custom BGM: " + fullPath, e);
                }
            }
            return SpireReturn.Continue();
        }
    }

    // 战斗结束时恢复BGM
    @SpirePatch(
            clz = AbstractRoom.class,
            method = "endBattle"
    )
    public static class RestoreBGMOnBattleEnd {
        @SpirePostfixPatch
        public static void Postfix(AbstractRoom __instance) {
            if (BgmManager.isPlayingCustomBGM()) {
                BgmManager.restoreOriginalBGM();
            }
        }
    }

    // 进入胜利房间时恢复BGM
    @SpirePatch(
            clz = VictoryRoom.class,
            method = "onPlayerEntry"
    )
    public static class RestoreBGMOnVictory {
        @SpirePostfixPatch
        public static void Postfix(VictoryRoom __instance) {
            if (BgmManager.isPlayingCustomBGM()) {
                BgmManager.restoreOriginalBGM();
            }
        }
    }

    // 游戏结束时清理资源
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "closeCurrentScreen"
    )
    public static class CleanupOnClose {
        @SpirePostfixPatch
        public static void Postfix() {
            // 如果是退出游戏
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DEATH) {
                CardCrawlGame.music.dispose();
            }
        }
    }
}