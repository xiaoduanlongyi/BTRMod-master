package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;
import static btrmod.util.CardTagEnum.KITA;

public class BocchiAtWork extends BaseCard {
    public static final String ID = makeID(BocchiAtWork.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BAP = 2;
    private static final int UPG_BAP = -1;
    private static final int MONEY = 25;
    private static final int UPG_MONEY = 10;
    private static final int DIST = 2;
    private static final int UPG_DIST = 0;

    public BocchiAtWork() {
        super(ID, info);

        setMagic(MONEY, UPG_MONEY);
        setCustomVar("BAP", BAP, UPG_BAP);
        setCustomVar("DIST", DIST, UPG_DIST);

        setExhaust(true);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainGoldAction(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new BocchiAfraidPower(p, customVar("BAP"))));
        addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, customVar("DIST"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiAtWork();
    }
}