package btrmod.events;

import basemod.abstracts.events.PhasedEvent;
import basemod.abstracts.events.phases.TextPhase;
import btrmod.relics.BocchiStartRelic;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;

import static btrmod.BTRMod.makeID;

public class BtrStartingEvent extends PhasedEvent {
    // 事件ID
    public static final String ID = makeID("BtrStartingEvent");

    // 从本地化文件加载文本
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    // 事件图片路径
    private static final String IMG = "btrmod/images/events/BtrStartingEvent.jpg"; // 你需要添加对应的图片

    // 要给予的特定遗物
    private AbstractRelic relicToGive;
    private MapRoomNode currNode;
    private MapRoomNode node;

    public BtrStartingEvent() {
        super(ID, NAME, IMG);

        // 创建要给予的遗物实例
        relicToGive = new BocchiStartRelic(); // 替换为你的遗物类

        // 注册开始阶段
        registerPhase("start", new TextPhase(DESCRIPTIONS[0]) // 显示欢迎文本
                .addOption(OPTIONS[0], (i) -> { // 接受遗物的选项
                    // 给予玩家遗物
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                            (float)Settings.WIDTH / 2.0F,
                            (float)Settings.HEIGHT / 2.0F,
                            relicToGive
                    );

                    // 记录到运行历史（可选）
                    AbstractEvent.logMetricObtainRelic(ID, "Accepted Relic", relicToGive);

                    // 转到结束阶段
                    transitionKey("end");
                })
        );

        // 注册结束阶段
        registerPhase("end", new TextPhase(DESCRIPTIONS[1]) // 显示获得遗物后的文本
                .addOption(OPTIONS[1], (i) -> goToNeow()) // 继续到Neow
        );

        // 设置起始阶段
        transitionKey("start");
    }

    @Override
    public void onEnterRoom() {
        RoomEventDialog.waitForInput = true;
    }

    private void goToNeow() {
        // 清除所有对话
        for (AbstractGameEffect f : AbstractDungeon.effectList) {
            if (f instanceof InfiniteSpeechBubble)
                ((InfiniteSpeechBubble) f).dismiss();
        }
        this.imageEventText.clearAllDialogs();
        GenericEventDialog.hide();

        // 获取当前节点
        this.currNode = AbstractDungeon.getCurrMapNode();
        this.node = new MapRoomNode(this.currNode.x, this.currNode.y);

        // 创建Neow房间
        this.node.setRoom(new NeowRoom(false) {
            public void onPlayerEntry() {
                AbstractDungeon.overlayMenu.proceedButton.hide();
                this.event = new NeowEvent(false);
                for (AbstractGameEffect f : AbstractDungeon.effectList) {
                    if (f instanceof InfiniteSpeechBubble)
                        ((InfiniteSpeechBubble) f).dismiss();
                }
                this.event.onEnterRoom();
            }
        });

        // 切换到Neow房间
        AbstractDungeon.dungeonMapScreen.dismissable = true;
        AbstractDungeon.nextRoom = this.node;
        AbstractDungeon.setCurrMapNode(this.node);
        AbstractDungeon.getCurrRoom().onPlayerEntry();
        AbstractDungeon.scene.nextRoom(this.node.room);
        AbstractDungeon.currMapNode.setRoom(new NeowRoom(false));
    }
}