package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static btrmod.util.CardTagEnum.*;

public class Swing extends BaseCard {
    public static final String ID = makeID(Swing.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;
    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;

    public Swing() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        this.isMultiDamage = true;

        tags.add(KITA);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(p, new CleaveEffect(), 0.1F));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV")), customVar("GRV")));

        CardCrawlGame.sound.play("Swing");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Swing();
    }
    }
