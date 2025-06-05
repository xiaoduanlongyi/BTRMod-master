package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.cards.attack.special.RyoHenchman1;
import btrmod.cards.attack.special.RyoHenchman2;
import btrmod.character.KessokuBandChar;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class RyoSuit extends BaseCard {
    public static final String ID = makeID(RyoSuit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int MONEY = 10;
    private static final int UPG_MONEY = 10;

    public RyoSuit() {
        super(ID, info);

        setMagic(MONEY, UPG_MONEY);
        cardsToPreview = new RyoHenchman1();
        cardsToPreview = new RyoHenchman2();
        setExhaust(true);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainGoldAction(magicNumber));
        addToBot(new MakeTempCardInHandAction(new RyoHenchman1(), 1));
        addToBot(new MakeTempCardInHandAction(new RyoHenchman2(), 1));
        addToBot(new ApplyPowerAction(p, p, new RyoSoloPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoSuit();
    }
}