package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.PubertySyndromePower;
import btrmod.powers.RyoThumbUpPower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.RYO;

public class PubertySyndrome extends BaseCard {
    public static final String ID = makeID(PubertySyndrome.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 4;

    public PubertySyndrome() {
        super(ID, info);

        setMagic(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PubertySyndromePower(p, magicNumber)));

        BgmManager.playCustomBGM("bgm/SeisyunComplex.ogg");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new PubertySyndrome();
    }
}
