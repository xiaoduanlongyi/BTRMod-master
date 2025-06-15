package btrmod.cards.power.special;

import btrmod.cards.BaseCard;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BluePlanet extends BaseCard {
    public static final String ID = makeID(BluePlanet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int BAP_REDUCE = 2;
    private static final int UPG_BAP_REDUCE = 1;

    public BluePlanet() {
        super(ID, info);
        setCustomVar("BAP", BAP_REDUCE, UPG_BAP_REDUCE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, customVar("BAP")));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BluePlanet();
    }

}
