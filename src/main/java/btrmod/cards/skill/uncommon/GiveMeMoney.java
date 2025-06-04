package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.cards.attack.EatGrass;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class GiveMeMoney extends BaseCard {
    public static final String ID = makeID(GiveMeMoney.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int MONEY_GAINED = 15;
    private static final int UPG_MONEY_GAINED = 10;

    public GiveMeMoney() {
        super(ID, info);

        setMagic(MONEY_GAINED,UPG_MONEY_GAINED);
        this.cardsToPreview = new EatGrass();
        setExhaust(true,true);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainGoldAction(magicNumber));
        this.addToBot(new MakeTempCardInDrawPileAction(new EatGrass(), 3, true, true));
        addToBot(new ApplyPowerAction(p, p, new BocchiAfraidPower(p, 1)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GiveMeMoney();
    }
}
