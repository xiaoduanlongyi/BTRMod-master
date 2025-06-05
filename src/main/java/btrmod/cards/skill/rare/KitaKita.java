package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.SoloPowers.KitaSoloPower;
import btrmod.util.CardStats;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.KITA;

public class KitaKita extends BaseCard {
    public static final String ID = makeID(KitaKita.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    public KitaKita() {
        super(ID, info);

        setExhaust(true);
        setCostUpgrade(2);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StunMonsterAction(m, p));
        addToBot(new ApplyPowerAction(p, p, new KitaSoloPower(p)));
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaKita();
    }
}