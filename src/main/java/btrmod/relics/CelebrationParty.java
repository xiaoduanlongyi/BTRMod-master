package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static btrmod.BTRMod.makeID;

public class CelebrationParty extends BaseRelic {
    private static final String NAME = "CelebrationParty";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    private static final int GROOVE_THRESHOLD = 25;
    private static final int HEAL_AMOUNT = 6;

    public CelebrationParty() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void onVictory() {
        // 检查玩家是否有律动能力
        if (AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            GroovePower groovePower = (GroovePower) AbstractDungeon.player.getPower(GroovePower.POWER_ID);

            // 检查律动是否大于25
            if (groovePower.amount > GROOVE_THRESHOLD) {
                // 触发遗物效果
                flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

                // 回复生命
                addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + GROOVE_THRESHOLD + DESCRIPTIONS[1] + HEAL_AMOUNT + DESCRIPTIONS[2];
    }
}