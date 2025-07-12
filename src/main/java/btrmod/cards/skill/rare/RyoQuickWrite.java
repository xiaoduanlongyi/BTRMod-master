package btrmod.cards.skill.rare;

import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.util.CardTagEnum.*;

public class RyoQuickWrite extends BaseCard {
    public static final String ID = makeID(RyoQuickWrite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int GROOVE = 5;
    private static final int UPG_GROOVE = -2;

    public RyoQuickWrite() {
        super(ID, info);

        setMagic(GROOVE, UPG_GROOVE);
        setExhaust(true);

        tags.add(RYO);
        tags.add(GROOVE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, GroovePower.POWER_ID, magicNumber));
        addToBot(new ExpertiseAction(p, 10));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoQuickWrite();
    }
}