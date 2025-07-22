package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.REDUCE_BAP;

public class WhatIsWrongWithPower extends BasePower {
    public static final String POWER_ID = makeID(WhatIsWrongWithPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public WhatIsWrongWithPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {

        super.onAfterCardPlayed(usedCard);

        if (usedCard.hasTag(REDUCE_BAP)) {
            this.flash();
            addToBot(new GainBlockAction(owner, amount));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
