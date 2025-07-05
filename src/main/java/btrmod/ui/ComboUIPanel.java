package btrmod.ui;

import btrmod.BTRMod;
import btrmod.util.CardTagEnum;
import btrmod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

import static btrmod.BTRMod.imagePath;

public class ComboUIPanel extends AbstractPanel {
    // Panel positioning
    private static final float SHOW_X = 75f * Settings.scale;
    private static final float SHOW_Y = Settings.HEIGHT * 0.5f;
    private static final float HIDE_X = -200f * Settings.scale;

    // UI dimensions
    private static final float CHARACTER_SIZE = 80f * Settings.scale;
    private static final float SPACING = 10f * Settings.scale;
    private static final float PANEL_WIDTH = CHARACTER_SIZE + 40f * Settings.scale;
    private static final float PANEL_HEIGHT = 3f * (CHARACTER_SIZE + 40f * Settings.scale);
    //private static final float PANEL_WIDTH = 120f * Settings.scale;
    //private static final float PANEL_HEIGHT = 1.27f * (CHARACTER_SIZE + 40f * Settings.scale);

    // Textures
    private Texture panelBg;
    private Texture bocchiTexture;
    private Texture kitaTexture;
    private Texture nijikaTexture;
    private Texture ryoTexture;

    // State
    private AbstractCard.CardTags currentTag = null;
    private int comboCount = 0;

    // Interaction
    private Hitbox hb;

    public ComboUIPanel() {
        // Initialize with show/hide positions
        super(SHOW_X, SHOW_Y, HIDE_X, SHOW_Y, null, true); // Start hidden

        // Load textures
        loadTextures();

        // Initialize hitbox
        hb = new Hitbox(PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void loadTextures() {
        // Load panel background (optional)
        panelBg = TextureLoader.getTextureNull(BTRMod.imagePath("ui/combo/panel_bg.png"));

        // Load character textures
        bocchiTexture = TextureLoader.getTexture(BTRMod.imagePath("ui/combo/bocchi.png"));
        kitaTexture = TextureLoader.getTexture(BTRMod.imagePath("ui/combo/kita.png"));
        nijikaTexture = TextureLoader.getTexture(BTRMod.imagePath("ui/combo/nijika.png"));
        ryoTexture = TextureLoader.getTexture(BTRMod.imagePath("ui/combo/ryo.png"));
    }

    public void updateCombo(AbstractCard.CardTags tag, int count) {
        boolean wasEmpty = (this.comboCount == 0);

        this.currentTag = tag;
        this.comboCount = count;

        // Show panel when combo starts
        if (wasEmpty && count > 0) {
            show();
        }
        // Hide panel when combo is cleared
        else if (count == 0) {
            hide();
        }
    }

    public void clearCombo() {
        updateCombo(null, 0);
    }

    public void update() {
        // Update positions (handles animation)
        updatePositions();

        // Update hitbox
        if (!isHidden) {
            hb.move(current_x + PANEL_WIDTH / 2f, current_y);
            hb.update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!isHidden && shouldRender()) {
            // Render panel background if available
            if (panelBg != null) {
                sb.setColor(Color.WHITE);
                sb.draw(panelBg,
                        current_x - 20f * Settings.scale,
                        current_y - PANEL_HEIGHT / 2f - 20f * Settings.scale,
                        PANEL_WIDTH,
                        PANEL_HEIGHT);
            }

            // Render character images
            renderComboImages(sb);

            // Render tooltip on hover
            if (hb.hovered && !AbstractDungeon.isScreenUp) {
                renderTooltip(sb);
            }
        }
    }

    private boolean shouldRender() {
        return CardCrawlGame.isInARun() &&
                AbstractDungeon.getCurrRoom() != null &&
                !AbstractDungeon.getCurrRoom().isBattleOver;
    }

    private void renderComboImages(SpriteBatch sb) {
        if (currentTag == null || comboCount == 0) return;

        Texture charTexture = getTextureForTag(currentTag);
        if (charTexture == null) return;

        sb.setColor(Color.WHITE);

        // Render character images vertically
        for (int i = 0; i < comboCount; i++) {
            float y = current_y - PANEL_HEIGHT / 2f - 10f * Settings.scale +
                    (i * (CHARACTER_SIZE + SPACING));

            sb.draw(charTexture,
                    current_x,
                    y,
                    CHARACTER_SIZE,
                    CHARACTER_SIZE);
        }

        // Render combo count text
        String comboText = comboCount + "/3";
        FontHelper.renderFontCentered(sb,
                FontHelper.panelNameFont,
                comboText,
                current_x + CHARACTER_SIZE / 2f,
                current_y + PANEL_HEIGHT / 2f - 10f * Settings.scale,
                getCharacterColor(currentTag));
    }

    private void renderTooltip(SpriteBatch sb) {
        if (currentTag == null || comboCount == 0) return;

        //String header = getCharacterName(currentTag);
        String header = CardCrawlGame.languagePack.getUIString(BTRMod.makeID("soloComboPanel")).TEXT[0];

        String body = CardCrawlGame.languagePack.getUIString(BTRMod.makeID("soloComboPanel")).TEXT[1] +
                getCharacterName(currentTag) +
                CardCrawlGame.languagePack.getUIString(BTRMod.makeID("soloComboPanel")).TEXT[2] +
                comboCount +
                " /3。 ";

        body += getCharacterSoloDescription(currentTag);

        TipHelper.renderGenericTip(
                current_x + PANEL_WIDTH + 10f * Settings.scale,
                current_y,
                header,
                body
        );
    }

    private Texture getTextureForTag(AbstractCard.CardTags tag) {
        if (tag == CardTagEnum.BOCCHI) return bocchiTexture;
        if (tag == CardTagEnum.KITA) return kitaTexture;
        if (tag == CardTagEnum.NIJIKA) return nijikaTexture;
        if (tag == CardTagEnum.RYO) return ryoTexture;
        return null;
    }

    private String getCharacterName(AbstractCard.CardTags tag) {
        if (tag == CardTagEnum.BOCCHI) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSolo")).TEXT[0];
        if (tag == CardTagEnum.KITA) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSolo")).TEXT[1];
        if (tag == CardTagEnum.NIJIKA) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSolo")).TEXT[2];
        if (tag == CardTagEnum.RYO) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSolo")).TEXT[3];
        return "Unknown";
    }

    private String getCharacterSoloDescription(AbstractCard.CardTags tag) {
        if (tag == CardTagEnum.BOCCHI) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSoloDescription")).TEXT[0];
        if (tag == CardTagEnum.KITA) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSoloDescription")).TEXT[1];
        if (tag == CardTagEnum.NIJIKA) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSoloDescription")).TEXT[2];
        if (tag == CardTagEnum.RYO) return CardCrawlGame.languagePack.getUIString(BTRMod.makeID("memberSoloDescription")).TEXT[3];
        return "Unknown";
    }

    private Color getCharacterColor(AbstractCard.CardTags tag) {
        if (tag == CardTagEnum.BOCCHI) return new Color(1f, 0.6f, 0.8f, 1f);  // 粉色
        if (tag == CardTagEnum.RYO) return new Color(0.4f, 0.6f, 1f, 1f);     // 蓝色
        if (tag == CardTagEnum.NIJIKA) return new Color(1f, 0.9f, 0.4f, 1f);  // 黄色
        if (tag == CardTagEnum.KITA) return new Color(1f, 0.5f, 0.3f, 1f);    // 橙红色
        return Color.WHITE;
    }
}