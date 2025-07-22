package btrmod.cards.attack.uncommon;

import btrmod.actions.BocchiQuickDrawAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.BOCCHI;

public class BocchiQuickDraw extends BaseCard {
    public static final String ID = makeID(BocchiQuickDraw.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 0;
    private static final int DRAW = 3;
    private static final int UPG_DRAW = 1;

    public BocchiQuickDraw() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(DRAW, UPG_DRAW);

        tags.add(BOCCHI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new DrawCardAction(this.magicNumber, new BocchiQuickDrawAction()));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiQuickDraw();
    }
}
