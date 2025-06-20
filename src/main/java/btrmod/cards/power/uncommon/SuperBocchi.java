package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.REDUCE_BAP;

public class SuperBocchi extends BaseCard {
    public static final String ID = makeID(SuperBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int STRENGTH_AND_DEXTERITY = 2;
    private static final int UPG_STRENGTH_AND_DEXTERITY = 1;
    private static final int BOCCHI_AFRAID = 1;
    private static final int UPG_BOCCHI_AFRAID = 1;

    public SuperBocchi() {
        super(ID, info);

        setMagic(STRENGTH_AND_DEXTERITY, UPG_STRENGTH_AND_DEXTERITY);
        setCustomVar("BA1",BOCCHI_AFRAID,UPG_BOCCHI_AFRAID);

        tags.add(BOCCHI);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p,p, BocchiAfraidPower.POWER_ID,customVar("BA1")));
        addToBot(new ApplyPowerAction(p,p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p,p, new DexterityPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SuperBocchi();
    }
}
