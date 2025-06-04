package btrmod.powers.SoloPowers;

import com.megacrit.cardcrawl.core.AbstractCreature;

import static btrmod.BTRMod.makeID;

public class NijikaSoloPower extends SoloPower {
    public static final String POWER_ID = makeID("NijikaSoloPower");

    public NijikaSoloPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner);
    }

    @Override
    public float getGrooveMultiplier() {
        return 1.5f; // every Stack of Groove now counts double
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}