package btrmod.cards.attack.uncommon;

import btrmod.actions.PinkBocchiAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.interfaces.GrooveMultiplierCard;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.powers.SoloPowers.SoloPower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class PinkBocchi extends BaseCard implements GrooveMultiplierCard {
    public static final String ID = makeID(PinkBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            -1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 0;
    private static  final int GROOVE_TOUSE = 5;
    private static  final int UPG_GROOVE_TOUSE = 0;
    private static final float GROOVE_MULTIPLIER = 0.34f;
    private static final float UPG_GROOVE_MULTIPLIER = 0.5f;

    public PinkBocchi() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        //setMagic(GROOVE_TOUSE, UPG_GROOVE_TOUSE);
        setCustomVar("GRV_USE", 3, -1);
        this.isMultiDamage = true;

        tags.add(BOCCHI);
        tags.add(GROOVE_USE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PinkBocchiAction(p, damage, damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));

        CardCrawlGame.sound.play("PinkBocchi");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    // 2) This controls the actual damage against a specific monster
    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PinkBocchi();
    }

    @Override
    public float getGrooveMultiplier() {
        if(!upgraded)
            return GROOVE_MULTIPLIER;
        else
            return UPG_GROOVE_MULTIPLIER;
    }
}
