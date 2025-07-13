package btrmod.actions;

import btrmod.util.BgmManager;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwitchBGMAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(SwitchBGMAction.class);

    private String bgmPath;
    private boolean fadeOutFirst;
    private float fadeOutTime;
    private boolean fadeStarted = false;

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
        // 如果正在播放相同的BGM，直接结束Action
        if (BgmManager.getCurrentBGMPath() != null && BgmManager.getCurrentBGMPath().equals(bgmPath)) {
            logger.info("Already playing BGM: " + bgmPath + ", skipping in action");
            isDone = true;
            return;
        }

        if (fadeOutFirst && duration > 0f) {
            // 第一次调用时开始淡出
            if (!fadeStarted) {
                CardCrawlGame.music.fadeOutTempBGM();
                fadeStarted = true;
            }

            // 等待淡出
            duration -= Gdx.graphics.getDeltaTime();
            return;
        }

        // 播放新BGM
        BgmManager.playCustomBGMDirect(bgmPath);
        isDone = true;
    }
}