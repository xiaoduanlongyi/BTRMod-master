package btrmod.actions;

import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

/**
 * 自定义 Action：将抽牌堆中所有带有 BOCCHI 标签的卡牌加入手牌。
 * 如果手牌已满（10张），则停止添加。
 */
public class DrawAllBocchiCardsAction extends AbstractGameAction {
    private static final int MAX_HAND_SIZE = 10;
    private final AbstractPlayer player;

    /**
     * 构造函数
     * 将抽牌堆中所有 BOCCHI 卡牌移到手牌
     */
    public DrawAllBocchiCardsAction() {
        this.player = AbstractDungeon.player;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 收集所有带 BOCCHI 标签的卡牌
            ArrayList<AbstractCard> bocchiCards = new ArrayList<>();
            for (AbstractCard card : player.drawPile.group) {
                if (card.hasTag(CardTagEnum.BOCCHI)) {
                    bocchiCards.add(card);
                }
            }

            // 将找到的卡牌移到手牌
            for (AbstractCard card : bocchiCards) {
                // 检查手牌上限
                if (player.hand.size() >= MAX_HAND_SIZE) {
                    break;
                }

                // 从抽牌堆移除并加入手牌
                player.drawPile.moveToHand(card);

                // 应用卡牌效果和更新显示
                card.applyPowers();
            }

            // 如果抽了很多牌，刷新手牌布局
            if (!bocchiCards.isEmpty()) {
                player.hand.refreshHandLayout();
                player.hand.glowCheck();
            }
        }

        tickDuration();
    }
}