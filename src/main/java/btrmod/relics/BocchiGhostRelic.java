package btrmod.relics;

import btrmod.cards.skill.uncommon.BocchiGhost;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.DistortionPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static btrmod.BTRMod.makeID;

public class BocchiGhostRelic extends BaseRelic implements OnReceivePowerRelic {
    private static final String NAME = "BocchiGhostRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private static final int INTANGIBLE_AMOUNT = 1;

    public BocchiGhostRelic() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target) {
        // 检查是否是玩家获得扭曲
        if (power instanceof DistortionPower && target == AbstractDungeon.player && power.amount > 0) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            // 给予1层无实体
            addToBot(new ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    new IntangiblePlayerPower(AbstractDungeon.player, INTANGIBLE_AMOUNT),
                    INTANGIBLE_AMOUNT
            ));
        }
        return true;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + INTANGIBLE_AMOUNT + DESCRIPTIONS[1];
    }
}
