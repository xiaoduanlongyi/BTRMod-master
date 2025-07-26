package btrmod.cards.skill.uncommon;

import btrmod.cards.BaseCard;
import btrmod.character.KessokuBandChar;
import btrmod.powers.GroovePower;
import btrmod.util.CardStats;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Bandman extends BaseCard {
    public static final String ID = makeID(Bandman.class.getSimpleName());
    private static final CardStats info = new CardStats(
            KessokuBandChar.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK_PER_CARD = 4;
    private static final int UPG_BLOCK_PER_CARD = 1;
    private static final int EXTRA_BLOCK_PER_CARD = 2;
    private static final int UPG_EXTRA_BLOCK_PER_CARD = 1;
    private static final int GROOVE_THRESHOLD = 35;
    private static final int UPG_GROOVE_THRESHOLD = 0;

    public Bandman() {
        super(ID, info);

        setBlock(BLOCK_PER_CARD, UPG_BLOCK_PER_CARD);
        setMagic(EXTRA_BLOCK_PER_CARD, UPG_EXTRA_BLOCK_PER_CARD);
        setCustomVar("GRV", GROOVE_THRESHOLD, UPG_GROOVE_THRESHOLD);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 计算手牌中带有GROOVE_GRANT标签的牌数量
        int grooveGrantCards = 0;
        for (AbstractCard c : p.hand.group) {
            if (c.hasTag(CardTagEnum.GROOVE_GRANT)) {
                grooveGrantCards++;
            }
        }

        // 计算基础格挡值
        int blockAmount = grooveGrantCards * block;

        // 如果当前律动大于35，每张牌额外获得格挡
        int currentGroove = getGrooveStacks();
        if (currentGroove > customVar("GRV")) {
            blockAmount += grooveGrantCards * magicNumber;
        }

        // 获得格挡
        if (blockAmount > 0) {
            addToBot(new GainBlockAction(p, p, blockAmount));
        }

        CardCrawlGame.sound.play("BandMan");
    }

    private int getGrooveStacks() {
        int grooveStacks = 0;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(GroovePower.POWER_ID)) {
            grooveStacks = AbstractDungeon.player.getPower(GroovePower.POWER_ID).amount;
        }
        return grooveStacks;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        // 动态更新描述，显示当前会获得多少格挡
        int grooveGrantCards = 0;
        if (AbstractDungeon.player != null) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.hasTag(CardTagEnum.GROOVE_GRANT)) {
                    grooveGrantCards++;
                }
            }
        }

        int blockAmount = grooveGrantCards * block;
        int currentGroove = getGrooveStacks();
        if (currentGroove > customVar("GRV")) {
            blockAmount += grooveGrantCards * magicNumber;
        }

        // 如果有额外伤害，标记为已修改并更新描述
        if (grooveGrantCards > 0) {
            this.isDamageModified = true;

            // 更新描述显示总伤害
            this.rawDescription = cardStrings.DESCRIPTION;
            if (cardStrings.EXTENDED_DESCRIPTION != null && cardStrings.EXTENDED_DESCRIPTION.length > 0) {
                this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + blockAmount + cardStrings.EXTENDED_DESCRIPTION[1];
            }
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bandman();
    }
}