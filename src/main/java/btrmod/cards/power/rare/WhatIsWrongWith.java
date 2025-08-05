package btrmod.cards.power.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.WhatIsWrongWithPower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhatIsWrongWith extends BaseCard {
    public static final String ID = makeID(WhatIsWrongWith.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public WhatIsWrongWith() {
        super(ID, info);

        setMagic(BLOCK, UPG_BLOCK);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WhatIsWrongWithPower(p, magicNumber), magicNumber));

        BgmManager.playCustomBGM("bgm/WhatIsWrongWith.ogg");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WhatIsWrongWith();
    }
}
