package btrmod.cards.attack.rare;

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
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.GROOVE_EXHAUST;

public class ConfidentBocchi extends BaseCard {
    public static final String ID = makeID(ConfidentBocchi.class.getSimpleName());
    private static final CardStats INFO = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );
    private static final int PER_STACK_DAMAGE = 4;
    private static final int UPG_PER_STACK_DAMAGE = 2;
    private static final int BAP_MAX = 2;
    private static final int UPG_BAP_MAX = 1;

    public ConfidentBocchi() {
        super(ID, INFO);
        // 初始设个 0，真正的数值我们在 applyPowers 里动态重写
        setDamage(0, 0);
        setMagic(PER_STACK_DAMAGE, UPG_PER_STACK_DAMAGE);
        setCustomVar("BAP_VALUE", BAP_MAX, UPG_BAP_MAX);

        tags.add(GROOVE_EXHAUST);
        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 先把所有 Groove 消耗掉，并拿到它的层数
        int rawStacks = 0;
        if (p.hasPower(GroovePower.POWER_ID)) {
            rawStacks = p.getPower(GroovePower.POWER_ID).amount;
            // 用 RemoveSpecificPowerAction 一次性移除
            addToBot(new RemoveSpecificPowerAction(p, p, GroovePower.POWER_ID));
        }

        // What multiplier does BocchiSoloPower impose?
        float mult = 1f;
        if (p.hasPower(BocchiSoloPower.POWER_ID)) {
            mult = ((SoloPower) p.getPower(BocchiSoloPower.POWER_ID))
                    .getGrooveMultiplier();
        }

        //Effective stacks = raw × multiplier
        int effectiveStacks = MathUtils.floor(rawStacks * mult);

        // 按消耗层数×伤害 发一次伤害
        if (rawStacks > 0) {
            int totalDmg = effectiveStacks * magicNumber;
            addToBot(new DamageAction(
                    m,
                    new DamageInfo(p, totalDmg, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_HEAVY
            ));
        }

        addToBot(new ApplyPowerAction(p, p, new BocchiSoloPower(p)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (p.getPower(BocchiAfraidPower.POWER_ID) != null)
            if (p.getPower(BocchiAfraidPower.POWER_ID).amount > customVar("BAP_VALUE")) {
            //this.cantUseMessage = "需要 " + this.magicNumber + " 层律动";
                return false;
        }
        return true;
    }

    @Override
    public void applyPowers() {
        // 先走父类，保证 this.damage 被重置成 baseDamage(0) 加上 Strength/Vulnerable 等
        super.applyPowers();

        // 动态计算当前应该显示的伤害：消耗层数 × 4
        int rawStacks = AbstractDungeon.player.hasPower(GroovePower.POWER_ID)
                ? AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount
                : 0;

        float mult = 1;
        if (AbstractDungeon.player.hasPower(BocchiSoloPower.POWER_ID)) {
            mult = ((SoloPower)AbstractDungeon.player
                    .getPower(BocchiSoloPower.POWER_ID))
                    .getGrooveMultiplier();
        }

            int effectiveStacks = MathUtils.floor(rawStacks * mult);
            int previewDmg = effectiveStacks * magicNumber;

            setCustomVar("PREVIEW_DAMAGE", previewDmg);

            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ConfidentBocchi();
    }
}