package btrmod.powers;

import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static btrmod.BTRMod.makeID;

public class BocchiFantasyPower extends BasePower {
    public static final String POWER_ID = makeID(BocchiFantasyPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    // 缓存带有 FANTASY 标签的卡牌列表
    private static ArrayList<String> fantasyCardPool = null;

    public BocchiFantasyPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURN_BASED, owner, -1);

        // 初始化卡牌池（只在第一次创建时执行）
        if (fantasyCardPool == null) {
            initializeFantasyCardPool();
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            // 每回合添加1张卡牌
            AbstractCard cardToAdd = getRandomFantasyCard();
            if (cardToAdd != null) {
                addToBot(new MakeTempCardInHandAction(cardToAdd, 1));
            }
        }
    }

    /**
     * 初始化带有 FANTASY 标签的卡牌池
     */
    private void initializeFantasyCardPool() {
        fantasyCardPool = new ArrayList<>();

        // 遍历所有卡牌，找出带有 FANTASY 标签的卡牌
        for (AbstractCard card : CardLibrary.getAllCards()) {
            if (card.hasTag(CardTagEnum.FANTASY)) {
                fantasyCardPool.add(card.cardID);
            }
        }
    }

    /**
     * 从卡牌池中随机获取一张 FANTASY 卡牌
     */
    private AbstractCard getRandomFantasyCard() {
        if (fantasyCardPool == null || fantasyCardPool.isEmpty()) {
            return null;
        }

        // 随机选择一张卡牌ID
        String randomCardID = fantasyCardPool.get(
                AbstractDungeon.cardRandomRng.random(fantasyCardPool.size() - 1)
        );

        // 根据ID创建卡牌实例
        AbstractCard card = CardLibrary.getCard(randomCardID);
        if (card != null) {
            return card.makeCopy();
        }

        return null;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}