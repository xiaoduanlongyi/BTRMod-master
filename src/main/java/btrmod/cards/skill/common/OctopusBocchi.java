package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class OctopusBocchi extends BaseCard {
    public static final String ID = makeID(OctopusBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 2;
    private static final int BAP = 2;
    private static final int UPG_BAP = -1;

    public OctopusBocchi() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setCustomVar("BAP", BAP, UPG_BAP);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,block));
        addToBot(new ApplyPowerAction(p, p, new BocchiAfraidPower(p, customVar("BAP"))));
        addToBot(new ExhaustAction(1, true, false, false));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new OctopusBocchi();
    }
}