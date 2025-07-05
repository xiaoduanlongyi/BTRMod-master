package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
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

    private static final int GROOVE_TO_DIVIDE = 10;
    private static final int UPG_GROOVE_TO_DIVIDE = -3;

    public NijikaGambade() {
        super(ID, info);

        setMagic(GROOVE_TO_DIVIDE, UPG_GROOVE_TO_DIVIDE);

        tags.add(NIJIKA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int energyToAdd = MathUtils.floor((float) getGrooveStacks() / magicNumber);

        if (energyToAdd > 0) {
            addToBot(new GainEnergyAction(energyToAdd));
        }

        addToBot(new ApplyPowerAction(p, p, new NijikaSoloPower(p)));

        CardCrawlGame.sound.play("NijikaGambade");
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
        return new NijikaGambade();
    }
}