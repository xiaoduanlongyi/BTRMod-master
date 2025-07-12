//package btrmod.patches;
//
//import btrmod.powers.GuitarHeroPower;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//
//import static btrmod.util.CardTagEnum.BOCCHI;
//
///**
// * 确保有 freeToPlayOnce 的卡牌显示为 0 费
// */
//@SpirePatch(
//        clz = AbstractCard.class,
//        method = "applyPowers"
//)
//public class GuitarHeroDisplayPatch {
//
//    @SpirePostfixPatch
//    public static void Postfix(AbstractCard __instance) {
//        // 如果卡牌有 freeToPlayOnce，确保显示为 0 费
//        if (__instance.freeToPlayOnce && __instance.costForTurn > 0) {
//            __instance.setCostForTurn(0);
//        }
//    }
//}
//
///**
// * 确保 freeToPlayOnce 正确工作
// */
//@SpirePatch(
//        clz = AbstractCard.class,
//        method = "hasEnoughEnergy"
//)
//class GuitarHeroEnergyCheckPatch {
//
//    @SpirePostfixPatch
//    public static boolean Postfix(boolean __result, AbstractCard __instance) {
//        // 如果卡牌有 freeToPlayOnce，它总是有足够的能量
//        if (__instance.freeToPlayOnce) {
//            return true;
//        }
//        return __result;
//    }
//}
//
///**
// * 在卡牌使用前确保 GuitarHeroPower 的状态是最新的
// */
//@SpirePatch(
//        clz = AbstractPlayer.class,
//        method = "useCard"
//)
//class GuitarHeroPreUseCardPatch {
//
//    @SpireInsertPatch(
//            locator = Locator.class
//    )
//    public static void Insert(AbstractPlayer __instance, AbstractCard c) {
//        // 在使用卡牌前，确保 GuitarHeroPower 的免费状态是正确的
//        if (__instance.hasPower(GuitarHeroPower.POWER_ID) && c.hasTag(BOCCHI)) {
//            GuitarHeroPower power = (GuitarHeroPower) __instance.getPower(GuitarHeroPower.POWER_ID);
//            // 如果这张卡应该免费但没有标记
//            if (power.getRemainingFreeBocchiCards() > 0 && !c.freeToPlayOnce) {
//                c.freeToPlayOnce = true;
//            }
//        }
//    }
//
//    private static class Locator extends SpireInsertLocator {
//        @Override
//        public int[] Locate(javassist.CtBehavior ctMethodToPatch) throws Exception {
//            Matcher finalMatcher = new Matcher.MethodCallMatcher(
//                    AbstractCard.class, "use"
//            );
//            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//        }
//    }
//}