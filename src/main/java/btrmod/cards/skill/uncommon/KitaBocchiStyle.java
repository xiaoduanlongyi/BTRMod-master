package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.DistortionPower;
import btrmod.util.CardStats;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;

import static btrmod.util.CardTagEnum.KITA;

public class KitaBocchiStyle extends BaseCard {
    public static final String ID = makeID(KitaBocchiStyle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.NONE,
            0
    );

    private static final int DIST = 1;
    private static final int UPG_DIST = 1;

    public KitaBocchiStyle() {
        super(ID, info);

        setEthereal(true, true);
        setMagic(DIST, UPG_DIST);

        tags.add(KITA);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int DistStacksToApply = getBocchiCount();
        addToBot(new ApplyPowerAction(p, p, new DistortionPower(p, DistStacksToApply)));
    }

    private int getBocchiCount(){
        // 计算手牌中BOCCHI卡的数量
        int bocchiCount = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hand != null) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card.hasTag(CardTagEnum.BOCCHI)) {
                    bocchiCount = bocchiCount + magicNumber;
                }
            }
        }
        return bocchiCount;
    }

    @Override
    public AbstractCard makeCopy() { //Optional
        return new KitaBocchiStyle();
    }
}