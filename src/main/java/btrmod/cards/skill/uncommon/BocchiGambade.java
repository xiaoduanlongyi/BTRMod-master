package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiGambade extends BaseCard {
    public static final String ID = makeID(BocchiGambade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int DRAW = 3;
    private static final int UPG_DRAW = 1;

    public BocchiGambade() {
        super(ID, info);

        setMagic(DRAW,UPG_DRAW);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new ReducePowerAction(p,p, BocchiAfraidPower.POWER_ID,1));
        addToBot(new ApplyPowerAction(p, p, new BocchiSoloPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiGambade();
    }
}