package btrmod.actions;

import btrmod.powers.DistortionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FleeGuitaristAction extends AbstractGameAction {
    int energyBack;

    public FleeGuitaristAction(AbstractCreature target, int block, int energy) {
        this.target = target;
        this.amount = block;
        this.energyBack = energy;
    }

    public void update() {
        addToTop(new GainBlockAction(target, amount));
        if (AbstractDungeon.player.hasPower(DistortionPower.POWER_ID)) {
            addToTop(new GainEnergyAction(energyBack));
        }

        this.isDone = true;
    }
}