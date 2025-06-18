package btrmod.powers;

import btrmod.powers.SoloPowers.SoloPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;

public class NeverForgetPower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(NeverForgetPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public NeverForgetPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private int soloSwitchedThisTurn = 0;

    @Override
    public void atStartOfTurn() {
        this.soloSwitchedThisTurn = 0;
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power instanceof SoloPower && target == this.owner) {
            if (soloSwitchedThisTurn < amount) {
                this.flash();
                soloSwitchedThisTurn ++;
                addToBot(new GainEnergyAction(amount));
            }
        }
        return true;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 重置计数器
        if (isPlayer) {
            soloSwitchedThisTurn = 0;
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
