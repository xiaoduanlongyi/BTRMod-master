package btrmod.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RyoEatEatAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int maxHPIncrease;

    public RyoEatEatAction(AbstractCreature source, int damage, int maxHPIncrease) {
        this.source = source;
        this.info = new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL);
        this.maxHPIncrease = maxHPIncrease;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m.currentHealth > 0 && !m.isDead && !m.halfDead) {
                    // flash
                    AbstractDungeon.actionManager.addToTop(
                            new VFXAction(new FlashAtkImgEffect(
                                    m.hb.cX, m.hb.cY, AttackEffect.NONE), 0.0F));
                    // deal damage
                    m.damage(this.info);
                    // if killed, buff max HP
                    if ((m.isDying || m.currentHealth <= 0)
                            && !m.halfDead
                            && !m.hasPower("Minion")) {
                        AbstractDungeon.player.increaseMaxHp(
                                this.maxHPIncrease, false);
                    }
                }
            }

            // if all dead, clear
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}