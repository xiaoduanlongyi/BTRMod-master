package btrmod.patches;

import basemod.ReflectionHacks;
import btrmod.character.KessokuBandChar;
import btrmod.effects.StartingRelicEffect;
import btrmod.events.BtrStartingEvent;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;

import static btrmod.BTRMod.makeID;

public class StartingRelicPatch {
    private static final String EVENT_ID = makeID("BtrStartingEvent");
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(EVENT_ID);
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;

    // 标记是否已经给过遗物
    private static boolean relicGiven = false;

    // 强制显示Neow祝福
    @SpirePatch(clz = NeowRoom.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
    public static class ForceBlessing {
        @SpireInsertPatch(rloc = 1)
        public static void Insert(NeowRoom __instance, boolean isBoss) {
            Settings.isTestingNeow = true;
        }
    }

    // 添加自定义选项到Neow房间
    @SpirePatch(clz = NeowRoom.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
    public static class AddRelicButton {
        @SpirePostfixPatch
        public static void Postfix(NeowRoom __instance, boolean isBoss) {
            if (!isBoss && !relicGiven && isPlayingKessokuBand()) {
                __instance.event.roomEventText.clear();
                __instance.event.roomEventText.addDialogOption(OPTIONS[2]); // 使用OPTIONS[2]作为按钮文本
            }
        }
    }

    // 清除Neow事件的图片文本
    @SpirePatch(clz = NeowEvent.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {boolean.class})
    public static class FixEventImage {
        @SpirePostfixPatch
        public static void Postfix(NeowEvent __instance, boolean isDone) {
            if (!relicGiven && isPlayingKessokuBand()) {
                __instance.imageEventText.clear();
            }
        }
    }

    // 处理按钮点击
    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect")
    public static class HandleButtonClick {
        @SpireInsertPatch(rloc = 0)
        public static SpireReturn<Void> Prefix(NeowEvent __instance, int buttonPressed) {
            if (!relicGiven && buttonPressed == 0 && isPlayingKessokuBand()) {
                // 调用私有方法dismissBubble
                ReflectionHacks.privateMethod(NeowEvent.class, "dismissBubble").invoke(__instance);

                // 标记已给予遗物
                relicGiven = true;

                // 添加触发事件的特效
                AbstractDungeon.effectList.add(new StartingRelicEffect());

                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    // 重置标记
    @SpirePatch(clz = AbstractDungeon.class, method = "generateSeeds")
    public static class ResetFlag {
        @SpirePostfixPatch
        public static void Postfix() {
            relicGiven = false;
        }
    }

    // 辅助方法：检查是否在玩KessokuBandChar
    private static boolean isPlayingKessokuBand() {
        return AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass == KessokuBandChar.Meta.KessokuBand;
    }
}