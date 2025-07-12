package btrmod.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarHeroPower_DEPRECATE extends BasePower {
    public static final String POWER_ID = makeID(GuitarHeroPower_DEPRECATE.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GuitarHeroPower_DEPRECATE(AbstractCreature owner, int amount) {
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
        if (AbstractDungeon.player != null){
            AbstractDungeon.player.gainEnergy(amount);
        }
        // 在新回合开始时，更新所有手牌的费用
        updateAllBocchiCardsCost();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // 当抽到Bocchi卡时，检查是否应该免费
        if (card.hasTag(BOCCHI)) {
            updateSingleCardCost(card);
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // 当使用Bocchi卡时
        if (card.hasTag(BOCCHI) && card.freeToPlayOnce) {
            // 打牌前先增加计数
            bocchiCardsPlayedThisTurn++;
            // 更新所有Bocchi卡的费用状态
            updateAllBocchiCardsCost();
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 重置计数器
        bocchiCardsPlayedThisTurn = 0;
        // 移除所有Bocchi卡的免费状态
        resetAllBocchiCardsCost();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // 当层数改变时更新手牌费用
        updateAllBocchiCardsCost();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        // 当层数改变时更新手牌费用
        updateAllBocchiCardsCost();
    }

    @Override
    public void onInitialApplication() {
        // 初次获得时立即更新手牌
        updateAllBocchiCardsCost();
    }

    /**
     * 更新所有Bocchi卡的费用状态
     */
    private void updateAllBocchiCardsCost() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            int remainingFree = getRemainingFreeBocchiCards();

            // 如果还有免费次数，所有Bocchi卡都应该显示为免费
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(BOCCHI)) {
                    if (remainingFree > -1) {
                        // 还有免费次数，设为免费
                        if (!card.freeToPlayOnce) {
                            card.freeToPlayOnce = true;
                        }
                    } else {
                        // 没有免费次数了，恢复正常费用
                        if (card.freeToPlayOnce) {
                            card.freeToPlayOnce = false;
                            card.setCostForTurn(card.cost);
                            card.isCostModified = false;
                        }
                    }
                }
            }

            // 触发所有卡牌重新计算显示
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    /**
     * 更新单张卡的费用（新抽的卡）
     */
    private void updateSingleCardCost(AbstractCard card) {
        if (!card.hasTag(BOCCHI)) return;

        // 计算还能免费多少张
        int remainingFree = getRemainingFreeBocchiCards();

        // 如果还有免费次数
        if (remainingFree > -1) {
            card.freeToPlayOnce = true;
        } else {
            card.freeToPlayOnce = false;
            // 不需要设置costForTurn，让卡牌自己的applyPowers处理
        }
    }

    /**
     * 重置手牌中所有Bocchi卡的免费状态
     */
    private void resetAllBocchiCardsCost() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(BOCCHI) && card.freeToPlayOnce) {
                    card.freeToPlayOnce = false;
                    card.setCostForTurn(card.cost);
                    card.isCostModified = false;
                }
            }
        }
    }

    /**
     * 获取当前还有几张免费Bocchi卡
     */
    public int getRemainingFreeBocchiCards() {
        //return Math.max(0, this.amount - bocchiCardsPlayedThisTurn);
        return this.amount - bocchiCardsPlayedThisTurn;
    }
}