package btrmod.powers.SoloPowers;

import btrmod.powers.BasePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.Arrays;
import java.util.List;

import static btrmod.BTRMod.makeID;

public abstract class SoloPower extends BasePower {
    // list every solo‐power ID here
    private static final List<String> ALL_SOLO_IDS = Arrays.asList(
            makeID("BocchiSoloPower"),
            makeID("RyoSoloPower"),
            makeID("NijikaSoloPower"),
            makeID("KitaSoloPower")
    );

    public SoloPower(String id, PowerType type, boolean turnBased, AbstractCreature owner) {
        super(id, type, turnBased, owner, 1); // always start at 1
    }

    @Override
    public void onInitialApplication() {
        // remove any other solo power that’s currently on the owner
        for (String otherId : ALL_SOLO_IDS) {
            if (!otherId.equals(this.ID) && owner.hasPower(otherId)) {
                addToBot(new RemoveSpecificPowerAction(owner, owner, otherId));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        // never go above 1
        this.amount = 1;
        updateDescription();
    }

    /**
     * How many times each Groove stack should count?
     */
    public float getGrooveMultiplier() {
        return 1f; // default = no change
    }
}