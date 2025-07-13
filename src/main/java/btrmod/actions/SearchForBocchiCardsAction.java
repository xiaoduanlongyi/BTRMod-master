package btrmod.actions;

import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class SearchForBocchiCardsAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;
    private boolean makeSelectedCardsFree; // 新增：是否让选中的卡牌本回合免费

    public SearchForBocchiCardsAction(int numberOfCards, boolean optional, boolean makeSelectedCardsFree) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        this.makeSelectedCardsFree = makeSelectedCardsFree;
    }

    public SearchForBocchiCardsAction(int numberOfCards, boolean optional) {
        this(numberOfCards, optional, false);
    }

    public SearchForBocchiCardsAction(int numberOfCards) {
        this(numberOfCards, true, false);
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                // 收集抽牌堆中所有带 BOCCHI 标签的卡牌
                ArrayList<AbstractCard> bocchiCards = getBocchiCardsFromDrawPile();

                if (bocchiCards.isEmpty()) {
                    // 没有 BOCCHI 卡牌，直接结束
                    this.isDone = true;
                    return;
                }

                if (bocchiCards.size() <= this.numberOfCards && !this.optional) {
                    // 如果BOCCHI卡牌数量不超过要选择的数量且不可选择跳过，直接全部拿到手牌
                    for (AbstractCard c : bocchiCards) {
                        if (this.player.hand.size() == 10) {
                            this.player.drawPile.moveToDiscardPile(c);
                            this.player.createHandIsFullDialog();
                        } else {
                            // 如果需要让卡牌免费，设置freeToPlayOnce
                            if (this.makeSelectedCardsFree) {
                                c.setCostForTurn(0);
                            }
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                        }
                    }
                    this.isDone = true;
                } else {
                    // 创建临时卡组用于选择
                    CardGroup temp = new CardGroup(CardGroupType.UNSPECIFIED);
                    for (AbstractCard c : bocchiCards) {
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);

                    // 限制选择数量不超过实际卡牌数量
                    int actualChoices = Math.min(this.numberOfCards, bocchiCards.size());

                    if (actualChoices == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, actualChoices, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, actualChoices, TEXT[0], false);
                        }
                    } else {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, actualChoices, true, TEXT[1] + actualChoices + TEXT[2]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, actualChoices, TEXT[1] + actualChoices + TEXT[2], false);
                        }
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            // 处理选择结果
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (this.player.hand.size() == 10) {
                        this.player.drawPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                    } else {
                        // 如果需要让卡牌免费，设置freeToPlayOnce
                        if (this.makeSelectedCardsFree) {
                            c.setCostForTurn(0);
                        }
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    /**
     * 从抽牌堆中获取所有 BOCCHI 标签的卡牌
     */
    private ArrayList<AbstractCard> getBocchiCardsFromDrawPile() {
        ArrayList<AbstractCard> bocchiCards = new ArrayList<>();
        for (AbstractCard card : player.drawPile.group) {
            if (card.hasTag(CardTagEnum.BOCCHI)) {
                bocchiCards.add(card);
            }
        }
        return bocchiCards;
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}