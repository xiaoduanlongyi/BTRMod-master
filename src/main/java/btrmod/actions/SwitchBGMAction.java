package btrmod.actions;

import btrmod.util.BgmManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;

public class SwitchBGMAction extends AbstractGameAction {
    private String bgmPath;
    private boolean fadeOutFirst;
    private float fadeOutTime;

    // 立即切换
    public SwitchBGMAction(String bgmPath) {
        this(bgmPath, false, 0f);
    }

    // 带淡出效果的切换
    public SwitchBGMAction(String bgmPath, float fadeOutTime) {
        this(bgmPath, true, fadeOutTime);
    }

    private SwitchBGMAction(String bgmPath, boolean fadeOutFirst, float fadeOutTime) {
        this.bgmPath = bgmPath;
        this.fadeOutFirst = fadeOutFirst;
        this.fadeOutTime = fadeOutTime;
        this.duration = fadeOutFirst ? fadeOutTime : Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (fadeOutFirst && duration > 0f) {
            // 第一次调用时开始淡出
            if (duration == fadeOutTime) {
                CardCrawlGame.music.fadeOutTempBGM();
            }

            // 等待淡出
            duration -= Settings.ACTION_DUR_XFAST;
            return;
        }

        // 播放新BGM
        BgmManager.playCustomBGMDirect(bgmPath);
        isDone = true;
    }
}