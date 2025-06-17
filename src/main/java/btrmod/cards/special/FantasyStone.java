package btrmod.cards.special;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class FantasyStone extends BaseCard {
    public static final String ID = makeID(FantasyStone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final int BAP = 1;
    private static final int UPG_BAP = 0;

    public FantasyStone() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setCustomVar("BAP", BAP, UPG_BAP);
        setSelfRetain(true, true);

        tags.add(BOCCHI);
        tags.add(FANTASY);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(this)) {
            this.flash();

            addToBot(new GainBlockAction(
                    AbstractDungeon.player,
                    block
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
        return new FantasyStone();
    }
}
