package btrmod.cards.status;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class FantasyRockYou extends BaseCard {
    public static final String ID = makeID(FantasyRockYou.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;
    private static final int BOCCHI_AFRAID = 1;
    private static final int UPG_BOCCHI_AFRAID = 0;

    public FantasyRockYou() {
        super(ID, info);

        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setCustomVar("BAP", BOCCHI_AFRAID, UPG_BOCCHI_AFRAID);
        setSelfRetain(true, true);

        tags.add(BOCCHI);
        tags.add(GROOVE_GRANT);
        tags.add(FANTASY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            this.flash();

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new GroovePower(AbstractDungeon.player, customVar("GRV"))
            ));

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new BocchiAfraidPower(AbstractDungeon.player, customVar("BAP"))
            ));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FantasyRockYou();
    }
}