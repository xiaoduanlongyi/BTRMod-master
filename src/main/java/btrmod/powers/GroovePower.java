package btrmod.powers;

import btrmod.interfaces.GrooveMultiplierCard;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.powers.SoloPowers.SoloPower;
import btrmod.util.CardTagEnum;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static btrmod.BTRMod.makeID;

public class GroovePower extends BasePower {
    public static final String POWER_ID = makeID(GroovePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GroovePower(AbstractCreature owner, int amount) {
        // id, type=DEBUFF, turnBased=false, owner, source=owner, amt, initDesc=true, loadImage=true
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card != null && card.hasTag(CardTagEnum.GROOVE_USE)) {
            float mult = 1f;
            if (owner.hasPower(BocchiSoloPower.POWER_ID)) {
                mult = ((SoloPower)owner.getPower(BocchiSoloPower.POWER_ID)).getGrooveMultiplier();
            }

            float cardMultiplier = getCardGrooveMultiplier(card);

            float grooveBonus = MathUtils.floor(amount * mult * cardMultiplier);
            return damage + grooveBonus;
        }
        return damage;
    }

    /**
     * 获取卡牌的 Groove 倍数
     * @param card 要检查的卡牌
     * @return Groove 倍数，默认为 1.0
     */
    private float getCardGrooveMultiplier(AbstractCard card) {
        // 方法1：使用接口
        if (card instanceof GrooveMultiplierCard) {
            return ((GrooveMultiplierCard) card).getGrooveMultiplier();
        }
        return 1.0f;
    }

    @Override
    public void stackPower(int stackAmount) {

        super.stackPower(stackAmount);

        //这些逻辑放在GrooveGrantPatch中实现了
        if (owner.hasPower(RyoSoloPower.POWER_ID)) {
            super.stackPower(stackAmount);
        }

        updateDescription();
    }

    /** helper for cards that “exhaust all grooves” */
    public int consumeAllGroove() {
        int stacks = this.amount;
        addToBot(
                new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        return stacks;
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}
