package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class FadeAway extends BaseCard {
    public static final String ID = makeID(FadeAway.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int BASE_DAMAGE = 6;
    private static final int UPG_DAMAGE = 0;
    private static final int DAMAGE_PER_BAP = 3;
    private static final int UPG_DAMAGE_PER_BAP = 1;

    public FadeAway() {
        super(ID, info);

        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        setMagic(DAMAGE_PER_BAP, UPG_DAMAGE_PER_BAP);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_HEAVY));

        CardCrawlGame.sound.playV("FadeAway", 1.5f);
    }

    @Override
    public void applyPowers() {
        // 先获取自闭层数
        int afraidStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BocchiAfraidPower.POWER_ID)) {
            afraidStacks = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        // 计算额外伤害
        int bonusDamage = afraidStacks * this.magicNumber;

        // 临时修改基础伤害来应用加成
        int originalBaseDamage = this.baseDamage;
        this.baseDamage += bonusDamage;

        // 调用父类方法应用所有效果（力量、易伤等）
        super.applyPowers();

        // 恢复原始基础伤害
        this.baseDamage = originalBaseDamage;

        // 如果有额外伤害，标记为已修改并更新描述
        if (bonusDamage > 0) {
            this.isDamageModified = true;

            // 更新描述显示总伤害
            this.rawDescription = cardStrings.DESCRIPTION;
            if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
                this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.damage +
                        cardStrings.EXTENDED_DESCRIPTION[1];
            }
            this.initializeDescription();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        // 先获取自闭层数
        int afraidStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BocchiAfraidPower.POWER_ID)) {
            afraidStacks = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        // 计算额外伤害
        int bonusDamage = afraidStacks * this.magicNumber;

        // 临时修改基础伤害
        int originalBaseDamage = this.baseDamage;
        this.baseDamage += bonusDamage;

        // 调用父类方法计算对特定怪物的伤害
        super.calculateCardDamage(mo);

        // 恢复原始基础伤害
        this.baseDamage = originalBaseDamage;

        // 更新描述
        if (bonusDamage > 0) {
            this.isDamageModified = true;

            this.rawDescription = cardStrings.DESCRIPTION;
            if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
                this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + this.damage +
                        cardStrings.EXTENDED_DESCRIPTION[1];
            }
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        // 重置描述
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new FadeAway();
    }
}