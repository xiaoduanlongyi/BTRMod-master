package btrmod.cards.skill.basic;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.SoloPowers.BocchiSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import static btrmod.util.CardTagEnum.BOCCHI;

public class ProudBocchi extends BaseCard {
    public static final String ID = makeID(ProudBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;
    private static final int BAP = 1;
    private static final int UPG_BAP = 0;

    private static final CardStrings STRINGS =
            CardCrawlGame.languagePack.getCardStrings(ID);

    public ProudBocchi() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(BAP, UPG_BAP);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,block));
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new ProudBocchi();
    }
}