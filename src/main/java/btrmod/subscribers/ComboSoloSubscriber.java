package btrmod.subscribers;

import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.CardTagEnum;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
public class ComboSoloSubscriber implements OnCardUseSubscriber, OnPowersModifiedSubscriber, OnStartBattleSubscriber {
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

            // 检查是否达成连击
            if (checkCombo(characterTag)) {
                triggerSolo(characterTag);
                // 触发后清空连击记录，避免一张牌触发多次
                recentCharacterTags.clear();
            }
        }
    }

    @Override
    public void receivePowersModified() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;

        // 检查是否有新的Solo Power被添加（非连击触发）
        // 通过检查连击是否已满来判断
        if (recentCharacterTags.size() < COMBO_REQUIREMENT) {
            // 如果连击未满但有了Solo Power，说明是通过卡牌直接获得的
            boolean hasSoloPower = false;
            for (Class<? extends AbstractPower> soloPowerClass : TAG_TO_SOLO_CLASS.values()) {
                if (hasPowerOfClass(p, soloPowerClass)) {
                    hasSoloPower = true;
                    break;
                }
            }

            if (hasSoloPower) {
                // 清空连击记录
                recentCharacterTags.clear();
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

        if (soloPowerClass != null && !hasPowerOfClass(p, soloPowerClass)) {
            try {
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
     * 检查玩家是否有特定类型的Power
     */
    private boolean hasPowerOfClass(AbstractPlayer p, Class<? extends AbstractPower> powerClass) {
        for (AbstractPower power : p.powers) {
            if (powerClass.isInstance(power)) {
                return true;
            }
        }
        return false;
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
}