package btrmod.cards.status;

import btrmod.cards.BaseCard;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static btrmod.util.CardTagEnum.*;

public class FantasySurf extends BaseCard {
    public static final String ID = makeID(FantasySurf.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int VULN = 1;
    private static final int UPG_VULN = 0;
    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;

    public FantasySurf() {
        super(ID, info);

        setMagic(VULN, UPG_VULN);
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

            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new VulnerablePower(AbstractDungeon.player, magicNumber, false)
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
        return new FantasySurf();
    }
}
