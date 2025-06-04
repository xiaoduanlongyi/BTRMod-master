package btrmod.subscribers;

import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.CardTagEnum;
import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一把「打出带 Solo 对应标签的卡时 → 额外给玩家 +1 层 GroovePower」的逻辑放在一起。
 */
public class SoloGrantGrooveSubscriber implements OnCardUseSubscriber {
    // 标签 SoloPower ID 的映射表
    private static final Map<AbstractCard.CardTags, String> TAG_TO_SOLO = new HashMap<>();

    static {
        TAG_TO_SOLO.put(CardTagEnum.BOCCHI,  BocchiSoloPower.POWER_ID);
        TAG_TO_SOLO.put(CardTagEnum.KITA,   KitaSoloPower.POWER_ID);
        TAG_TO_SOLO.put(CardTagEnum.NIJIKA, NijikaSoloPower.POWER_ID);
        TAG_TO_SOLO.put(CardTagEnum.RYO,    RyoSoloPower.POWER_ID);
    }

    @Override
    public void receiveCardUsed(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;

        // 遍历映射表，检查卡牌标签 + 玩家身上 SoloPower
        for (Map.Entry<AbstractCard.CardTags, String> e : TAG_TO_SOLO.entrySet()) {
            AbstractCard.CardTags tag = e.getKey();
            String soloPowerId = e.getValue();

            if (card.tags.contains(tag) && p.hasPower(soloPowerId)) {
                // 玩家在打出一张带 tag 的卡，且身上有对应的 SoloPower，就额外 +1 层 GroovePower
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(
                                p, p,
                                new GroovePower(p, 1),
                                1
                        )
                );
                break; // 找到一个匹配后就不必继续遍历了
            }
        }
    }
}