package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.SpotlightPlayerEffect;

import static btrmod.util.CardTagEnum.*;

public class KitaKira extends BaseCard {
    public static final String ID = makeID(KitaKira.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DEBUFF = 2;
    private static final int UPG_DEBUFF = 0;

    public KitaKira() {
        super(ID, info);

        setMagic(DEBUFF, UPG_DEBUFF);
        setCostUpgrade(0);
        setExhaust(true);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber));
        }
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, false), magicNumber));
        AbstractDungeon.effectList.add(new SpotlightEffect());
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaKira();
    }
}