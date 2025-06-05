package btrmod.powers;

import btrmod.BTRMod;
import btrmod.util.CardTagEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

/**
 * ComboCounterPower 用来实时展示“玩家当前连续打出了多少张同角色 Tag 卡 (X/3)”
 * 其中 DESCRIPTIONS[0] 应包含一个 %s 占位符，用来填入角色名（Bocchi / Kita / Nijika / Ryo）。
 * DESCRIPTIONS[1] 就是 "/3" 这个固定后缀。
 */
public class ComboCounterPower extends BasePower {
    public static final String POWER_ID = BTRMod.makeID("ComboCounterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private AbstractCard.CardTags trackedTag;
    private static final int MAX_COMBO = 3;

    /**
     * 构造器
     * @param owner       持有此 Power 的生物 (一般就是玩家)
     * @param tag         当前正在叠连击标签的角色 (CardTagEnum.BOCCHI / KITA / NIJIKA / RYO)
     * @param initialCount 初始连击数 (通常是 1 或 2)
     */
    public ComboCounterPower(AbstractCreature owner, AbstractCard.CardTags tag, int initialCount) {
        super(POWER_ID, PowerType.BUFF, false, owner, null, initialCount);

        this.trackedTag = tag;
        updateDescription();
    }

    /**
     * 当需要在连击过程中更新“角色”或“已连击张数”时，可以调用此方法，
     * 它会把 trackedTag 和 amount 都替换掉，然后刷新描述文本。
     *
     * @param tag   要显示的角色标签 (CardTagEnum.BOCCHI 等)
     * @param count 当前已连续打出的同角色标签的张数 (1, 2)；最大到 2 时依然显示 "2/3"
     */
    public void setCounter(AbstractCard.CardTags tag, int count) {
        this.trackedTag = tag;
        this.amount = count;
        updateDescription();
    }

    /**
     * 重写 AbstractPower.updateDescription()，让描述显示：
     *   “当前已连续打出 Bocchi 卡：X/3”
     * 其中 %s 由 trackedTag.name() 动态替换为 “Bocchi” 等。
     */
    @Override
    public void updateDescription() {
        String tagName = getTagDisplayName(trackedTag);
        this.description = String.format(powerStrings.DESCRIPTIONS[0], tagName)
                + this.amount
                + powerStrings.DESCRIPTIONS[1];
    }

    private String getTagDisplayName(AbstractCard.CardTags tag) {
        if (tag == CardTagEnum.BOCCHI) return "Bocchi";
        if (tag == CardTagEnum.KITA) return "Kita";
        if (tag == CardTagEnum.NIJIKA) return "Nijika";
        if (tag == CardTagEnum.RYO) return "Ryo";
        return "Unknown";
    }
}
