package btrmod.powers.SoloPowers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static btrmod.BTRMod.makeID;

public class NijikaSoloPower extends SoloPower {
    public static final String POWER_ID = makeID("NijikaSoloPower");

    public NijikaSoloPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner);
    }

    @Override
    public int getGrooveMultiplier() {
        return 2; // every Stack of Groove now counts double
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}