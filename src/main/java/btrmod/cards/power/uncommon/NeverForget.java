package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.NeverForgetPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NeverForget extends BaseCard {
    public static final String ID = makeID(NeverForget.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int STACK = 1;
    private static final int UPG_STACK = 0;

    public NeverForget() {
        super(ID, info);

        setMagic(STACK, UPG_STACK);
        setCostUpgrade(0);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NeverForgetPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NeverForget();
    }
}
