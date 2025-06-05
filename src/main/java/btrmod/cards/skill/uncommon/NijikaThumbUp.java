package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.NIJIKA;

public class NijikaThumbUp extends BaseCard {
    public static final String ID = makeID(NijikaThumbUp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BAP = 1;
    private static final int UPG_BAP = 0;
    private static final int BONUS_BAP_REDUCTION = 2;
    private static final int UPG_BONUS_BAP_REDUCTION = 1;
    private static final int AFRAID_THRESHOLD = 5;

    public NijikaThumbUp() {
        super(ID, info);

        setMagic(BAP, UPG_BAP);
        setCustomVar("BASE", BAP, UPG_BAP);
        setCustomVar("BONUS", BONUS_BAP_REDUCTION, UPG_BONUS_BAP_REDUCTION);
        setCustomVar("THRESHOLD", AFRAID_THRESHOLD);

        tags.add(NIJIKA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int currentBAPStacks = 0;
        if (p.hasPower((BocchiAfraidPower.POWER_ID))) {
            currentBAPStacks = p.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        int totalReduction = magicNumber;
        if (currentBAPStacks > customVar("THRESHOLD")) {
            totalReduction += customVar("BONUS");
        }

        if (totalReduction > 0 && currentBAPStacks > 0) {
            addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, totalReduction));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaThumbUp();
    }
}