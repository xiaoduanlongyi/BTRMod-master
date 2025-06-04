package btrmod.cards.skill.basic;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MatureMango extends BaseCard {
    public static final String ID = makeID(MatureMango.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public MatureMango() {
        super(ID, info);

        setBlock(BLOCK, UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.

        tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p,p,block));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new MatureMango();
    }
}