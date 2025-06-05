package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiHeartbeat extends BaseCard {
    public static final String ID = makeID(BocchiHeartbeat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DRAW = 2;
    private static final int UPG_DRAW = 1;
    private static final int GROOVE = 2;
    private static final int UPG_GROOVE = 0;

    public BocchiHeartbeat() {
        super(ID, info);

        setMagic(DRAW,UPG_DRAW);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        cardsToPreview = new Burn();

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, false, false));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiHeartbeat();
    }
}