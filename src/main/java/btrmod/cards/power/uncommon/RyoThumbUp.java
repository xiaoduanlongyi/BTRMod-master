package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.RyoThumbUpPower;
import btrmod.powers.ShimokitaAngelPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.NIJIKA;
import static btrmod.util.CardTagEnum.RYO;

public class RyoThumbUp extends BaseCard {
    public static final String ID = makeID(RyoThumbUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int GROOVE = 4;
    private static final int UPG_GROOVE = 0;

    public RyoThumbUp() {
        super(ID, info);

        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setInnate(false, true);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RyoThumbUpPower(p, customVar("GRV"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoThumbUp();
    }
}
