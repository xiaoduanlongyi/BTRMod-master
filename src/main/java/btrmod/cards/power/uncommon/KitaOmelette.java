package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.RyoThumbUpPower;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import static btrmod.util.CardTagEnum.KITA;
import static btrmod.util.CardTagEnum.RYO;

public class KitaOmelette extends BaseCard {
    public static final String ID = makeID(KitaOmelette.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int REGEN = 4;
    private static final int UPG_REGEN = 1;

    public KitaOmelette() {
        super(ID, info);

        setMagic(REGEN, UPG_REGEN);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new KitaSoloPower(p)));

        CardCrawlGame.sound.play("KitaOmelette");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaOmelette();
    }
}
