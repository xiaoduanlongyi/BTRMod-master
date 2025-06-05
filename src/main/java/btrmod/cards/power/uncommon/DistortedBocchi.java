package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class DistortedBocchi extends BaseCard {
    public static final String ID = makeID(DistortedBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int DISTORT = 0;
    private static final int UPG_DISTORT = 0;

    public DistortedBocchi() {
        super(ID, info);

        setMagic(DISTORT, UPG_DISTORT);
        setCostUpgrade(1);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int distortAmount = calculateDistortAmount();
        if (distortAmount > 0) {
            addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, distortAmount), distortAmount));
        }
    }

    /**
     * 计算当前应该施加的扭曲层数
     */
    private int calculateDistortAmount() {
        // 获取自闭层数
        int afraidStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BocchiAfraidPower.POWER_ID)) {
            afraidStacks = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        // 基础扭曲层数 + 自闭层数
        return baseMagicNumber+ afraidStacks;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 计算并设置显示的 magicNumber
        this.magicNumber = calculateDistortAmount();
        this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);


        // 更新描述
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] +
                    cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        // 计算并设置显示的 magicNumber
        this.magicNumber = calculateDistortAmount();
        this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);

        // 更新描述
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] +
                    cardStrings.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        // 重置描述
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new DistortedBocchi();
    }
}
