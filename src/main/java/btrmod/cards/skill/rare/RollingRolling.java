package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RollingRolling extends BaseCard {
    public static final String ID = makeID(RollingRolling.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            1
    );

    private static final int BAP_TO_MULT = 2;
    private static final int UPG_BAP_TO_MULT = 0;
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 0;

    public RollingRolling() {
        super(ID, info);

        setMagic(BAP_TO_MULT, UPG_BAP_TO_MULT);
        setBlock(BLOCK, UPG_BLOCK);
        setExhaust(true);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int grooveToAdd = getBAPStacks() * magicNumber;
        int blockToAdd = getBAPStacks() * block;

        if (grooveToAdd > 0) {
            addToBot(new ApplyPowerAction(p, p, new GroovePower(p, grooveToAdd)));
        }
        if (blockToAdd > 0){
            addToBot(new GainBlockAction(p, blockToAdd));
        }

        BgmManager.playCustomBGM("bgm/RocknRollMorningLight.ogg");
    }

    private int getBAPStacks()
    {
        int BAPStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(BocchiAfraidPower.POWER_ID)) {
            BAPStacks = AbstractDungeon.player.getPower(BocchiAfraidPower.POWER_ID).amount;
        }

        return BAPStacks;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RollingRolling();
    }
}