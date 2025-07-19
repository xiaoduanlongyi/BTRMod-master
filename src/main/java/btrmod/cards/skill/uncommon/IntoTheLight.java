package btrmod.cards.skill.uncommon;

import btrmod.actions.ChooseRandomSkillCardAction;
import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IntoTheLight extends BaseCard {
    public static final String ID = makeID(IntoTheLight.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    public IntoTheLight() {
        super(ID, info);

        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ChooseRandomSkillCardAction(this.upgraded));

        BgmManager.playCustomBGM("bgm/IntoTheLight.ogg");
    }

    @Override
    public AbstractCard makeCopy() {
        return new IntoTheLight();
    }
}