package btrmod.cards.power.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.WormBocchiPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class WormBocchi extends BaseCard {
    public static final String ID = makeID(WormBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            3
    );

    public WormBocchi() {
        super(ID, info);

        setCostUpgrade(2);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        {
            int IntangileStacksToAdd = getBAPStacks();

            addToBot(new ApplyPowerAction(p, p, new WormBocchiPower(p)));
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, IntangileStacksToAdd)));
        }
    }

    private int getBAPStacks()
    {
        int BAPStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BocchiAfraidPower.POWER_ID)) {
            BAPStacks = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        return BAPStacks;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new WormBocchi();
    }
}
