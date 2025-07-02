package btrmod.util;

import btrmod.actions.SwitchBGMAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BgmManager {
    private static final Logger logger = LogManager.getLogger(BgmManager.class);

    // 当前自定义BGM的key
    private static String currentCustomBGMKey = null;

    // 原始BGM信息
    private static String originalBGMKey = null;
    private static boolean wasTemp = false;
    private static boolean hasStoredOriginalBGM = false;

    /**
     * 播放自定义BGM（推荐使用这个方法）
     * @param bgmPath BGM文件路径（相对于audio文件夹）
     */
    public static void playCustomBGM(String bgmPath) {
        // 使用Action来处理BGM切换，避免同时播放多个BGM
        if (isPlayingCustomBGM()) {
            // 如果已经在播放自定义BGM，使用淡出效果
            AbstractDungeon.actionManager.addToBottom(new SwitchBGMAction(bgmPath, 0.5f));
        } else {
            // 第一次播放，立即切换
            AbstractDungeon.actionManager.addToBottom(new SwitchBGMAction(bgmPath));
        }
    }

    /**
     * 直接播放BGM（由Action调用）
     */
    public static void playCustomBGMDirect(String bgmPath) {
        try {
            // 创建自定义BGM的key
            String customKey = "CUSTOM_" + bgmPath
                    .replace(".mp3", "_mp3")
                    .replace(".ogg", "_ogg")
                    .replace("/", "_");

            // 只在第一次播放自定义BGM时保存原始状态
            if (!hasStoredOriginalBGM) {
                saveCurrentBGMState();
                hasStoredOriginalBGM = true;
            }

            currentCustomBGMKey = customKey;

            // 通过游戏的音乐系统播放
            CardCrawlGame.music.playTempBgmInstantly(customKey);

            logger.info("Playing custom BGM: " + bgmPath + " with key: " + customKey);
        } catch (Exception e) {
            logger.error("Failed to play custom BGM: " + bgmPath, e);
        }
    }

    /**
     * 恢复原始BGM
     */
    public static void restoreOriginalBGM() {
        if (currentCustomBGMKey == null) {
            return;
        }

        try {
            currentCustomBGMKey = null;

            // 淡出当前BGM
            CardCrawlGame.music.fadeOutTempBGM();

            // 恢复原始BGM
            if (originalBGMKey != null) {
                if (wasTemp) {
                    CardCrawlGame.music.playTempBgmInstantly(originalBGMKey);
                } else {
                    CardCrawlGame.music.changeBGM(originalBGMKey);
                }
                logger.info("Restored original BGM: " + originalBGMKey);
            } else {
                // 根据当前场景播放默认BGM
                playDefaultBGM();
            }

            originalBGMKey = null;
            wasTemp = false;
        } catch (Exception e) {
            logger.error("Failed to restore original BGM", e);
        }
    }

    /**
     * 保存当前BGM状态
     */
    private static void saveCurrentBGMState() {
        if (AbstractDungeon.getCurrRoom() != null) {
            AbstractRoom room = AbstractDungeon.getCurrRoom();

            // 根据房间类型确定BGM
            if (room.monsters != null && !room.monsters.areMonstersBasicallyDead()) {
                if (AbstractDungeon.bossList != null && !AbstractDungeon.bossList.isEmpty()) {
                    wasTemp = true;
                    originalBGMKey = getBossBGMKey();
                } else if (room.eliteTrigger) {
                    wasTemp = true;
                    originalBGMKey = "ELITE";
                } else {
                    wasTemp = false;
                    originalBGMKey = getActBGMKey();
                }
            } else {
                wasTemp = false;
                originalBGMKey = getActBGMKey();
            }
        }
    }

    /**
     * 获取当前楼层的BGM key
     */
    private static String getActBGMKey() {
        if (AbstractDungeon.id != null) {
            switch (AbstractDungeon.id) {
                case "Exordium":
                    return "Exordium";
                case "TheCity":
                    return "TheCity";
                case "TheBeyond":
                    return "TheBeyond";
                case "TheEnding":
                    return "TheEnding";
            }
        }
        return "Exordium";
    }

    /**
     * 获取Boss BGM key
     */
    private static String getBossBGMKey() {
        if (AbstractDungeon.id != null) {
            switch (AbstractDungeon.id) {
                case "Exordium":
                    return "BOSS_BOTTOM";
                case "TheCity":
                    return "BOSS_CITY";
                case "TheBeyond":
                    return "BOSS_BEYOND";
                case "TheEnding":
                    return "BOSS_ENDING";
            }
        }
        return "BOSS_BOTTOM";
    }

    /**
     * 播放默认BGM
     */
    private static void playDefaultBGM() {
        if (AbstractDungeon.getCurrRoom() != null) {
            AbstractDungeon.getCurrRoom().playBGM(null);
        }
    }

    /**
     * 检查是否正在播放自定义BGM
     */
    public static boolean isPlayingCustomBGM() {
        return currentCustomBGMKey != null;
    }
}