package btrmod.cards.attack.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static btrmod.util.CardTagEnum.*;

public class RyoPray extends BaseCard {
    public static final String ID = makeID(RyoPray.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 8;

    public RyoPray() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) {
            return false;
        }
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(KITA)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void triggerOnGlowCheck() {
        boolean glow = true;

        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hasTag(KITA)) {
                glow = false;
                break;
            }
        }

        if (glow) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }

    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoPray();
    }
}
