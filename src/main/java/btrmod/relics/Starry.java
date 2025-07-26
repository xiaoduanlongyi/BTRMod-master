package btrmod.relics;

import btrmod.character.KessokuBandChar;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.ArrayList;
import java.util.Collections;

import static btrmod.BTRMod.makeID;

public class Starry extends BaseRelic {
    private static final String NAME = "Starry"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    public Starry() {
        super(ID, NAME, KessokuBandChar.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public void justEnteredRoom(AbstractRoom room) {
        // 使用 justEnteredRoom 而不是 onEnterRoom
        if (room instanceof ShopRoom) {
            // 直接执行升级逻辑，不使用 action
            ArrayList<AbstractCard> upgradableCards = new ArrayList<>();

            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    upgradableCards.add(c);
                }
            }

            if (!upgradableCards.isEmpty()) {
                // 闪光效果
                flash();

                // 打乱可升级卡牌列表
                Collections.shuffle(upgradableCards, AbstractDungeon.miscRng.random);

                // 确定要升级的卡牌数量（最多2张，但不超过可升级卡牌的数量）
                int cardsToUpgrade = Math.min(2, upgradableCards.size());

                // 升级卡牌
                for (int i = 0; i < cardsToUpgrade; i++) {
                    AbstractCard cardToUpgrade = upgradableCards.get(i);
                    cardToUpgrade.upgrade();

                    // 显示升级的卡牌
                    AbstractCard showCard = cardToUpgrade.makeStatEquivalentCopy();

                    // 为每张卡牌设置不同的显示位置
                    float xPos = Settings.WIDTH / 2.0F + (i - 0.5f) * 300.0F * Settings.scale;
                    float yPos = Settings.HEIGHT / 2.0F;

                    AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(
                            showCard,
                            xPos,
                            yPos
                    ));

                    // 升级特效
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(
                            xPos,
                            yPos
                    ));
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
    }
}