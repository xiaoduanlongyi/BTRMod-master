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

public class PinkBocchi extends BaseCard {
    public static final String ID = makeID(PinkBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            -1
    );

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;
    private static final int BAP = 3;
    private static final int UPG_BAP = 1;

    public PinkBocchi() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(BAP, UPG_BAP);
        this.isMultiDamage = true;

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PinkBocchiAction(p, damage, damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse, magicNumber));

        CardCrawlGame.sound.play("PinkBocchi");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        super.calculateCardDamage(m);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PinkBocchi();
    }
}
