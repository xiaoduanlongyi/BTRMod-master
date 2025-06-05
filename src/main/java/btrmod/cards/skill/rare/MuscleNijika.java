package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
            3
    );

    private static final int GROOVE_TO_DIVIDE = 5;
    private static final int UPG_GROOVE_TO_DIVIDE = -2;

    public MuscleNijika() {
        super(ID, info);

        setMagic(GROOVE_TO_DIVIDE, UPG_GROOVE_TO_DIVIDE);
        setExhaust(true);

        tags.add(NIJIKA);
        tags.add(GROOVE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int strengthStacksToAdd = MathUtils.floor((float) getGrooveStacks() / magicNumber);

        if (p.hasPower(GroovePower.POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(p, p, GroovePower.POWER_ID));
        }
        if (strengthStacksToAdd > 0) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, strengthStacksToAdd), strengthStacksToAdd));
        }
    }

    private int getGrooveStacks()
    {
        int grooveStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            grooveStacks = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
        }

        return grooveStacks;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MuscleNijika();
    }
}