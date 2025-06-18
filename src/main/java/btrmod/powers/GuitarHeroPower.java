package btrmod.powers;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarHeroPower extends BasePower{
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
        this.bocchiCardsPlayedThisTurn= 0;
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(BOCCHI) && bocchiCardsPlayedThisTurn + 1 == this.amount) {
            if (card.cost > 0 && !card.freeToPlayOnce) {
                card.setCostForTurn(0);
            }
        }
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        // 当打出Bocchi卡时计数
        if (usedCard.hasTag(BOCCHI)) {
            bocchiCardsPlayedThisTurn++;

            // 如果已经打出了amount张Bocchi卡，让手牌中剩余的Bocchi卡费用归位
            if (bocchiCardsPlayedThisTurn >= this.amount) {
                if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
                    for (AbstractCard card : AbstractDungeon.player.hand.group) {
                        if (card.hasTag(BOCCHI)) {
                            card.applyPowers();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        // 当使用第n张Bocchi卡时，确保不消耗费用
        if (card.hasTag(BOCCHI) && bocchiCardsPlayedThisTurn + 1 == this.amount) {
            if (card.cost > 0 && !card.freeToPlayOnce) {
                this.flash();
                // 确保这张卡本回合免费
                card.freeToPlayOnce = true;
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 重置计数器，恢复卡牌费用
        if (isPlayer) {
            bocchiCardsPlayedThisTurn = 0;
            // 重置手牌中所有Bocchi卡的费用
            if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
                for (AbstractCard card : AbstractDungeon.player.hand.group) {
                    if (card.hasTag(BOCCHI)) {
                        card.applyPowers();
                    }
                }
            }
        }
    }
}
