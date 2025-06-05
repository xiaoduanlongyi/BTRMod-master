package btrmod.subscribers;

import btrmod.powers.ComboCounterPower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.CardTagEnum;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 * 监听玩家打出的卡牌，当连续打出3张相同角色的卡牌时，自动获得对应的Solo Power
 * 如果通过卡牌直接获得Solo Power，会打断当前的连击计数
 */
public class ComboSoloSubscriber implements OnCardUseSubscriber, OnStartBattleSubscriber {
    private static final int COMBO_REQUIREMENT = 3;

    // 存储最近打出的角色标签
    private LinkedList<AbstractCard.CardTags> recentCharacterTags = new LinkedList<>();

    // 标签到Solo Power的映射
    private static final Map<AbstractCard.CardTags, Class<? extends AbstractPower>> TAG_TO_SOLO_CLASS = new HashMap<>();

    static {
        TAG_TO_SOLO_CLASS.put(CardTagEnum.BOCCHI, BocchiSoloPower.class);
        TAG_TO_SOLO_CLASS.put(CardTagEnum.KITA, KitaSoloPower.class);
        TAG_TO_SOLO_CLASS.put(CardTagEnum.NIJIKA, NijikaSoloPower.class);
        TAG_TO_SOLO_CLASS.put(CardTagEnum.RYO, RyoSoloPower.class);
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;

        // 获取这张卡的角色标签
        AbstractCard.CardTags characterTag = getCharacterTag(card);

        if (characterTag != null) {
            // 添加到最近的标签列表
            recentCharacterTags.add(characterTag);

            // 只保留最近的COMBO_REQUIREMENT个标签
            while (recentCharacterTags.size() > COMBO_REQUIREMENT) {
                recentCharacterTags.removeFirst();
            }

            int currentCount = getCurrentComboCount(characterTag);

            if (currentCount < COMBO_REQUIREMENT) {
                // 3a. 若未达成三连击，则更新或应用 ComboCounterPower，显示“X/3”
                applyOrUpdateComboCounter(p, characterTag, currentCount);
            } else {
                // 3b. 达成三连击：触发 SoloPower
                triggerSolo(characterTag);
                // 发完 Solo 之后，清空连击记录
                recentCharacterTags.clear();
                // 同时移除 ComboCounterPower
                removeComboCounter(p);
            }
        }
    }

    /**
     * 获取卡牌的角色标签
     */
    private AbstractCard.CardTags getCharacterTag(AbstractCard card) {
        if (card.hasTag(CardTagEnum.BOCCHI)) return CardTagEnum.BOCCHI;
        if (card.hasTag(CardTagEnum.KITA)) return CardTagEnum.KITA;
        if (card.hasTag(CardTagEnum.NIJIKA)) return CardTagEnum.NIJIKA;
        if (card.hasTag(CardTagEnum.RYO)) return CardTagEnum.RYO;
        return null;
    }

    /**
     * 检查是否达成连击
     */
    private boolean checkCombo(AbstractCard.CardTags targetTag) {
        if (recentCharacterTags.size() < COMBO_REQUIREMENT) {
            return false;
        }

        // 检查最近的COMBO_REQUIREMENT张牌是否都是同一个角色
        for (AbstractCard.CardTags tag : recentCharacterTags) {
            if (tag != targetTag) {
                return false;
            }
        }

        return true;
    }

    /**
     * 触发Solo Power
     */
    private void triggerSolo(AbstractCard.CardTags characterTag) {
        AbstractPlayer p = AbstractDungeon.player;
        Class<? extends AbstractPower> soloPowerClass = TAG_TO_SOLO_CLASS.get(characterTag);

        if (soloPowerClass != null) {
            try {
                // 不需要检查是否已有，因为SoloPower会自动处理移除其他Solo
                AbstractPower power = soloPowerClass.getConstructor(AbstractCreature.class)
                        .newInstance(p);

                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(p, p, power, 1)
                );

                // 可以添加特效或音效
                p.powers.forEach(pow -> pow.flash());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果玩家身上还没 ComboCounterPower，就 new 一个并 Apply；
     * 如果已经有了，就直接更新 trackedTag 和 amount。
     *
     * @param p      玩家
     * @param tag    当前正在叠连击的角色 Tag
     * @param count  连续打出的张数 (1 或 2)
     */
    private void applyOrUpdateComboCounter(AbstractPlayer p, AbstractCard.CardTags tag, int count) {
        if (!p.hasPower(ComboCounterPower.POWER_ID)) {
            // 1) 玩家身上没有时，创建新的 ComboCounterPower，amount = count
            ComboCounterPower newPower = new ComboCounterPower(p, tag, count);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, p, newPower, count)
            );
        } else {
            // 2) 已有时，直接更新 trackedTag 和 amount
            ComboCounterPower existing = (ComboCounterPower) p.getPower(ComboCounterPower.POWER_ID);
            existing.setCounter(tag, count);
        }
    }


    /**
     * 移除玩家身上的 ComboCounterPower（如果存在）。
     */
    private void removeComboCounter(AbstractPlayer p) {
        if (p.hasPower(ComboCounterPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(p, p, ComboCounterPower.POWER_ID)
            );
        }
    }

    /**
     * 在战斗开始时重置
     */
    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        recentCharacterTags.clear();
    }

    /**
     * 获取当前连击进度（用于UI显示）
     */
    public int getCurrentComboCount(AbstractCard.CardTags tag) {
        if (recentCharacterTags.isEmpty()) return 0;

        int count = 0;
        // 从后往前数，遇到不同的就停止
        for (int i = recentCharacterTags.size() - 1; i >= 0; i--) {
            if (recentCharacterTags.get(i) == tag) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /*** 供外部直接清空连击记录 ***/
    public void clearRecentTags() {
        recentCharacterTags.clear();
    }
}