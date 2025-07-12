package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class RyoReturnMoney extends BaseCard {
    public static final String ID = makeID(RyoReturnMoney.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            1
    );

    private static final int BAP_REDUCE = 2;
    private static final int UPG_BAP_REDUCE = 0;
    private static final int MONEY = 30;
    private static final int UPG_MONEY = -10;

    public RyoReturnMoney() {
        super(ID, info);

        setMagic(BAP_REDUCE, UPG_BAP_REDUCE);
        setCustomVar("MONEY_RETURN", MONEY, UPG_MONEY);

        tags.add(RYO);
        tags.add(REDUCE_BAP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ReducePowerAction(p, p, BocchiAfraidPower.POWER_ID, magicNumber));

        final int toLose = customVar("MONEY_RETURN");
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                // this will correctly subtract gold (and floor at zero)
                AbstractDungeon.player.loseGold(toLose);
                isDone = true;
            }
        });
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoReturnMoney();
    }
}