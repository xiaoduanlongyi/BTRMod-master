package btrmod.cards.power.rare;

import btrmod.actions.KitaGoneAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.KitaGonePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.KITA;

public class KitaGone extends BaseCard {
    public static final String ID = makeID(KitaGone.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int MAXHP_LOSS = 10;
    private static final int UPG_MAXHP_LOSS = 0;

    public KitaGone() {
        super(ID, info);

        setMagic(MAXHP_LOSS, UPG_MAXHP_LOSS);
        setCostUpgrade(1);
        setSelfRetain(true);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new KitaGoneAction(p, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new KitaGonePower(p, 1)));

        CardCrawlGame.sound.play("KitaGone");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaGone();
    }
}