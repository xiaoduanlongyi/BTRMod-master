package btrmod.cards.attack.uncommon;

import btrmod.actions.GiveMeMoneyAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class GiveMeMoney extends BaseCard {
    public static final String ID = makeID(GiveMeMoney.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 8;
    private static final int BAP = 2;
    private static final int UPG_BAP = 0;

    public GiveMeMoney() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setCustomVar("BAP", BAP, UPG_BAP);
        setExhaust(true);
        isMultiDamage = true;

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GiveMeMoneyAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new BocchiAfraidPower(p, customVar("BAP"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GiveMeMoney();
    }
}
