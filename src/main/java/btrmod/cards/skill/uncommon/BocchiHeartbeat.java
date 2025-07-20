package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiHeartbeat extends BaseCard {
    public static final String ID = makeID(BocchiHeartbeat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DRAW = 1;
    private static final int UPG_DRAW = 0;
    private static final int ADD_DRAW = 2;
    private static final int UPG_ADD_DRAW = 0;
    private static final int GROOVE = 20;
    private static final int UPG_GROOVE = -5;

    public BocchiHeartbeat() {
        super(ID, info);

        setMagic(DRAW,UPG_DRAW);
        setCustomVar("MORE_DRAW", ADD_DRAW, UPG_ADD_DRAW);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setExhaust(true);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int currentGrooveStacks = 0;
        if (p.hasPower((GroovePower.POWER_ID))) {
            currentGrooveStacks = p.getPower(GroovePower.POWER_ID).amount;
        }

        int totalDraw = magicNumber;
        if (currentGrooveStacks > customVar("GRV")) {
            totalDraw += customVar("MORE_DRAW");
        }

        if (totalDraw > 0 && currentGrooveStacks > 0) {
            addToBot(new DrawCardAction(totalDraw));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiHeartbeat();
    }
}