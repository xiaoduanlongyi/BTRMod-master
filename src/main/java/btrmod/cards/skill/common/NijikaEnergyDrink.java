package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static btrmod.util.CardTagEnum.NIJIKA;
import static btrmod.util.CardTagEnum.RYO;

public class NijikaEnergyDrink extends BaseCard {
    public static final String ID = makeID(NijikaEnergyDrink.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int ENERGY = 1;
    private static final int UPG_ENERGY = 0;
    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public NijikaEnergyDrink() {
        super(ID, info);

        setMagic(ENERGY, UPG_ENERGY);
        setBlock(BLOCK, UPG_BLOCK);

        tags.add(NIJIKA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p ,p, new EnergizedBluePower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaEnergyDrink();
    }
}