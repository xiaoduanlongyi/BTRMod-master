package btrmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static btrmod.BTRMod.makeID;

public class BocchiOmelettePower extends BasePower {
    public static final String POWER_ID = makeID(BocchiOmelettePower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public BocchiOmelettePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void atStartOfTurnPostDraw() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            // 获取波奇自闭层数
            int BAP = 0;
            if (owner.hasPower(BocchiAfraidPower.POWER_ID)) {
                BAP = owner.getPower(BocchiAfraidPower.POWER_ID).amount;
            }

            // 如果有波奇自闭层数，对所有敌人施加中毒
            if (BAP > 0) {
                this.flash();

                int poisonAmount = this.amount * BAP; // 每层波奇自闭施加amount层中毒

                for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (!m.isDead && !m.isDying) {
                        this.addToBot(new ApplyPowerAction(m, this.owner, new PoisonPower(m, this.owner, poisonAmount), poisonAmount));
                    }
                }
            }
        }
    }

//    public void atEndOfTurn(boolean isPlayer) {
//        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
//            this.flash();
//            addToTop(new ApplyPowerAction(owner, owner, new BocchiAfraidPower(owner, 1)));
//        }
//    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
