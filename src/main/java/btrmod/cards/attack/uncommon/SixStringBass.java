package btrmod.cards.attack.uncommon;

import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class SixStringBass extends BaseCard {
    public static final String ID = makeID(SixStringBass.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    private static final int DISTOR = 2;
    private static final int UPG_DISTOR = 0;
    private static final int GROOVE = 3;
    private static final int UPG_GROOVE = 0;

    public SixStringBass() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DISTOR,UPG_DISTOR);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(KITA);
        tags.add(GROOVE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, GroovePower.POWER_ID, customVar("GRV")));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new SixStringBass();
    }
}