package btrmod.actions;

import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class ChooseBocchiCardAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ChooseOneScreen").TEXT;

    public ChooseBocchiCardAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 生成3张随机的Bocchi卡牌供选择
            ArrayList<AbstractCard> choices = generateBocchiCardChoices();

            if (choices.isEmpty()) {
                // 如果没有Bocchi卡牌，直接结束
                this.isDone = true;
                return;
            }

            // 打开选择界面
            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, TEXT[0], false);
            this.tickDuration();

        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    // 获取选择的卡牌
                    AbstractCard selectedCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    // 设置卡牌位置
                    selectedCard.current_x = -1000.0F * Settings.xScale;

                    // 添加到手牌或弃牌堆
                    if (AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(
                                selectedCard,
                                Settings.WIDTH / 2.0F,
                                Settings.HEIGHT / 2.0F
                        ));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(
                                selectedCard,
                                Settings.WIDTH / 2.0F,
                                Settings.HEIGHT / 2.0F
                        ));
                    }

                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }
                this.retrieveCard = true;
            }

            this.tickDuration();
        }
    }

    private ArrayList<AbstractCard> generateBocchiCardChoices() {
        ArrayList<AbstractCard> choices = new ArrayList<>();

        // 随机选择3张不同的Bocchi卡牌
        for (int i = 0; i < 3 ; i++) {
            AbstractCard temp = returnTrulyRandomBocchiCardInCombat(cardRandomRng);
            choices.add(temp);
        }

        return choices;
    }

    public static AbstractCard returnTrulyRandomBocchiCardInCombat(Random rng) {
        ArrayList<AbstractCard> bocchiCards = new ArrayList();

        // 收集所有带有BOCCHI标签的卡牌
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.hasTag(CardTagEnum.BOCCHI) && !c.hasTag(CardTagEnum.FANTASY) && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                bocchiCards.add(c);
            }
        }

        return bocchiCards.get(rng.random(bocchiCards.size() - 1));
    }
}