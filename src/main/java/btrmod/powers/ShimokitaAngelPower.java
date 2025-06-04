package btrmod.powers;

import btrmod.powers.SoloPowers.RyoSoloPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static btrmod.BTRMod.makeID;

public class ShimokitaAngelPower extends BasePower {
    public static final String POWER_ID = makeID(ShimokitaAngelPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ShimokitaAngelPower(AbstractCreature owner, int amount) {
        // id, type=DEBUFF, turnBased=false, owner, source=owner, amt, initDesc=true, loadImage=true
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurnPostDraw() {
        this.flash();

        if (owner.hasPower(BocchiAfraidPower.POWER_ID)) {
            this.addToBot(new ReducePowerAction(owner, owner, BocchiAfraidPower.POWER_ID, amount));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
