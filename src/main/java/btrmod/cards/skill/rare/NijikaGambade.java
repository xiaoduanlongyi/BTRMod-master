package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static btrmod.util.CardTagEnum.NIJIKA;

public class NijikaGambade extends BaseCard {
    public static final String ID = makeID(NijikaGambade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1
    );


    public NijikaGambade() {
        super(ID, info);

        setExhaust(true, false);

        tags.add(NIJIKA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new GroovePower(p, getGrooveToAdd())));
        addToBot(new ApplyPowerAction(p, p, new NijikaSoloPower(p)));
    }

    private int getGrooveToAdd() {
        int groove = 0;
        if (AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            groove = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
        }
        return groove;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaGambade();
    }
}