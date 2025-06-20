package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class RyoTouchFace extends BaseCard {
    public static final String ID = makeID(RyoTouchFace.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    private static final int BAP_REDUCE = 1;
    private static final int UPG_BAP_REDUCE = 0;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public RyoTouchFace() {
        super(ID, info);

        setMagic(BAP_REDUCE, UPG_BAP_REDUCE);
        setBlock(BLOCK, UPG_BLOCK);

        tags.add(RYO);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, magicNumber));
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoTouchFace();
    }
}