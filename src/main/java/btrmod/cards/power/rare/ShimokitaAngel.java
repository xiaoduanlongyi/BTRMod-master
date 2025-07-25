package btrmod.cards.power.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.ShimokitaAngelPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.NIJIKA;
import static btrmod.util.CardTagEnum.REDUCE_BAP;

public class ShimokitaAngel extends BaseCard {
    public static final String ID = makeID(ShimokitaAngel.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int BOCCHI_AFRAID = 3;
    private static final int UPG_BOCCHI_AFRAID = 1;

    public ShimokitaAngel() {
        super(ID, info);

        setMagic(BOCCHI_AFRAID, UPG_BOCCHI_AFRAID);

        tags.add(NIJIKA);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShimokitaAngelPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new NijikaSoloPower(p)));

        //CardCrawlGame.sound.play("ShimokitaAngel");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ShimokitaAngel();
    }
}
