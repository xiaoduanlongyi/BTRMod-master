package btrmod.patches;

import btrmod.character.KessokuBandChar;
import btrmod.ui.ComboUIPanel;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;

public class ComboPanelPatch {

    // Add ComboUIPanel field to AbstractPlayer
    @SpirePatch(
            clz = AbstractPlayer.class,
            method = SpirePatch.CLASS
    )
    public static class ComboPanelField {
        public static SpireField<ComboUIPanel> comboPanel = new SpireField<>(ComboUIPanel::new);
    }

    // Show panel when combat panels are shown
    @SpirePatch(
            clz = OverlayMenu.class,
            method = "showCombatPanels"
    )
    public static class ShowComboPanelPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            if (AbstractDungeon.player != null && isPlayingKessokuBand()) {
                ComboPanelField.comboPanel.get(AbstractDungeon.player).show();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(
                        EnergyPanel.class, "show"
                );
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    // Hide panel when combat panels are hidden
    @SpirePatch(
            clz = OverlayMenu.class,
            method = "hideCombatPanels"
    )
    public static class HideComboPanelPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert() {
            if (AbstractDungeon.player != null) {
                ComboPanelField.comboPanel.get(AbstractDungeon.player).hide();
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(
                        EnergyPanel.class, "hide"
                );
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    // Update panel
    @SpirePatch(
            clz = OverlayMenu.class,
            method = "update"
    )
    public static class UpdateComboPanelPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if (AbstractDungeon.player != null) {
                ComboPanelField.comboPanel.get(AbstractDungeon.player).update();
            }
        }
    }

    // Render panel
    @SpirePatch(
            clz = OverlayMenu.class,
            method = "render"
    )
    public static class RenderComboPanelPatch {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(OverlayMenu __instance, SpriteBatch sb) {
            if (AbstractDungeon.player != null) {
                ComboPanelField.comboPanel.get(AbstractDungeon.player).render(sb);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(
                        EnergyPanel.class, "render"
                );
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    // Get the panel instance
    public static ComboUIPanel getComboPanel() {
        if (AbstractDungeon.player != null) {
            return ComboPanelField.comboPanel.get(AbstractDungeon.player);
        }
        return null;
    }

    // 辅助方法：检查是否在玩KessokuBandChar
    private static boolean isPlayingKessokuBand() {
        return AbstractDungeon.player != null &&
                AbstractDungeon.player.chosenClass == KessokuBandChar.Meta.KessokuBand;
    }
}