package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.GROOVE_GRANT;

public class FatherTear extends BaseCard {
    public static final String ID = makeID(FatherTear.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 4;
    private static final int BAP = 2;
    private static final int UPG_BAP = 0;
    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;

    public FatherTear() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(BAP, UPG_BAP);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new ReducePowerAction(p,p, BocchiAfraidPower.POWER_ID, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FatherTear();
    }
}