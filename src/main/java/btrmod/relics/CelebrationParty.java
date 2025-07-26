package btrmod.relics;

import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.BTRMod.makeID;

public class CelebrationParty extends BaseRelic {
    private static final String NAME = "CelebrationParty";
    public static final String ID = makeID(NAME);
    private static final RelicTier RARITY = RelicTier.BOSS;
    private static final LandingSound SOUND = LandingSound.HEAVY;

    private static final int GROOVE_THRESHOLD = 25;
    private static final int HEAL_AMOUNT = 8;

    private int grooveAtCombatEnd = 0;

    public CelebrationParty() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void atBattleStart() {
        // 战斗开始时重置记录的律动值
        grooveAtCombatEnd = 0;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        // 每当有怪物死亡时检查是否所有怪物都死了
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            // 在怪物全部死亡时（战斗即将结束）记录当前律动值
            if (AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
                GroovePower groovePower = (GroovePower) AbstractDungeon.player.getPower(GroovePower.POWER_ID);
                grooveAtCombatEnd = groovePower.amount;
            }
        }
    }

    @Override
    public void onVictory() {
        // 检查记录的律动值是否大于阈值
        if (grooveAtCombatEnd > GROOVE_THRESHOLD) {
            flash();

            AbstractPlayer p = AbstractDungeon.player;
            if (p.currentHealth > 0) {
                p.heal(6);
            }

            grooveAtCombatEnd = 0;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + GROOVE_THRESHOLD + DESCRIPTIONS[1] + HEAL_AMOUNT + DESCRIPTIONS[2];
    }
}