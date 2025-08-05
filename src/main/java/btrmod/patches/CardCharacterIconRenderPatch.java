package btrmod.patches;

import btrmod.util.CardCharacterIconRenderHelper;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import javassist.CannotCompileException;
import javassist.CtBehavior;

/**
 * Patch卡牌的render方法来显示角色图标
 */
public class CardCharacterIconRenderPatch {

    /**
     * 在卡牌渲染时添加角色图标
     */
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCard",
            paramtypez = {SpriteBatch.class, boolean.class, boolean.class}
    )
    public static class RenderCardPatch {
        @SpireInsertPatch(
                locator = CardCharacterIconRenderPatch.RenderCardPatch.Locator.class
        )
        public static void Insert(AbstractCard _inst, SpriteBatch sb, boolean hovered, boolean selected) {
            CardCharacterIconRenderHelper.renderCharacterIconOnCard(_inst, sb);
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
                return LineFinder.findInOrder(ctBehavior, new Matcher.FieldAccessMatcher(Settings.class, "lineBreakViaCharacter"));
            }
        }
    }

    /**
     * 在单卡查看界面也显示图标
     */
    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderInLibrary",
            paramtypez = {SpriteBatch.class}
    )
    public static class RenderInLibraryPatch {
        @SpireInsertPatch(
                locator = CardCharacterIconRenderPatch.RenderInLibraryPatch.Locator.class
        )
        public static void Insert(AbstractCard _inst, SpriteBatch sb) {
            CardCharacterIconRenderHelper.renderCharacterIconInLibrary(_inst, sb);

        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctBehavior) throws CannotCompileException, PatchingException {
                return LineFinder.findInOrder(ctBehavior, new Matcher.FieldAccessMatcher(Settings.class, "lineBreakViaCharacter"));
            }
        }
    }
}