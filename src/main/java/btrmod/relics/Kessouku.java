package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;

public class Kessouku extends BaseRelic {
    private static final String NAME = "Kessouku"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int GROOVE_LEVEL = 10;

    public Kessouku() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();  // visual flash on equip
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new GroovePower(AbstractDungeon.player, GROOVE_LEVEL)
                )
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + GROOVE_LEVEL + DESCRIPTIONS[1];
    }
}
