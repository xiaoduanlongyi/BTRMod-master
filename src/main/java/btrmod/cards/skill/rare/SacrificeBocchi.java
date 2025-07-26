package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class SacrificeBocchi extends BaseCard {
    public static final String ID = makeID(SacrificeBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            0
    );

    private static final int DRAW = 3;
    private static final int UPG_DRAW = 2;
    private static final int ENERGY = 2;
    private static final int UPG_ENERGY = 0;
    private static final int BOCCHI_AFRAID = 3;

    public SacrificeBocchi() {
        super(ID, info);

        setMagic(DRAW, UPG_DRAW);
        setCustomVar("ENE", ENERGY, UPG_ENERGY);
        setCustomVar("BAP", BOCCHI_AFRAID, 0);
        setExhaust(true,true);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, magicNumber));
        addToBot(new GainEnergyAction(customVar("ENE")));
        addToBot(new ApplyPowerAction(p,p, new BocchiAfraidPower(p,customVar("BAP"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SacrificeBocchi();
    }
}