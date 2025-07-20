package btrmod.powers;

import btrmod.powers.BocchiAfraidPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static btrmod.BTRMod.makeID;

public class WormBocchiPower extends BasePower {
    public static final String POWER_ID = makeID(WormBocchiPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    private boolean triggeredThisCombat = false;

    public WormBocchiPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        updateDescription();
    }

    public boolean isTriggered() {
        return triggeredThisCombat;
    }

    @Override
    public void updateDescription() {
        if (triggeredThisCombat) {
            description = DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
    }

    @Override
    public void onSpecificTrigger() {
        // Check if Bocchi's Anxiety has reached or exceeded 10
        if (!triggeredThisCombat && owner.hasPower(BocchiAfraidPower.POWER_ID)) {
            int anxietyLevel = owner.getPower(BocchiAfraidPower.POWER_ID).amount;

            if (anxietyLevel >= 10) {
                triggeredThisCombat = true;
                flash();
                addToBot(new ApplyPowerAction(owner, owner, new IntangiblePlayerPower(owner, amount), amount));
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
    }

    @Override
    public void atEndOfRound() {
        // Check anxiety level at the end of each round
        onSpecificTrigger();
    }
}