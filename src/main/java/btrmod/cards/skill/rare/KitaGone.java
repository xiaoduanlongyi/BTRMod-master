package btrmod.cards.skill.rare;

import basemod.devcommands.maxhp.MaxHp;
import btrmod.actions.KitaGoneAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.trials.LoseMaxHpTrial;

import static btrmod.util.CardTagEnum.KITA;

public class KitaGone extends BaseCard {
    public static final String ID = makeID(KitaGone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int MAXHP_LOSS = 3;
    private static final int UPG_MAXHP_LOSS = 0;
    private static final int GROOVE = 8;
    private static final int UPG_GROOVE = 3;
    private static final int STRENGTH = 5;
    private static final int UPG_STRENGTH = 2;

    public KitaGone() {
        super(ID, info);

        setMagic(MAXHP_LOSS, UPG_MAXHP_LOSS);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setCustomVar("STR", STRENGTH, UPG_STRENGTH);

        setExhaust(true);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new KitaGoneAction(p, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, customVar("STR"))));
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaGone();
    }
}