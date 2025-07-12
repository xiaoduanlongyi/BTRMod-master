package btrmod.cards.power.special;

import btrmod.cards.BaseCard;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class Guitar extends BaseCard {
    public static final String ID = makeID(Guitar.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    private static final int GROOVE = 7;
    private static final int UPG_GROOVE = 3;

    public Guitar() {
        super(ID, info);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Guitar();
    }

}
