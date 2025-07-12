package btrmod.cards.attack.rare;

import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.cards.power.special.ToBocchiSolo;
import btrmod.cards.power.special.ToKitaSolo;
import btrmod.cards.power.special.ToNijikaSolo;
import btrmod.cards.power.special.ToRyoSolo;
import btrmod.character.KessokuBandChar;
import btrmod.interfaces.GrooveMultiplierCard;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.powers.SoloPowers.SoloPower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static btrmod.util.CardTagEnum.*;

public class IfConstellation extends BaseCard implements GrooveMultiplierCard {
    public static final String ID = makeID(IfConstellation.class.getSimpleName());
    private static final CardStats INFO = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            2
    );
    private static final int PER_STACK_DAMAGE = 3;
    private static final int UPG_PER_STACK_DAMAGE = 1;
    private static final float GROOVE_MULTIPLIER = 3;
    private static final float UPG_GROOVE_MULTIPLIER = 4;

    public IfConstellation() {
        super(ID, INFO);
        setDamage(0, 0);
        setMagic(PER_STACK_DAMAGE, UPG_PER_STACK_DAMAGE);

        tags.add(GROOVE_EXHAUST);
        tags.add(GROOVE_USE);
    }

    @Override
    public float getGrooveMultiplier() {
        if (!this.upgraded)
            return GROOVE_MULTIPLIER;
        else
            return UPG_GROOVE_MULTIPLIER;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 把所有 Groove 消耗掉
        if (p.hasPower(GroovePower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, GroovePower.POWER_ID));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        BgmManager.playCustomBGM("bgm/IfConstellation.ogg");

        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new ToBocchiSolo());
        stanceChoices.add(new ToKitaSolo());
        stanceChoices.add(new ToNijikaSolo());
        stanceChoices.add(new ToRyoSolo());

        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 更新描述以显示总伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            int totalDamage = this.damage;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);

        // 更新描述以显示总伤害
        this.rawDescription = cardStrings.DESCRIPTION;
        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
            int totalDamage = this.damage;
            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }


    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new IfConstellation();
    }
}