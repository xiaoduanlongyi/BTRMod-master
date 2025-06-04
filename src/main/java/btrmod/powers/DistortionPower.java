package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.*;

import static btrmod.BTRMod.makeID;

public class DistortionPower extends BasePower {
    public static final String POWER_ID = makeID(DistortionPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    // 存储每张卡片的原始费用
    private Map<AbstractCard, Integer> originalCosts = new HashMap<>();
    // 存储已经被随机化的卡片，避免重复随机
    private Set<AbstractCard> randomizedCards = new HashSet<>();

    public DistortionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);

        // 如果是第一次施加，随机化当前手牌
        if (!owner.hasPower(POWER_ID)) {
            randomizeHandCosts();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // 叠加层数时不需要重新随机化已有的牌
    }

    private void randomizeHandCosts() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            randomizeCardCost(card);
        }
    }

    private void randomizeCardCost(AbstractCard card) {
        // 跳过已经随机化的卡片、X费用卡、免费卡
        if (randomizedCards.contains(card) || card.cost < 0 || card.freeToPlayOnce) {
            return;
        }

        // 保存原始费用（只在第一次随机化时保存）
        if (!originalCosts.containsKey(card)) {
            originalCosts.put(card, card.cost);
        }

        // 设置新的随机费用
        int newCost = AbstractDungeon.cardRandomRng.random(3);
        if (card.cost != newCost) {
            card.cost = newCost;
            card.costForTurn = newCost;
            card.isCostModified = true;
        }

        // 标记为已随机化
        randomizedCards.add(card);
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // 新抽的牌也要随机化费用
        randomizeCardCost(card);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            // 减少一层
            if (this.amount <= 1) {
                // 最后一层，恢复所有费用并移除 Power
                restoreAllCosts();
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            } else {
                // 还有层数，只减少层数
                addToBot(new ReducePowerAction(owner, owner, this, 1));
            }
        }
    }

    @Override
    public void onRemove() {
        // Power 被移除时恢复所有费用
        restoreAllCosts();
    }

    private void restoreAllCosts() {
        // 恢复所有位置的卡牌费用
        restoreCardsInGroup(AbstractDungeon.player.hand.group);
        restoreCardsInGroup(AbstractDungeon.player.drawPile.group);
        restoreCardsInGroup(AbstractDungeon.player.discardPile.group);
        restoreCardsInGroup(AbstractDungeon.player.exhaustPile.group);

        // 清空记录
        originalCosts.clear();
        randomizedCards.clear();
    }

    private void restoreCardsInGroup(ArrayList<AbstractCard> cardGroup) {
        for (AbstractCard card : cardGroup) {
            if (originalCosts.containsKey(card)) {
                card.cost = originalCosts.get(card);
                card.costForTurn = card.cost;
                card.isCostModified = false;
            }
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}