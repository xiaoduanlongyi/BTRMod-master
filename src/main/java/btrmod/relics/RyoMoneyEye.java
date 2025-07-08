package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.SoloPowers.NijikaSoloPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.TrueVictoryRoom;

import static btrmod.BTRMod.makeID;

public class RyoMoneyEye extends BaseRelic {
    private static final String NAME = "RyoMoneyEye"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int GOLD = 15;

    public RyoMoneyEye() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onVictory() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new GainGoldAction(GOLD));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + GOLD + DESCRIPTIONS[1];
    }
}
