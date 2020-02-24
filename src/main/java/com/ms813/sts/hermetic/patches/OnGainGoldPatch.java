package com.ms813.sts.hermetic.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.hooks.OnGainGoldPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@SpirePatch(
    clz = AbstractPlayer.class,
    method = "gainGold"
)
public class OnGainGoldPatch {

    private static final Logger logger = LoggerFactory.getLogger(OnGainGoldPatch.class);

    @SpireInsertPatch(
        locator = Locator.class,
        localvars = {"amount"}
    )
    public static void Insert(final AbstractPlayer __instance, int amount) {
        // draw things right before the SpriteBatch has `end` called
        for (final AbstractPower power : __instance.powers) {
            if (power instanceof OnGainGoldPower) {
                logger.debug("{} gained {} gold!", __instance.name, amount);
                ((OnGainGoldPower) power).onGainGold(__instance, amount);
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
