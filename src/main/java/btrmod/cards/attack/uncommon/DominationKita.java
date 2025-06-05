package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.DistortionPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.GROOVE_EXHAUST;
import static btrmod.util.CardTagEnum.KITA;

public class DominationKita extends BaseCard {
    public static final String ID = makeID(DominationKita.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE_PER_BOCCHI = 8;
    private static final int UPG_DAMAGE_PER_BOCCHI = 3;
    private static final int DISTOR = 1;
    private static final int UPG_DISTOR = 1;

    public DominationKita() {
        super(ID, info);

        setDamage(0, 0);
        setMagic(DAMAGE_PER_BOCCHI, UPG_DAMAGE_PER_BOCCHI);
        setCustomVar("DISTOR", DISTOR, UPG_DISTOR);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, customVar("DISTOR"))));
    }

    @Override
    public void applyPowers() {
        // 计算手牌中BOCCHI卡的数量
        int bocchiCount = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(CardTagEnum.BOCCHI)) {
                    bocchiCount++;
                }
            }
        }

        // 计算总伤害
        int totalDamage = bocchiCount * this.magicNumber;

        // 临时修改基础伤害
        int originalBaseDamage = this.baseDamage;
        this.baseDamage = totalDamage;

        // 调用父类方法应用力量等效果
        super.applyPowers();

        // 恢复原始基础伤害
        this.baseDamage = originalBaseDamage;

        // 如果有BOCCHI卡，标记为已修改并更新描述
        if (bocchiCount > 0) {
            this.isDamageModified = true;
        }

        // 更新描述显示当前伤害和BOCCHI卡数量
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + damage +
                    cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        // 计算手牌中BOCCHI卡的数量
        int bocchiCount = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(CardTagEnum.BOCCHI)) {
                    bocchiCount++;
                }
            }
        }

        // 计算总伤害
        int totalDamage = bocchiCount * this.magicNumber;

        // 临时修改基础伤害
        int originalBaseDamage = this.baseDamage;
        this.baseDamage = totalDamage;

        // 调用父类方法计算对特定怪物的伤害
        super.calculateCardDamage(mo);

        // 恢复原始基础伤害
        this.baseDamage = originalBaseDamage;

        // 如果有BOCCHI卡，标记为已修改
        if (bocchiCount > 0) {
            this.isDamageModified = true;
        }

        // 更新描述
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + damage +
                    cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        // 移除时重置描述
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DominationKita();
    }
}