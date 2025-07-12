package btrmod.cards.skill.rare;

import btrmod.actions.SearchForBocchiCardsAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.KITA;

public class DarwinKita extends BaseCard {
    public static final String ID = makeID(DarwinKita.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            0
    );

    private static final int DRAW = 2;
    private static final int UPG_DRAW = 0;

    public DarwinKita() {
        super(ID, info);

        setMagic(DRAW, UPG_DRAW);
        setExhaust(true, false);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SearchForBocchiCardsAction(magicNumber, true));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DarwinKita();
    }
}