package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;
import static btrmod.util.CardTagEnum.GROOVE_GRANT;

public class KikuriWave extends BaseCard {
    public static final String ID = makeID(KikuriWave.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int BAP = 1;
    private static final int UPG_BAP = 0;
    private static final int GROOVE = 2;
    private static final int UPG_GROOVE = 0;

    public KikuriWave() {
        super(ID, info);

        setMagic(BAP, UPG_BAP);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setInnate(false, true);
        setExhaust(true);

        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 计算活着的敌人数量
        int aliveEnemies = 0;
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDead && !monster.isDying && !monster.isEscaping) {
                aliveEnemies++;
            }
        }

        // 根据敌人数量减少波奇自闭和增加律动
        if (aliveEnemies > 0 && p.hasPower(BocchiAfraidPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new GroovePower(p, aliveEnemies * customVar("GRV"))));
            addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, aliveEnemies * magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KikuriWave();
    }
}