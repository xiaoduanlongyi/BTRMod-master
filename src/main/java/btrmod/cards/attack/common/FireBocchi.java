package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class FireBocchi extends BaseCard {
    public static final String ID = makeID(FireBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 16;
    private static final int UPG_DAMAGE = 4;
    private static final int VULNERABLE = 2;
    private static final int UPG_VULNERABLE = 1;
    private static final int BAP_THRESHOLD = 5;
    private static final int UPG_BAP_THRESHOLD = 0;

    public FireBocchi() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(VULNERABLE, UPG_VULNERABLE);
        setCustomVar("BAP", BAP_THRESHOLD, UPG_BAP_THRESHOLD);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false), magicNumber));

        CardCrawlGame.sound.play("FireBocchi");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        AbstractPower BAP = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID);
        if (BAP != null && BAP.amount > BAP_THRESHOLD) {
            this.setCostForTurn(0);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        AbstractPower BAP = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID);
        if (BAP != null && BAP.amount > BAP_THRESHOLD) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FireBocchi();
    }
}
