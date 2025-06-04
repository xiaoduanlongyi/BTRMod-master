package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiShowHead extends BaseCard {
    public static final String ID = makeID(BocchiShowHead.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            2
    );

    private static final int BAP_REDUCE = 2;
    private static final int UPG_BAP_REDUCE = 0;
    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 0;

    public BocchiShowHead() {
        super(ID, info);

        setMagic(BAP_REDUCE, UPG_BAP_REDUCE);
        setCostUpgrade(1);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiShowHead();
    }
}