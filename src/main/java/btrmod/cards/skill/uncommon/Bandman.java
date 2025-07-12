package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bandman extends BaseCard {
    public static final String ID = makeID(Bandman.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BLOCK = 1;
    private static final int UPG_BLOCK = 0;
    private static final int GROOVE_TO_DIVIDE = 1;
    private static final int UPG_GROOVE_TO_DIVIDE = 0;
    private static final int GROOVE_TO_REMOVE = 8;
    private static final int UPG_GROOVE_TO_REMOVE = -3;

    public Bandman() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK);
        setMagic(GROOVE_TO_REMOVE, UPG_GROOVE_TO_REMOVE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int blockToAdd = MathUtils.floor((float) getGrooveStacks() / 1);

        if (blockToAdd > 0) {
            addToBot(new GainBlockAction(p, blockToAdd));
            addToBot(new ReducePowerAction(p, p, GroovePower.POWER_ID, magicNumber));
        }

        CardCrawlGame.sound.play("BandMan");
    }

    private int getGrooveStacks()
    {
        int grooveStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            grooveStacks = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
        }

        return grooveStacks;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new Bandman();
    }
}
