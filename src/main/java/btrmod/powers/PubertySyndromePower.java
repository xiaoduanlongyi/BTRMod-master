package btrmod.powers;

import btrmod.powers.SoloPowers.SoloPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;

public class PubertySyndromePower extends BasePower implements OnReceivePowerPower {
    public static final String POWER_ID = makeID(PubertySyndromePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public PubertySyndromePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        // 检查是否是 SoloPower 且目标是拥有者
        if (power instanceof SoloPower && target == this.owner) {
            // 任何时候获得 SoloPower 都触发格挡
            // 包括从无到有，或从一个切换到另一个
            this.flash();
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        return true;
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
