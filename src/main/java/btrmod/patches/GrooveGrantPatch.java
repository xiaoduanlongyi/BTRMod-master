//package btrmod.patches;
//
//import btrmod.cards.BaseCard;
//import btrmod.powers.SoloPowers.RyoSoloPower;
//import btrmod.util.CardTagEnum;
//import com.evacipated.cardcrawl.modthespire.lib.*;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//
//@SpirePatch(
//        clz = AbstractCard.class,
//        method = "applyPowers"
//)
//public class GrooveGrantPatch {
//    @SpirePostfixPatch
//    public static void Postfix(AbstractCard __instance) {
//        if (__instance.hasTag(CardTagEnum.GROOVE_GRANT) && __instance instanceof BaseCard) {
//            BaseCard card = (BaseCard) __instance;
//
//            if (card.hasCustomVar("GRV")) {
//                AbstractPlayer p = AbstractDungeon.player;
//
//                if (p != null && p.hasPower(RyoSoloPower.POWER_ID)) {
//                    // 翻倍 GRV 值
//                    int baseGRV = card.customVarBase("GRV");
//                    int currentGRV = card.customVar("GRV");
//
//                    // 只在还没被修改时翻倍
//                    //if (currentGRV == baseGRV) {
//                        card.setCustomVarValue("GRV", baseGRV * 2);
//                    }
//                } else {
//                    // 没有 RyoSoloPower 时重置
//                    card.resetCustomVar("GRV");
//                }
//            }
//        }
//    }