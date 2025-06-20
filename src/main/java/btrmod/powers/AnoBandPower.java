package btrmod.powers;

import btrmod.powers.SoloPowers.SoloPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;

public class AnoBandPower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(AnoBandPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public AnoBandPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof SoloPower && target == this.owner) {
            this.flash();
            addToBot(new ApplyPowerAction(owner, owner, new GroovePower(owner, amount), amount));
        }
        return true;
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
