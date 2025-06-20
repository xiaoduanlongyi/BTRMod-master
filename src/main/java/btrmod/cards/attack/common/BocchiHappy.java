package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class BocchiHappy extends BaseCard {
    public static final String ID = makeID(BocchiHappy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 0;
    private static final int BAP = 1;
    private static final int UPG_BAP = 1;

    public BocchiHappy() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("BAP", BAP, UPG_BAP);

        tags.add(BOCCHI);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, customVar("BAP")));
        }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiHappy();
    }
}
