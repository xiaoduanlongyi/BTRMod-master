package btrmod.patches;

import btrmod.util.CardTagEnum;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "onMoveToDiscard"
)
public class GrooveGrantResetPatch {
    @SpirePostfixPatch
    public static void postfix(AbstractCard __card) {
        if (!__card.tags.contains(CardTagEnum.GROOVE_GRANT)) {
            return;
        }
        // restore to base value
        __card.magicNumber = __card.baseMagicNumber;
        __card.isMagicNumberModified = false;
        __card.initializeDescription();
    }
}