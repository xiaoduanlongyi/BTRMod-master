package btrmod.actions;

import btrmod.powers.BocchiAfraidPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class PinkBocchiAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int damage;
    private final AbstractPlayer p;
    private final DamageInfo.DamageType damageTypeForTurn;
    private final int energyOnUse;
    private final int BAPThreshold;

    public PinkBocchiAction(AbstractPlayer p, int damage, DamageInfo.DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, int BAPThreshold) {
        this.p = p;
        this.damage = damage;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
        this.energyOnUse = energyOnUse;
        this.BAPThreshold = BAPThreshold;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (this.p.getPower(BocchiAfraidPower.POWER_ID) == null)
            effect += 1;

        if (this.p.getPower(BocchiAfraidPower.POWER_ID) != null)
            if (this.p.getPower(BocchiAfraidPower.POWER_ID).amount < BAPThreshold) {
                effect += 1;
        }

        if (effect > 0) {
            int[] allDmg = p.cardInUse.multiDamage;
            for(int i = 0; i < effect; ++i) {
                this.addToBot(new DamageAllEnemiesAction(p, allDmg, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
