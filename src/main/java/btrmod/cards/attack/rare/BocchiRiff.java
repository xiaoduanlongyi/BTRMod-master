package btrmod.cards.attack.rare;

import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.subscribers.ComboSoloSubscriber;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class BocchiRiff extends BaseCard {
    public static final String ID = makeID(BocchiRiff.class.getSimpleName());
    private static final CardStats INFO = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );
    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 8;
    private static final int BAP = 2;
    private static final int UPG_BAP = 0;
    private static final int GROOVE = 4;
    private static final int UPG_GROOVE = 2;

    public BocchiRiff() {
        super(ID, INFO);

        setDamage(DAMAGE,UPG_DAMAGE);
        setCustomVar("BAP", BAP, UPG_BAP);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        this.isMultiDamage = true;

        tags.add(BOCCHI);
        tags.add(GROOVE_GRANT);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.LIGHTNING));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, customVar("BAP")));

        ComboSoloSubscriber combo = BTRMod.getComboSoloSubscriber();
        if (combo != null) {
            combo.clearRecentTags();
        }
        addToBot(new ApplyPowerAction(p, p, new BocchiSoloPower(p)));

        CardCrawlGame.sound.playV("CHAR_CHOOSE", 1.5f);
    }

    @Override
    public AbstractCard makeCopy() {
        return new BocchiRiff();
    }
}