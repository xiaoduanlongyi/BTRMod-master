package btrmod.cards.attack.special;

import btrmod.cards.BaseCard;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class RyoHenchman2 extends BaseCard {
    public static final String ID = makeID(RyoHenchman2.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;

    public RyoHenchman2() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(WEAK, UPG_WEAK);
        setExhaust(true);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoHenchman2();
    }
}
