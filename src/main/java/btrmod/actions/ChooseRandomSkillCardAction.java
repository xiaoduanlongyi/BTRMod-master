package btrmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;

public class ChooseRandomSkillCardAction extends AbstractGameAction {
    private boolean retrieveCard = false;
    private boolean makeCardFree;
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("ChooseOneScreen").TEXT;

    public ChooseRandomSkillCardAction(boolean makeCardFree) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.makeCardFree = makeCardFree;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // 生成3张随机的技能卡牌供选择
            ArrayList<AbstractCard> choices = generateRandomSkillCards();

            if (choices.isEmpty()) {
                // 如果没有技能卡牌，直接结束
                this.isDone = true;
                return;
            }

            // 打开选择界面，第三个参数为true表示可以跳过
            AbstractDungeon.cardRewardScreen.customCombatOpen(choices, TEXT[0], true);
            this.tickDuration();

        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    // 获取选择的卡牌
                    AbstractCard selectedCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    // 设置卡牌位置
                    selectedCard.current_x = -1000.0F * Settings.xScale;

                    // 如果需要让卡牌免费，设置为本回合免费
                    if (this.makeCardFree) {
                        selectedCard.setCostForTurn(0);
                    }

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

    private ArrayList<AbstractCard> generateRandomSkillCards() {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        ArrayList<AbstractCard> skillCardPool = new ArrayList<>();

        // 收集所有 KessokuBandChar 角色的技能卡牌
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (c.type == AbstractCard.CardType.SKILL
                    && c.color == btrmod.character.KessokuBandChar.Meta.CARD_COLOR
                    && !c.hasTag(AbstractCard.CardTags.HEALING)) {
                skillCardPool.add(c);
            }
        }

        // 如果技能卡牌池为空，返回空列表
        if (skillCardPool.isEmpty()) {
            return choices;
        }

        // 随机选择3张不同的技能卡牌
        ArrayList<AbstractCard> tempPool = new ArrayList<>(skillCardPool);
        for (int i = 0; i < Math.min(3, tempPool.size()); i++) {
            int randomIndex = AbstractDungeon.cardRandomRng.random(tempPool.size() - 1);
            AbstractCard randomCard = tempPool.get(randomIndex).makeCopy();
            choices.add(randomCard);
            tempPool.remove(randomIndex);
        }

        return choices;
    }
}