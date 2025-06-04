package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;

public class BocchiStartRelic extends BaseRelic {
    private static final String NAME = "BocchiStartRelic"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int AnxietyLevel = 8; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public BocchiStartRelic() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);
    }

    @Override
    public void atBattleStartPreDraw() {
        flash();  // visual flash on equip
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new BocchiAfraidPower(AbstractDungeon.player, AnxietyLevel),
                        AnxietyLevel
                )
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AnxietyLevel + DESCRIPTIONS[1];
    }
}
