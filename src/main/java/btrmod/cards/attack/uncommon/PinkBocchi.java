package btrmod.cards.attack.uncommon;

import btrmod.actions.PinkBocchiAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
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
    private static  final int GROOVE_TOUSE = 5;
    private static  final int UPG_GROOVE_TOUSE = 0;

    public PinkBocchi() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(GROOVE_TOUSE, UPG_GROOVE_TOUSE);
        this.isMultiDamage = true;

        tags.add(BOCCHI);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new PinkBocchiAction(p, damage, damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
        addToBot(new ApplyPowerAction(p, p, new BocchiSoloPower(p)));
        addToBot(new ReducePowerAction(p, p, GroovePower.POWER_ID, magicNumber));
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        // 先调用父类的检查（比如能量不足、不可选时等）
        if (!super.canUse(p, m)) {
            return false;
        }
        // 然后检查 GroovePower
        if (!p.hasPower(GroovePower.POWER_ID)
                || p.getPower(GroovePower.POWER_ID).amount < this.magicNumber) {
            // 如果玩家没有 Groove 或者层数 < magicNumber，就禁止打出
            this.cantUseMessage = "需要 " + this.magicNumber + " 层律动";
            return false;
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PinkBocchi();
    }
}
