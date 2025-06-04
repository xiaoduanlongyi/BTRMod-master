package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static btrmod.BTRMod.makeID;

public class RyoThumbUpPower extends BasePower {
    public static final String POWER_ID = makeID(RyoThumbUpPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RyoThumbUpPower(AbstractCreature owner, int amount) {
        // id, type=DEBUFF, turnBased=false, owner, source=owner, amt, initDesc=true, loadImage=true
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurnPostDraw() {
        this.flash();
        addToBot(new ApplyPowerAction(owner, owner, new GroovePower(owner, amount), amount));
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
