package btrmod.cards.skill.common;

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

public class OctopusBocchi extends BaseCard {
    public static final String ID = makeID(OctopusBocchi.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 0;

    public OctopusBocchi() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(WEAK, UPG_WEAK);
        setExhaust(true);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        if (AbstractDungeon.player.currentBlock > 5) {
            addToBot(new GainBlockAction(p, -block));
        }
        else{
            addToBot(new RemoveAllBlockAction(p, p));
        }

        for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(mo, p, new WeakPower(mo, magicNumber, false), magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new OctopusBocchi();
    }
}