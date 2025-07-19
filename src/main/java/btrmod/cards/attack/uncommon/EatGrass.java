package btrmod.cards.attack.uncommon;

import btrmod.BTRMod;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static btrmod.util.CardTagEnum.KITA;
import static btrmod.util.CardTagEnum.RYO;

public class EatGrass extends BaseCard {
    public static final String ID = makeID(EatGrass.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 0;
    private static final int GOLD_THRESHOLD = 150;
    private static final int UPG_GOLD_THRESHOLD = 75;

    public EatGrass() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(GOLD_THRESHOLD, UPG_GOLD_THRESHOLD);
        setExhaust(true);

        tags.add(RYO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        // 如果金币少于Threshold，再打出一次这张牌
        // 只有当这张牌不是“自动复制”出来的（dontTriggerOnUseCard == false）并且金币少于阈值时，才再打出一次
        if (!this.dontTriggerOnUseCard && p.gold < magicNumber) {
            AbstractCard tmp = this.makeStatEquivalentCopy();
            tmp.purgeOnUse = true;
            // 标记这是“自动触发”用的副本，use() 里的再触发逻辑会被跳过
            tmp.dontTriggerOnUseCard = true;
            addToBot(new NewQueueCardAction(tmp, m, false, true));
        }

        CardCrawlGame.sound.play("RyoEatGrass");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new EatGrass();
    }
}
