package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
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

import static btrmod.util.CardTagEnum.GROOVE_GRANT;
import static btrmod.util.CardTagEnum.NIJIKA;

public class NijikaMiddleFinger extends BaseCard {
    public static final String ID = makeID(NijikaMiddleFinger.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 11;
    private static final int UPG_DAMAGE = 0;
    private static  final int GROOVE = 2;
    private static  final int UPG_GROOVE = 1;

    public NijikaMiddleFinger() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(NIJIKA);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new ApplyPowerAction(p, p, new NijikaSoloPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaMiddleFinger();
    }
}
