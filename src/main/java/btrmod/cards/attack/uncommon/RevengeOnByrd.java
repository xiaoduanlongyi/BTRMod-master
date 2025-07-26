package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Byrd;

import static btrmod.util.CardTagEnum.BOCCHI;

public class RevengeOnByrd extends BaseCard {
    public static final String ID = makeID(RevengeOnByrd.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;
    private static final int HITTIME = 4;
    private static final int UPG_HITTIME = 0;
    private static final float BYRD_DAMAGE_MULTIPLIER = 2.0F;

    public RevengeOnByrd() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(HITTIME, UPG_HITTIME);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; ++i) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        // 先调用父类方法计算基础修正
        float modifiedDamage = super.calculateModifiedCardDamage(player, mo, tmp);

        // 如果目标是Byrd，应用伤害倍率
        if (isTargetByrd(mo)) {
            modifiedDamage *= BYRD_DAMAGE_MULTIPLIER;
        }

        return modifiedDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        // 调用父类方法进行标准计算
        super.calculateCardDamage(mo);
    }

    // 检查目标是否为Byrd的辅助方法
    private boolean isTargetByrd(AbstractMonster m) {
        if (m == null) {
            return false;
        }

        // 使用instanceof判断
        return m instanceof Byrd;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RevengeOnByrd();
    }
}