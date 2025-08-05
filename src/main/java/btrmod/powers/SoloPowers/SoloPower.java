package btrmod.powers.SoloPowers;

import btrmod.powers.BasePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

import java.util.Arrays;
import java.util.List;

import static btrmod.BTRMod.makeID;

public abstract class SoloPower extends BasePower implements OnReceivePowerPower{
    // list every solo‐power ID here
    private static final List<String> ALL_SOLO_IDS = Arrays.asList(
            makeID("BocchiSoloPower"),
            makeID("RyoSoloPower"),
            makeID("NijikaSoloPower"),
            makeID("KitaSoloPower")
    );

    public SoloPower(String id, PowerType type, boolean turnBased, AbstractCreature owner) {
        super(id, type, turnBased, owner, 1); // always start at 1
        this.priority = 0;
    }

    @Override
    public void onInitialApplication() {
        if (owner.isPlayer) {
            AbstractDungeon.effectList.add(new SpotlightPlayerEffect());
        }

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

    @Override
    public boolean onReceivePower(AbstractPower powerToApply, AbstractCreature target, AbstractCreature source) {
        // 如果目标是自己且即将获得的Power是Solo Power
        if (target == this.owner && powerToApply instanceof SoloPower) {
            // 如果即将获得的是相同的Solo Power，阻止应用
            if (powerToApply.ID.equals(this.ID)) {
                // 返回false会取消这个Power的应用
                return false;
            }
        }
        // 其他情况正常应用
        return true;
    }


    /**
     * How many times each Groove stack should count?
     */
    public float getGrooveMultiplier() {
        return 1f; // default = no change
    }
}