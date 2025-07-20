package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiGhost extends BaseCard {
    public static final String ID = makeID(BocchiGhost.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int INTANGIBLE = 2;
    private static final int UPG_INTANGIBLE = 0;
    private static final int BOCCHI_AFRAID = 2;
    private static final int UPG_BOCCHI_AFRAID = -1;

    public BocchiGhost() {
        super(ID, info);

        setMagic(INTANGIBLE, UPG_INTANGIBLE);
        setCustomVar("BAP", BOCCHI_AFRAID, UPG_BOCCHI_AFRAID);
        setExhaust(true);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p,magicNumber)));
        addToBot(new ApplyPowerAction(p,p, new BocchiAfraidPower(p,customVar("BAP"))));

        CardCrawlGame.sound.play("BocchiGhost");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiGhost();
    }
}