package btrmod.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarHeroPower extends BasePower {
    public static final String POWER_ID = makeID(GuitarHeroPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GuitarHeroPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    private int bocchiCardsPlayedThisTurn = 0;

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        this.bocchiCardsPlayedThisTurn = 0;
        updateHandCardsCost();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(BOCCHI)) {
            updateCardCost(card);
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // 当使用Bocchi卡时
        if (card.hasTag(BOCCHI)) {
            // 打牌前先增加计数
            bocchiCardsPlayedThisTurn++;
            // 更新手牌中其他Bocchi卡的费用
            updateHandCardsCost();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 重置计数器
        if (isPlayer) {
            bocchiCardsPlayedThisTurn = 0;
            // 恢复所有Bocchi卡的费用
            updateHandCardsCost();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // 当层数改变时更新手牌费用
        updateHandCardsCost();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        // 当层数改变时更新手牌费用
        updateHandCardsCost();
    }

    /**
     * 更新手牌中所有Bocchi卡的费用
     */
    private void updateHandCardsCost() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(BOCCHI)) {
                    updateCardCost(card);
                }
            }
        }
    }

    /**
     * 更新单张卡的费用
     */
    private void updateCardCost(AbstractCard card) {
        if (!card.hasTag(BOCCHI)) return;

        // 如果已经打出的Bocchi卡数量小于层数，这张卡应该免费
        if (bocchiCardsPlayedThisTurn < this.amount) {
            if (card.cost >= 0 && card.costForTurn > 0) {
                card.setCostForTurn(0);
            }
        } else {
            // 否则恢复原始费用
            if (card.cost >= 0) {
                card.resetAttributes();
                card.applyPowers();
            }
        }
    }

    /**
     * 提供一个方法供外部查询当前还有几张免费Bocchi卡
     */
    public int getRemainingFreeBocchiCards() {
        return Math.max(0, this.amount - bocchiCardsPlayedThisTurn);
    }
}