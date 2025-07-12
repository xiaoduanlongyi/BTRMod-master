package btrmod.powers;

import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.relics.ExtrovertedBocchi;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.FANTASY;

public class BocchiAfraidPower extends BasePower {
    public static final String POWER_ID = makeID(BocchiAfraidPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    public static boolean guitarHeroTriggeredThisCombat = false;

    public BocchiAfraidPower(AbstractCreature owner, int amount) {
        // id, type=DEBUFF, turnBased=false, owner, source=owner, amt, initDesc=true, loadImage=true
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        priority = 1;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card != null && card.hasTag(BOCCHI) && !card.hasTag(FANTASY)) {
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
    public void reducePower(int reduceAmount) {
        if (owner.hasPower(WormBocchiPower.POWER_ID)) {
            return;
        }

        // 计算实际减少量
        int totalReduction = reduceAmount;

        // 如果有 NijikaSoloPower，额外减少1
        if (owner.hasPower(NijikaSoloPower.POWER_ID)) {
            totalReduction += 1;
        }

        // 确保不会减到负数
        totalReduction = Math.min(totalReduction, this.amount);

        super.reducePower(totalReduction);

        //减到10以下解除BocchiFantasy
        if (this.amount < 10){
            if (owner.hasPower(BocchiFantasyPower.POWER_ID)){
                addToBot(new RemoveSpecificPowerAction(owner, owner, BocchiFantasyPower.POWER_ID));
            }
        }
    }

    @Override
    public void onRemove() {
        //先判定有没有ExtrovertedBocchi，再判定本场战斗有没有通过清除BAP获得过吉他英雄
        if (!AbstractDungeon.player.hasRelic(ExtrovertedBocchi.ID)) {
            if (!guitarHeroTriggeredThisCombat) {
                guitarHeroTriggeredThisCombat = true;
                addToBot(new ApplyPowerAction(owner, owner, new GuitarHeroPower(owner, 1), 1));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {

        super.stackPower(stackAmount);

        if (this.amount >= 10) {
            if (!owner.hasPower(BocchiFantasyPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(owner, owner, new BocchiFantasyPower(owner)));
            }
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);

        float alpha = c.a;
        Color red = Color.RED.cpy();
        red.a = alpha;
        super.renderAmount(sb, x, y, red);
    }
}
