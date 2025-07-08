package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiTwist extends BaseCard {
    public static final String ID = makeID(BocchiTwist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BAP = 1;
    private static final int UPG_BAP = 0;
    private static final int DISTOR = 2;
    private static final int UPG_DISTOR = 0;

    public BocchiTwist() {
        super(ID, info);

        setMagic(DISTOR,UPG_DISTOR);
        setCustomVar("BAP", BAP, UPG_BAP);
        setCostUpgrade(0);
        setExhaust(false);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BocchiAfraidPower(p, customVar("BAP"))));
        addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, magicNumber)));

        CardCrawlGame.sound.play("DistortedBocchi");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiTwist();
    }
}