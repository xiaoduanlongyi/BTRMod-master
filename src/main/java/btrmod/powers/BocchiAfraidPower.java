package btrmod.powers;

import btrmod.powers.SoloPowers.BocchiSoloPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiAfraidPower extends BasePower {
    public static final String POWER_ID = makeID(BocchiAfraidPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    public static boolean guitarHeroTriggeredThisCombat = false;

    public BocchiAfraidPower(AbstractCreature owner, int amount) {
        // id, type=DEBUFF, turnBased=false, owner, source=owner, amt, initDesc=true, loadImage=true
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card != null && card.hasTag(BOCCHI)) {
            float reduction = Math.min(1f, 0.1f * this.amount); // 10% per stack, max 100%
            return damage * (1f - reduction);
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        // DESCRIPTIONS[0] = "Bocchi attacks deal #b-"
        // DESCRIPTIONS[1] = "% damage."
        description = DESCRIPTIONS[0]
                + Math.min(100, amount * 10)
                + DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (owner.hasPower(BocchiSoloPower.POWER_ID)) {
            super.stackPower(stackAmount);
        }
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        // 先做一次正常扣减
        super.reducePower(reduceAmount);
        // 如果玩家身上有 SoloPower，就再多扣一次
        if (owner.hasPower(BocchiSoloPower.POWER_ID)) {
            super.reducePower(reduceAmount);
        }
        updateDescription();
    }

    @Override
    public void onRemove() {
        if (!guitarHeroTriggeredThisCombat) {
            guitarHeroTriggeredThisCombat = true;
            addToBot(new ApplyPowerAction(owner, owner, new GuitarHeroPower(owner, 1), 1));
        }
    }
}
