package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.*;

public class RyoQuickWrite extends BaseCard {
    public static final String ID = makeID(RyoQuickWrite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            0
    );

    private static final int GROOVE = 0;
    private static final int UPG_GROOVE = 1;

    public RyoQuickWrite() {
        super(ID, info);

        setMagic(GROOVE, UPG_GROOVE);
        setExhaust(true);

        tags.add(RYO);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ExpertiseAction(p, 10));
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new GroovePower(p, magicNumber), magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new RyoQuickWrite();
    }
}