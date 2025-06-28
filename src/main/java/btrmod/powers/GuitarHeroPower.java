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
        // 当抽到Bocchi卡时，检查是否应该免费
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
            // 移除所有Bocchi卡的免费状态
            resetHandCardsCost();
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

    @Override
    public void onInitialApplication() {
        // 初次获得时立即更新手牌
        updateHandCardsCost();
    }

    /**
     * 更新手牌中所有Bocchi卡的费用
     */
    private void updateHandCardsCost() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            // 首先计算还能免费多少张
            int remainingFree = this.amount - bocchiCardsPlayedThisTurn;
            int markedFree = 0;

            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(BOCCHI)) {
                    if (markedFree < remainingFree) {
                        // 这张卡应该免费
                        if (!card.freeToPlayOnce) {
                            card.freeToPlayOnce = true;
                        }
                        markedFree++;
                    } else {
                        // 这张卡不应该免费
                        if (card.freeToPlayOnce) {
                            card.freeToPlayOnce = false;
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新单张卡的费用
     */
    private void updateCardCost(AbstractCard card) {
        if (!card.hasTag(BOCCHI)) return;

        // 计算还能免费多少张
        int remainingFree = this.amount - bocchiCardsPlayedThisTurn;

        // 计算这张卡之前有多少张Bocchi卡已经标记为免费
        int alreadyMarkedFree = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c != card && c.hasTag(BOCCHI) && c.freeToPlayOnce) {
                    alreadyMarkedFree++;
                }
            }
        }

        // 如果还有免费名额
        if (alreadyMarkedFree < remainingFree) {
            card.freeToPlayOnce = true;
        } else {
            card.freeToPlayOnce = false;
        }
    }

    /**
     * 重置手牌中所有Bocchi卡的免费状态
     */
    private void resetHandCardsCost() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(BOCCHI) && card.freeToPlayOnce) {
                    card.freeToPlayOnce = false;
                }
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