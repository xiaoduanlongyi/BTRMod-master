package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.WormBocchiPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class WormBocchi extends BaseCard {
    public static final String ID = makeID(WormBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int INTANGIBLE_AMT = 2;
    private static final int UPGRADE_PLUS_INTANGIBLE = 1;

    public WormBocchi() {
        super(ID, info);

        setMagic(INTANGIBLE_AMT, UPGRADE_PLUS_INTANGIBLE);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WormBocchiPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new WormBocchi();
    }
}