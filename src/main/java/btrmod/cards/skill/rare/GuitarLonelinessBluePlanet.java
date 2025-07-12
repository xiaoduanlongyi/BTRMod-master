package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.cards.power.special.BluePlanet;
import btrmod.cards.power.special.Guitar;
import btrmod.cards.power.special.Loneliness;
import btrmod.character.KessokuBandChar;
import btrmod.powers.BocchiAfraidPower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.optionCards.BecomeAlmighty;
import com.megacrit.cardcrawl.cards.optionCards.FameAndFortune;
import com.megacrit.cardcrawl.cards.optionCards.LiveForever;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static btrmod.util.CardTagEnum.BOCCHI;

public class GuitarLonelinessBluePlanet extends BaseCard {
    public static final String ID = makeID(GuitarLonelinessBluePlanet.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );

    private static final int DIST = 2;
    private static final int UPG_DIST = 1;
    private static final int GROOVE = 7;
    private static final int UPG_GROOVE = 3;
    private static final int BAP_REDUCE = 2;
    private static final int UPG_BAP_REDUCE = 1;

    public GuitarLonelinessBluePlanet() {
        super(ID, info);

        setMagic(DIST, UPG_DIST);
        setCustomVar("GRV", GROOVE, UPG_GROOVE);
        setCustomVar("BAP", BAP_REDUCE, UPG_BAP_REDUCE);
        setExhaust(true);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Guitar());
        stanceChoices.add(new Loneliness());
        stanceChoices.add(new BluePlanet());
        if (this.upgraded) {
            for(AbstractCard c : stanceChoices) {
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));

        BgmManager.playCustomBGM("bgm/GuitarLonelinessBluePlanet.ogg");
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new GuitarLonelinessBluePlanet();
    }
}