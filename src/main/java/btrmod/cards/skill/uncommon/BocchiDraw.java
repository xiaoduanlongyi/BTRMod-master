package btrmod.cards.skill.uncommon;

import btrmod.BTRMod;
import btrmod.actions.DrawAllBocchiCardsAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class BocchiDraw extends BaseCard {
    public static final String ID = makeID(BocchiDraw.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int GROOVE = 5;
    private static final int UPG_GROOVE = -2;

    public BocchiDraw() {
        super(ID, info);

        setMagic(GROOVE, UPG_GROOVE);
        setExhaust(true);

        tags.add(BOCCHI);
        tags.add(GROOVE_EXHAUST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawAllBocchiCardsAction());
        addToBot(new ReducePowerAction(p, p, GroovePower.POWER_ID, magicNumber));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        // 先调用父类的检查（比如能量不足、不可选时等）
        if (!super.canUse(p, m)) {
            return false;
        }
        // 然后检查 GroovePower
        if (!p.hasPower(GroovePower.POWER_ID)
                || p.getPower(GroovePower.POWER_ID).amount < this.magicNumber) {
            // 如果玩家没有 Groove 或者层数 < magicNumber，就禁止打出
            this.cantUseMessage = CardCrawlGame.languagePack.getUIString(BTRMod.makeID("cantUseMessage")).TEXT[0];
            return false;
        }
        return true;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new BocchiDraw();
    }
}