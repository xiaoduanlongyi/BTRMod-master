package btrmod.cards.attack.basic;

import basemod.devcommands.Info;
import btrmod.cards.BaseCard;
import btrmod.cards.power.special.ToBocchiSolo;
import btrmod.cards.power.special.ToKitaSolo;
import btrmod.cards.power.special.ToNijikaSolo;
import btrmod.cards.power.special.ToRyoSolo;
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
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import java.util.ArrayList;

import static btrmod.util.CardTagEnum.GROOVE_USE;

public class Ensemble extends BaseCard implements GrooveMultiplierCard {
    public static final String ID = makeID(Ensemble.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 4;
    private static final int GROOVE = 4;
    private static final int UPG_GROOVE = 0;
    private static final float GROOVE_MULTIPLIER = 1f;

    public Ensemble() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(GROOVE_USE);
    }

    @Override
    public float getGrooveMultiplier() {
        return GROOVE_MULTIPLIER;
    }

//    @Override
//    public void applyPowers() {
//        super.applyPowers();
//
//        // 更新描述以显示总伤害
//        this.rawDescription = cardStrings.DESCRIPTION;
//        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
//            int totalDamage = this.damage;
//            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
//        }
//        this.initializeDescription();
//    }
//
//    @Override
//    public void calculateCardDamage(AbstractMonster mo) {
//        super.calculateCardDamage(mo);
//
//        // 更新描述以显示总伤害
//        this.rawDescription = cardStrings.DESCRIPTION;
//        if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
//            int totalDamage = this.damage;
//            this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
//        }
//        this.initializeDescription();
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
//        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));

//        ArrayList<AbstractCard> stanceChoices = new ArrayList();
//        stanceChoices.add(new ToBocchiSolo());
//        stanceChoices.add(new ToKitaSolo());
//        stanceChoices.add(new ToNijikaSolo());
//        stanceChoices.add(new ToRyoSolo());
//
//        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Ensemble();
    }
}
