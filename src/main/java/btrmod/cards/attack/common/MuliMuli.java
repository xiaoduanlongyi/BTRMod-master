package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javax.smartcardio.Card;

import static btrmod.util.CardTagEnum.*;

public class MuliMuli extends BaseCard {
    public static final String ID = makeID(MuliMuli.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 0;

    public MuliMuli() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setCustomVar("GRV", GROOVE, UPG_GROOVE);

        tags.add(BOCCHI);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new MakeTempCardInDiscardAction(this.makeStatEquivalentCopy(), 1));

        CardCrawlGame.sound.play("MuliMuli");
        }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MuliMuli();
    }
}
