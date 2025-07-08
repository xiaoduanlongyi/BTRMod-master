//package btrmod.patches;
//
//import btrmod.cards.BaseCard;
//import btrmod.util.CardTagEnum;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//
//@SpirePatch(
//        clz = AbstractCard.class,
//        method = "onMoveToDiscard"
//)
//public class GrooveGrantResetPatch {
//    @SpirePostfixPatch
//    public static void postfix(AbstractCard __instance) {
//        if (!__instance.tags.contains(CardTagEnum.GROOVE_GRANT)) {
//            return;
//        }
//
//        if (__instance instanceof BaseCard) {
//            BaseCard card = (BaseCard) __instance;
//            // 重置 GRV 自定义变量
//            if (card.hasCustomVar("GRV")) {
//                card.resetCustomVar("GRV");
//            }
//        }
//
//        // restore to base value
//        __instance.magicNumber = __instance.baseMagicNumber;
//        __instance.isMagicNumberModified = false;
//        __instance.initializeDescription();
//    }
//}