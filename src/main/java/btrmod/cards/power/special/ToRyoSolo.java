package btrmod.cards.power.special;

import btrmod.cards.BaseCard;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

public class ToRyoSolo extends BaseCard {
    public static final String ID = makeID(ToRyoSolo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public ToRyoSolo() {
        super(ID, info);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.effectList.add(new SpotlightPlayerEffect());
        addToBot(new ApplyPowerAction(p, p, new RyoSoloPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ToRyoSolo();
    }

}
