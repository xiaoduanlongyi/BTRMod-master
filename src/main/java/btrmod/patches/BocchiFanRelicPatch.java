package btrmod.patches;

import btrmod.powers.BocchiAfraidPower;
import btrmod.relics.BocchiFan;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;

@SpirePatch(
        clz = BocchiAfraidPower.class,
        method = "reducePower"
)
public class BocchiFanRelicPatch {

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(BocchiAfraidPower __instance, int reduceAmount) {
        // 只在实际减少时触发（reduceAmount > 0 且不会减到负数）
        if (reduceAmount > 0 && __instance.amount > 0) {
            // 检查玩家是否有下北泽天使吉他遗物
            if (AbstractDungeon.player.hasRelic(BocchiFan.ID)) {
                BocchiFan relic = (BocchiFan) AbstractDungeon.player.getRelic(BocchiFan.ID);
                relic.onBocchiAfraidReduced();
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // 在调用父类的 reducePower 之前插入
            Matcher finalMatcher = new Matcher.MethodCallMatcher(
                    btrmod.powers.BasePower.class, "reducePower"
            );
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}