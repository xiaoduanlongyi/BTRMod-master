package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;

import static btrmod.BTRMod.makeID;

public class KitaGonePower extends BasePower {
    public static final String POWER_ID = makeID(KitaGonePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public KitaGonePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.priority = 999; // 高优先级，确保在其他死亡相关效果之前触发
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        // 如果有印记之花或层数为0，则不触发
        if (this.amount <= 0 || owner.hasPower("Minion") ||
                AbstractDungeon.player.hasRelic(MarkOfTheBloom.ID)) {
            return damageAmount;
        }

        // 检查这次伤害是否会致死
        if (damageAmount >= owner.currentHealth) {
            // 触发复活
            this.flash();

            // 减少伤害到刚好剩1血
            int reducedDamage = owner.currentHealth - 1;

            // 在下一帧恢复到50%血量
            addToTop(new HealAction(owner, owner, owner.maxHealth / 2));

            // 播放音效
            CardCrawlGame.sound.play("HEAL_1");

            // 减少一层
            addToTop(new ReducePowerAction(owner, owner, this, 1));

            return reducedDamage;
        }

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }
}