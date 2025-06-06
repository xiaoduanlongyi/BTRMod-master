package btrmod.actions;

import btrmod.powers.DistortionPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FleeGuitaristAction extends AbstractGameAction {
    int additionalAmt;

    public FleeGuitaristAction(AbstractCreature target, int block, int additional) {
        this.target = target;
        this.amount = block;
        this.additionalAmt = additional;
    }

    public void update() {
        if (AbstractDungeon.player.hasPower(DistortionPower.POWER_ID)) {
            addToTop(new GainBlockAction(target, amount + additionalAmt));
        } else {
            addToTop(new GainBlockAction(target, amount));
        }

        this.isDone = true;
    }
}