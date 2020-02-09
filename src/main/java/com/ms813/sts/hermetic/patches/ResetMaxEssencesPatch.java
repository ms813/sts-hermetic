package com.ms813.sts.hermetic.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.alchemy.AlchemyActions.CURRENT_MAX_ESSENCES;
import static com.ms813.sts.hermetic.alchemy.AlchemyActions.DEFAULT_MAX_ESSENCES;

@SpirePatch(
    clz = AbstractRoom.class,
    method = "endBattle"
)
public class ResetMaxEssencesPatch {

    private static final Logger logger = LoggerFactory.getLogger(ResetMaxEssencesPatch.class);

    @SpirePostfixPatch
    public static void resetMaxEssences(final AbstractRoom __instance) {
        logger.info("Resetting CURRENT_MAX_ESSENCES after room end from {} to {}", CURRENT_MAX_ESSENCES, DEFAULT_MAX_ESSENCES);
        CURRENT_MAX_ESSENCES = DEFAULT_MAX_ESSENCES;
    }
}
