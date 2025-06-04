package btrmod.powers.SoloPowers;

import basemod.BaseMod;
import btrmod.util.CardTagEnum;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static btrmod.BTRMod.makeID;

public class RyoSoloPower extends SoloPower{
    public static final String POWER_ID = makeID("RyoSoloPower");

    public RyoSoloPower(AbstractCreature owner) {
        super(POWER_ID, PowerType.BUFF, false, owner);
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        // 当获得 RyoSoloPower 时，强制重新计算手牌
        refreshGrooveCards();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        // 当失去 RyoSoloPower 时，强制重新计算手牌
        refreshGrooveCards();
    }

    private void refreshGrooveCards() {
        // 重新计算手牌中所有带 GROOVE_USE 标签的卡牌
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.hasTag(CardTagEnum.GROOVE_USE)) {
                card.applyPowers();
            }
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}