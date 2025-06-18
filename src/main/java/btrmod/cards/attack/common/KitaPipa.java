package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static btrmod.util.CardTagEnum.*;

public class KitaPipa extends BaseCard {
    public static final String ID = makeID(KitaPipa.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 0;
    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;
    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;

    public KitaPipa() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(WEAK, UPG_WEAK);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(KITA);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV")), customVar("GRV")));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaPipa();
    }
}
