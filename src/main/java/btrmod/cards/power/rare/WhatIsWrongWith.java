package btrmod.cards.power.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.ShimokitaAngelPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.WhatIsWrongWithPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.NIJIKA;

public class WhatIsWrongWith extends BaseCard {
    public static final String ID = makeID(WhatIsWrongWith.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );

    private static final int DRAW = 1;
    private static final int UPG_DRAW = 0;

    public WhatIsWrongWith() {
        super(ID, info);

        setCostUpgrade(2);
        setMagic(DRAW, UPG_DRAW);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WhatIsWrongWithPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WhatIsWrongWith();
    }
}
