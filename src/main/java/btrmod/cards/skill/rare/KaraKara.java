package btrmod.cards.skill.rare;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.powers.SoloPowers.RyoSoloPower;
import btrmod.util.BgmManager;
import btrmod.util.CardStats;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static btrmod.util.CardTagEnum.GROOVE_GRANT;
import static btrmod.util.CardTagEnum.RYO;

public class KaraKara extends BaseCard {
    public static final String ID = makeID(KaraKara.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            2
    );


    public KaraKara() {
        super(ID, info);

        setExhaust(true);
        setCostUpgrade(1);

        tags.add(RYO);
        tags.add(GROOVE_GRANT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        int grooveToAdd = getGrooveStacks();

        if (grooveToAdd > 0) {
            addToBot(new ApplyPowerAction(p, p, new GroovePower(p, grooveToAdd)));
        }
        addToBot(new ApplyPowerAction(p, p, new RyoSoloPower(p)));

        BgmManager.playCustomBGM("bgm/KaraKara.ogg");
    }

    private int getGrooveStacks()
    {
        int grooveStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            grooveStacks = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
        }

        return grooveStacks;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KaraKara();
    }
}