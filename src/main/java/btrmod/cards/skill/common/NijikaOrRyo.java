package btrmod.cards.skill.common;

import btrmod.cards.BaseCard;
import btrmod.cards.power.special.ToNijikaSolo;
import btrmod.cards.power.special.ToRyoSolo;
import btrmod.character.KessokuBandChar;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class NijikaOrRyo extends BaseCard {
    public static final String ID = makeID(NijikaOrRyo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.NONE,
            0
    );

    public NijikaOrRyo() {
        super(ID, info);

        setSelfRetain(false,true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new ToNijikaSolo());
        stanceChoices.add(new ToRyoSolo());

        addToBot(new ChooseOneAction(stanceChoices));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new NijikaOrRyo();
    }
}