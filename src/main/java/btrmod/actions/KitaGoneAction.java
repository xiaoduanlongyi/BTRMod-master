package btrmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/**
 * 自定义 Action：立即减少目标生物的最大生命值。
 * 如果目标当前生命值超过新的最大生命，则把当前生命值裁剪到新最大值。
 */
public class KitaGoneAction extends AbstractGameAction {
    private final int amount;

    /**
     * @param target 要减少最大生命的对象（如玩家）。传 null 会自动取 AbstractDungeon.player。
     * @param amount 要减少的最大生命值数量
     */
    public KitaGoneAction(AbstractCreature target, int amount) {
        this.target = target != null ? target : AbstractDungeon.player;
        this.amount = amount;
        this.actionType = ActionType.SPECIAL;  // “特殊”类型，不会触发伤害事件等
        this.duration = Settings.ACTION_DUR_FAST; // 快速执行
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.target != null) {
                // 调用 AbstractCreature.decreaseMaxHealth() 直接减少最大生命
                this.target.decreaseMaxHealth(amount);

                // 如果当前生命超过新的最大生命，就裁剪当前生命
                if (this.target.currentHealth > this.target.maxHealth) {
                    this.target.currentHealth = this.target.maxHealth;
                }

                // 刷新血条 UI
                if (this.target instanceof AbstractPlayer) {
                    ((AbstractPlayer) this.target).healthBarUpdatedEvent();
                } else {
                    this.target.healthBarUpdatedEvent();
                }
            }
        }
        tickDuration();
    }
}