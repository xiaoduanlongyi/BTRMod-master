package btrmod.cards.power.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiOmelettePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoxiousFumesPower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiOmelette extends BaseCard {
    public static final String ID = makeID(BocchiOmelette.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            2
    );

    private static final int POISON = 1;  // 每层波奇自闭施加的中毒层数
    private static final int UPG_POISON = 0;
    private static final int BAP = 1;
    private static final int UPG_BAP = 0;

    public BocchiOmelette() {
        super(ID, info);

        setMagic(POISON, UPG_POISON);
        setCustomVar("BAP", BAP, UPG_BAP);
        setCostUpgrade(1);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BocchiOmelettePower(p, magicNumber)));

        CardCrawlGame.sound.play("BocchiOmelette");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiOmelette();
    }
}
