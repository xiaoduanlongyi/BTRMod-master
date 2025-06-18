package btrmod.cards.attack.rare;

import btrmod.actions.RyoEatEatAction;
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

import static btrmod.util.CardTagEnum.RYO;

public class RyoEatEat extends BaseCard {
    public static final String ID = makeID(RyoEatEat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int DAMAGE = 8;           // per enemy
    private static final int UPG_DAMAGE = 3;
    private static final int FEED_BUFF = 2;        // maxHP per kill
    private static final int UPG_FEED_BUFF = 1;

    public RyoEatEat() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(FEED_BUFF, UPG_FEED_BUFF);
        this.isMultiDamage = true;
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // we ignore 'm' here, because our action loops every enemy
        addToBot(new RyoEatEatAction(p, this.multiDamage, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new RyoEatEat();
    }
}