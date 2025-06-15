package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class RyoConnection extends BaseCard {
    public static final String ID = makeID(RyoConnection.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int BAP_REDUCE = 1;
    private static final int UPG_BAP_REDUCE = 0;
    private static final int DRAW = 1;
    private static final int UPG_DRAW = 1;

    public RyoConnection() {
        super(ID, info);

        setMagic(DRAW, UPG_DRAW);
        setCustomVar("BAP", BAP_REDUCE, UPG_BAP_REDUCE);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExhaustAction(1, true, false, false));
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, customVar("BAP")));
        addToBot(new DrawCardAction(magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoConnection();
    }
}