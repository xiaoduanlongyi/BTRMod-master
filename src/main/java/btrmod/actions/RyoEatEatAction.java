package btrmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RyoEatEatAction extends AbstractGameAction {
    private final int[] multiDamage;
    private final int maxHPIncrease;

    public RyoEatEatAction(AbstractCreature source, int[] multiDamage, int maxHPIncrease) {
        this.source = source;
        this.multiDamage = multiDamage;
        this.maxHPIncrease = maxHPIncrease;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            boolean[] wasAlive = new boolean[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
            for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
                AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                wasAlive[i] = !m.isDead && !m.isDying && m.currentHealth > 0;
            }

            // deal damage
            addToTop(
                    new DamageAllEnemiesAction(source, multiDamage,
                            damageType, AttackEffect.NONE, false) {

                        @Override
                        public void update() {
                            super.update();

                            // 在伤害处理完成后检查是否击杀
                            if (this.isDone) {
                                for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
                                    AbstractMonster m = AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                                    // 如果怪物之前活着，现在死了或即将死亡，且不是仆从
                                    if (wasAlive[i] && (m.isDying || m.currentHealth <= 0)
                                            && !m.halfDead && !m.hasPower("Minion")) {
                                        AbstractDungeon.player.increaseMaxHp(maxHPIncrease, false);
                                    }
                                }
                            }
                        }
                    });


            // if all dead, clear
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}