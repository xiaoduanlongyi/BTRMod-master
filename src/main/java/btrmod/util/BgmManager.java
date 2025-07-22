package btrmod.util;

import btrmod.actions.SwitchBGMAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BgmManager {
    private static final Logger logger = LogManager.getLogger(BgmManager.class);

    // 当前自定义BGM的key
    private static String currentCustomBGMKey = null;

    // 当前自定义BGM的原始路径 - 新增这个字段来追踪当前播放的BGM路径
    private static String currentCustomBGMPath = null;

    // 原始BGM信息
    private static String originalBGMKey = null;
    private static boolean wasTemp = false;
    private static boolean hasStoredOriginalBGM = false;

    /**
     * 播放自定义BGM（推荐使用这个方法）
     * @param bgmPath BGM文件路径（相对于audio文件夹）
     */
    public static void playCustomBGM(String bgmPath) {
        // 检查是否与当前播放的BGM相同
        if (isPlayingSameBGM(bgmPath)) {
            logger.info("Already playing BGM: " + bgmPath + ", skipping switch");
            return;
        }

        // 使用Action来处理BGM切换，避免同时播放多个BGM
        if (isPlayingCustomBGM()) {
            // 如果已经在播放其他自定义BGM，使用淡出效果
            AbstractDungeon.actionManager.addToBottom(new SwitchBGMAction(bgmPath, 0.5f));
        } else {
            // 第一次播放，立即切换
            AbstractDungeon.actionManager.addToBottom(new SwitchBGMAction(bgmPath));
        }
    }

    /**
     * 检查是否正在播放相同的BGM
     */
    private static boolean isPlayingSameBGM(String bgmPath) {
        return currentCustomBGMPath != null && currentCustomBGMPath.equals(bgmPath);
    }

    /**
     * 直接播放BGM（由Action调用）
     */
    public static void playCustomBGMDirect(String bgmPath) {
        // 再次检查，避免在Action执行时仍然重复播放
        if (isPlayingSameBGM(bgmPath)) {
            logger.info("Already playing BGM: " + bgmPath + ", skipping switch in direct method");
            return;
        }

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
            currentCustomBGMPath = bgmPath; // 保存当前播放的BGM路径

            // 在播放新BGM之前，先停止所有当前的音乐， 这样可以避免在Boss战等情况下出现两个BGM同时播放的问题
            CardCrawlGame.music.silenceTempBgmInstantly();
            CardCrawlGame.music.silenceBGMInstantly();

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
            currentCustomBGMPath = null; // 清除当前BGM路径
            hasStoredOriginalBGM = false; // 重置标志，允许下次播放时重新保存原始BGM

            // 淡出当前BGM
            CardCrawlGame.music.fadeOutTempBGM();

            // 恢复原始BGM
            if (originalBGMKey != null) {
                // 统一使用 playTempBgmInstantly，这样可以确保正确替换当前播放的音乐
                // 避免 changeBGM 可能导致的重复播放问题
                CardCrawlGame.music.playTempBgmInstantly(originalBGMKey);
                logger.info("Restored original BGM: " + originalBGMKey + " (wasTemp: " + wasTemp + ")");
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
                // 检查当前房间是否是Boss房间
                // 使用 monsters 中是否有 boss 类型的怪物来判断
                boolean isBossRoom = false;
                if (room.monsters != null) {
                    for (AbstractMonster m : room.monsters.monsters) {
                        if (m.type == AbstractMonster.EnemyType.BOSS) {
                            isBossRoom = true;
                            break;
                        }
                    }
                }

                if (isBossRoom) {
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

    /**
     * 获取当前播放的BGM路径（用于调试）
     */
    public static String getCurrentBGMPath() {
        return currentCustomBGMPath;
    }
}