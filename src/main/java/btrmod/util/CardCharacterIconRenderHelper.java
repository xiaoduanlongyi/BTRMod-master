package btrmod.util;

import btrmod.BTRMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.HashMap;
import java.util.Map;

/**
 * 渲染卡牌角色图标的辅助类
 */
public class CardCharacterIconRenderHelper {
    // 图标纹理缓存
    private static Map<AbstractCard.CardTags, Texture> textures;

    // 图标尺寸和位置
    public static final float ICON_SIZE = 80.0F;  // 比参考例子小一些
    private static final float X_OFFSET = -134.0F;  // 与费用位置对齐
    private static final float Y_OFFSET = 110.0F;    // 在费用下方

    // 初始化纹理
    public static void initialize() {
        textures = new HashMap<>();
        textures.put(CardTagEnum.BOCCHI, new Texture(BTRMod.imagePath("ui/icons/bocchi_icon.png")));
        textures.put(CardTagEnum.KITA, new Texture(BTRMod.imagePath("ui/icons/kita_icon.png")));
        textures.put(CardTagEnum.NIJIKA, new Texture(BTRMod.imagePath("ui/icons/nijika_icon.png")));
        textures.put(CardTagEnum.RYO, new Texture(BTRMod.imagePath("ui/icons/ryo_icon.png")));
    }

    /**
     * 获取卡牌对应的角色标签
     */
    private static AbstractCard.CardTags getCharacterTag(AbstractCard card) {
        if (card.hasTag(CardTagEnum.BOCCHI)) return CardTagEnum.BOCCHI;
        if (card.hasTag(CardTagEnum.KITA)) return CardTagEnum.KITA;
        if (card.hasTag(CardTagEnum.NIJIKA)) return CardTagEnum.NIJIKA;
        if (card.hasTag(CardTagEnum.RYO)) return CardTagEnum.RYO;
        return null;
    }

    /**
     * 获取对应的图标纹理
     */
    private static Texture getTexture(AbstractCard card) {
        AbstractCard.CardTags tag = getCharacterTag(card);
        return tag != null ? textures.get(tag) : null;
    }

    /**
     * 在卡牌上绘制图标（核心方法，参考HueRenderHelper）
     */
    public static void drawOnCard(AbstractCard card, SpriteBatch sb, Texture img,
                                  float xPos, float yPos, Vector2 offset,
                                  float width, float height, float alpha,
                                  float drawScale, float scaleModifier) {
        // 处理卡牌旋转
        if (card.angle != 0.0F) {
            offset = offset.cpy();  // 创建副本避免修改原始偏移
            offset.rotate(card.angle);
        }

        // 应用缩放
        offset.scl(Settings.scale * drawScale);
        float drawX = xPos + offset.x;
        float drawY = yPos + offset.y;
        float scale = drawScale * Settings.scale * scaleModifier;

        // 保存当前颜色并设置透明度
        Color backup = sb.getColor();
        sb.setColor(1.0F, 1.0F, 1.0F, alpha);

        // 绘制图标
        sb.draw(img,
                drawX - width / 2.0F, drawY - height / 2.0F,  // 位置
                width / 2.0F, height / 2.0F,                  // 旋转中心
                width, height,                                 // 尺寸
                scale, scale,                                  // 缩放
                card.angle,                                    // 旋转角度
                0, 0, img.getWidth(), img.getHeight(),       // 纹理区域
                false, false);                                 // 翻转

        // 恢复颜色
        sb.setColor(backup);
    }

    /**
     * 渲染角色图标到卡牌上（对外接口）
     */
    public static void renderCharacterIconOnCard(AbstractCard card, SpriteBatch sb) {
        // 检查卡牌状态
        if (card.isFlipped || card.isLocked || card.transparency <= 0.0F) {
            return;
        }

        // 获取图标纹理
        Texture iconTexture = getTexture(card);
        if (iconTexture == null) {
            return;
        }

        // 绘制图标
        drawOnCard(card, sb, iconTexture,
                card.current_x, card.current_y,
                new Vector2(X_OFFSET, Y_OFFSET),
                ICON_SIZE, ICON_SIZE,
                card.transparency,
                card.drawScale,
                1.0F);
    }

    /**
     * 在卡牌库中渲染（如果需要）
     */
    public static void renderCharacterIconInLibrary(AbstractCard card, SpriteBatch sb) {
        renderCharacterIconOnCard(card, sb);
    }
}