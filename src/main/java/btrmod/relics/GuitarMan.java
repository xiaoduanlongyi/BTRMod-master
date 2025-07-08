package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;

public class GuitarMan extends BaseRelic {
    private static final String NAME = "GuitarMan"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    private static final int GROOVE = 2;

    public GuitarMan() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();

        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new GroovePower(AbstractDungeon.player, GROOVE),
                        GROOVE
                )
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + GROOVE + DESCRIPTIONS[1];
    }
}
