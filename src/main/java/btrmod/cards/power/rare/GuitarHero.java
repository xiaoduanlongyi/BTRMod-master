package btrmod.cards.power.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GuitarHeroPower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarHero extends BaseCard {
    public static final String ID = makeID(GuitarHero.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    public GuitarHero() {
        super(ID, info);

        setEthereal(true,false);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p,p,new GuitarHeroPower(p,1),1));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GuitarHero();
    }
}
