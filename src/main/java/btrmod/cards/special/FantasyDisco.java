package btrmod.cards.special;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class FantasyDisco extends BaseCard {
    public static final String ID = makeID(FantasyDisco.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = -3;
    private static final int GROOVE = 2;
    private static final int UPG_GROOVE = 0;

    public FantasyDisco() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setSelfRetain(true, true);

        tags.add(BOCCHI);
        tags.add(FANTASY);
        tags.add(GROOVE_GRANT);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            this.flash();

            addToBot(new DamageAction(
                    AbstractDungeon.player,
                    new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            ));

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new GroovePower(AbstractDungeon.player, customVar("GRV"))
            ));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FantasyDisco();
    }
}
