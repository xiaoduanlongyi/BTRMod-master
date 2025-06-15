package btrmod.cards.power.special;

import btrmod.cards.BaseCard;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Loneliness extends BaseCard {
    public static final String ID = makeID(Loneliness.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int DIST = 2;
    private static final int UPG_DIST = 1;

    public Loneliness() {
        super(ID, info);
        setMagic(DIST, UPG_DIST);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p ,p ,new DistortionPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Loneliness();
    }

}
