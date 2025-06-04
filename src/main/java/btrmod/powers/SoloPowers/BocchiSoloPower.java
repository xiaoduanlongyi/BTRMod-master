package btrmod.powers.SoloPowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static btrmod.BTRMod.makeID;

public class BocchiSoloPower extends SoloPower {
    public static final String POWER_ID = makeID("BocchiSoloPower");

    public BocchiSoloPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner);
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}