package btrmod.cards.attack.common;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static btrmod.util.CardTagEnum.BOCCHI;

public class JawPoke extends BaseCard {
    public static final String ID = makeID(JawPoke.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int HITTIME = 3;
    private static final int UPG_HITTIME = 0;

    public JawPoke() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(HITTIME, UPG_HITTIME);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < this.magicNumber; ++i) {
            // 1) 随机选一个存活的怪
            AbstractMonster target = AbstractDungeon.getMonsters()
                    .getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (target == null) continue;

            // 2) 对选中的怪造成一次伤害
            addToBot(new com.megacrit.cardcrawl.actions.common.DamageAction(
                    target,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
            ));

            // 3) 再对同一个怪施加 1 层 Vulnerable
            addToBot(new ApplyPowerAction(
                    target,
                    p,
                    new VulnerablePower(target, 1, false),
                    1
            ));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new JawPoke();
    }
}
