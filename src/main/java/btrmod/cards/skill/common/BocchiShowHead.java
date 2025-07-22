package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import static btrmod.util.CardTagEnum.*;

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
    private static final int UPG_BAP_REDUCE = 1;
    private static final int DRAW = 2;
    private static final int UPG_DRAW = 0;

    public BocchiShowHead() {
        super(ID, info);

        setMagic(BAP_REDUCE, UPG_BAP_REDUCE);
        setCustomVar("DRW", DRAW, UPG_DRAW);

        tags.add(BOCCHI);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, customVar("DRW")), customVar("DRW")));

        CardCrawlGame.sound.play("BocchiShowHead");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiShowHead();
    }
}