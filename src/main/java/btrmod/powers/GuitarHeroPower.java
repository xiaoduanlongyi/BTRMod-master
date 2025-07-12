package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.BTRMod.makeID;
import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarHeroPower extends BasePower {
    public static final String POWER_ID = makeID(GuitarHeroPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public GuitarHeroPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        priority = 2;
    }

    private int cardsDoubledThisTurn = 0;

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(DESCRIPTIONS[1]);
            sb.append(this.amount);
            sb.append(DESCRIPTIONS[2]);

            for(int i = 0; i < this.amount; ++i) {
                sb.append("[E] ");
            }

            sb.append(LocalizedStrings.PERIOD);
            this.description = sb.toString();
        }
    }

    @Override
    public void atStartOfTurn() {
        this.cardsDoubledThisTurn = 0;
        this.addToBot(new GainEnergyAction(this.amount));
        this.flash();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.tags.contains(BOCCHI)) {
            return;
        }

        if (!card.purgeOnUse && this.cardsDoubledThisTurn < this.amount) {
            this.cardsDoubledThisTurn++;
            this.flash();

            AbstractMonster m = (action.target instanceof AbstractMonster)
                    ? (AbstractMonster)action.target
                    : null;

            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
        }

    }

}