package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static btrmod.util.CardTagEnum.*;

public class MuscleNijika extends BaseCard {
    public static final String ID = makeID(MuscleNijika.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    public MuscleNijika() {
        super(ID, info);

        setExhaust(true,false);

        tags.add(NIJIKA);
        tags.add(GROOVE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int rawStacks = 0;
        if (p.hasPower(GroovePower.POWER_ID)) {
            rawStacks = p.getPower(GroovePower.POWER_ID).amount;
            // 用 RemoveSpecificPowerAction 一次性移除
            addToBot(new RemoveSpecificPowerAction(p, p, GroovePower.POWER_ID));
        }

        if (rawStacks > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, rawStacks), rawStacks));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MuscleNijika();
    }
}