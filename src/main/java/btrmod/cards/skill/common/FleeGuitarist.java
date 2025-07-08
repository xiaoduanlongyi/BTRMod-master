package btrmod.cards.skill.common;

import btrmod.actions.FleeGuitaristAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.KITA;

public class FleeGuitarist extends BaseCard {
    public static final String ID = makeID(FleeGuitarist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 0;
    private static final int BONUS_ENERGY = 1;
    private static final int UPG_BONUS_ENERGY = 1;

    public FleeGuitarist() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(BONUS_ENERGY, UPG_BONUS_ENERGY);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FleeGuitaristAction(p, block, magicNumber));

        CardCrawlGame.sound.playV("EscapedGuitar", 1.5f);
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FleeGuitarist();
    }
}