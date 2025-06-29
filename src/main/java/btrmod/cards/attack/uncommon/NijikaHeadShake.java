package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.interfaces.GrooveMultiplierCard;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.SoloPower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class NijikaHeadShake extends BaseCard implements GrooveMultiplierCard {
    public static final String ID = makeID(NijikaHeadShake.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 0;
    private static  final int HIT_TIME= 3;
    private static  final int UPG_HIT_TIME = 1;
    private static final float GROOVE_MULTIPLIER = 0.5f;

    public NijikaHeadShake() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(HIT_TIME, UPG_HIT_TIME);
        setExhaust(true);

        tags.add(NIJIKA);
        tags.add(GROOVE_USE);
    }

    @Override
    public float getGrooveMultiplier() {
        return GROOVE_MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; ++i) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        CardCrawlGame.sound.play("NijikaShakeHead");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 更新描述以显示总伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            int totalDamage = this.damage * this.magicNumber;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + totalDamage + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        // 更新描述以显示总伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            int totalDamage = this.damage * this.magicNumber;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + totalDamage + cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaHeadShake();
    }
}
