package btrmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;

public class GiveMeMoneyAction extends AbstractGameAction {
    public int[] damage;

    public GiveMeMoneyAction(AbstractCreature source, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this.setValues((AbstractCreature)null, source, amount[0]);
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();

            for(int i = 0; i < temp; ++i) {
                if (!((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isDying && ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).currentHealth > 0 && !((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect, true));
                    } else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cX, ((AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i)).hb.cY, this.attackEffect));
                    }
                }
            }
        }

        this.tickDuration();
        if (this.isDone) {
            for(AbstractPower p : player.powers) {
                p.onDamageAllEnemies(this.damage);
            }

            int totalGold = 0;

            for(int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++i) {
                AbstractMonster target = (AbstractMonster)AbstractDungeon.getCurrRoom().monsters.monsters.get(i);
                if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                    target.damage(new DamageInfo(this.source, this.damage[i], this.damageType));
                    if (target.lastDamageTaken > 0) {
                        totalGold += target.lastDamageTaken;
                    }
                }
            }

            if (totalGold > 0) {
                if (!Settings.FAST_MODE) {
                    this.addToBot(new WaitAction(0.3F));
                }

                this.addToBot(new GainGoldAction(totalGold));
            }

            // 显示金币特效（限制数量避免卡顿）
            int effectCount = Math.min(totalGold, 50);
            for (int i = 0; i < effectCount; i++) {
                AbstractDungeon.effectList.add(
                        new GainPennyEffect(player, player.hb.cX, player.hb.cY,
                                player.hb.cX, player.hb.cY, true)
                );
            }

            // 大量金币时显示金币雨
            if (totalGold >= 40) {
                AbstractDungeon.effectsQueue.add(new RainingGoldEffect(totalGold * 2));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }

            this.addToTop(new WaitAction(0.1F));
        }

    }
}
