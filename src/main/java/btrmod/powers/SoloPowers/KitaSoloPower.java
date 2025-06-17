package btrmod.powers.SoloPowers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static btrmod.BTRMod.makeID;

public class KitaSoloPower extends SoloPower {
    public static final String POWER_ID = makeID("KitaSoloPower");

    public KitaSoloPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner);
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new DrawCardAction(2));
        this.flash();
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}