package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.AnoBandPower;
import btrmod.powers.RyoThumbUpPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class AnoBand extends BaseCard {
    public static final String ID = makeID(AnoBand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int GROOVE = 2;
    private static final int UPG_GROOVE = 1;

    public AnoBand() {
        super(ID, info);

        setCustomVar("GRV", GROOVE, UPG_GROOVE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new AnoBandPower(p, customVar("GRV"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new AnoBand();
    }
}
