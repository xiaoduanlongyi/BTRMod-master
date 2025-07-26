package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;

public class ExtrovertedBocchi extends BaseRelic {
    private static final String NAME = "ExtrovertedBocchi"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int AnxietyLevel = 3; //For convenience of changing it later and clearly knowing what the number means instead of just having a 10 sitting around in the code.

    public ExtrovertedBocchi() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void obtain() {
        // 替换 BocchiStartRelic
        if (AbstractDungeon.player.hasRelic(BocchiTheRockRelic.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BocchiTheRockRelic.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

//    @Override
//    public void atBattleStartPreDraw() {
//        flash();
//        AbstractDungeon.actionManager.addToTop(
//                new ApplyPowerAction(
//                        AbstractDungeon.player,
//                        AbstractDungeon.player,
//                        new BocchiAfraidPower(AbstractDungeon.player, AnxietyLevel),
//                        AnxietyLevel
//                )
//        );
//    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BocchiTheRockRelic.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }
}
