package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.GROOVE_GRANT;

public class RockYou extends BaseCard {
    public static final String ID = makeID(RockYou.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int GROOVE = 1;
    private static final int UPG_GROOVE = 1;
    private static final int BOCCHI_AFRAID = 1;
    private static final int UPG_BOCCHI_AFRAID = 1;

    public RockYou() {
        super(ID, info);

        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setCustomVar("BAP", BOCCHI_AFRAID, UPG_BOCCHI_AFRAID);

        tags.add(BOCCHI);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, customVar("GRV"))));
        addToBot(new ReducePowerAction(p,p, BocchiAfraidPower.POWER_ID,customVar("BAP")));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RockYou();
    }
}