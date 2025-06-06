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
            0
    );

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;
    private static final int BONUS_BLOCK = 9;
    private static final int UPG_BONUS_BLOCK = 5;

    public FleeGuitarist() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(BONUS_BLOCK, UPG_BONUS_BLOCK);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FleeGuitaristAction(p, block, magicNumber));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new FleeGuitarist();
    }
}