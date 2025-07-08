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

                // 随机选择一张卡牌
                AbstractCard cardToUpgrade = upgradableCards.get(
                        AbstractDungeon.miscRng.random(upgradableCards.size() - 1)
                );

                // 升级卡牌
                cardToUpgrade.upgrade();

                // 显示升级的卡牌
                AbstractCard showCard = cardToUpgrade.makeStatEquivalentCopy();
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(
                        showCard,
                        Settings.WIDTH / 2.0F,
                        Settings.HEIGHT / 2.0F
                ));

                // 升级特效
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(
                        Settings.WIDTH / 2.0F,
                        Settings.HEIGHT / 2.0F
                ));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 1 + DESCRIPTIONS[1];
    }
}
