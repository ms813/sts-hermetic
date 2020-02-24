package com.ms813.sts.hermetic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.hooks.OnLoseGoldPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@SpirePatch(
    clz = AbstractPlayer.class,
    method = "loseGold"
)
public class OnLoseGoldPatch {

    private static final Logger logger = LoggerFactory.getLogger(OnLoseGoldPatch.class);

    @SpireInsertPatch(
        locator = Locator.class,
        localvars = {"goldAmount"}
    )
    public static void Insert(final AbstractPlayer __instance, int goldAmount) {
        // draw things right before the SpriteBatch has `end` called
        for (final AbstractPower power : __instance.powers) {
            if (power instanceof OnLoseGoldPower) {
                logger.debug("{} lost {} gold!", __instance.name, goldAmount);
                ((OnLoseGoldPower) power).onLoseGold(__instance, goldAmount);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {

        public int[] Locate(final CtBehavior methodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "iterator");
            return LineFinder.findInOrder(methodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
