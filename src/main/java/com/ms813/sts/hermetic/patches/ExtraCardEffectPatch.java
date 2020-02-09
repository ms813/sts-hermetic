package com.ms813.sts.hermetic.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.HermeticMod;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

@SpirePatch(
    clz = AbstractPlayer.class,
    method = "useCard",
    paramtypez = {
        AbstractCard.class,
        AbstractMonster.class,
        int.class
    }
)
public class ExtraCardEffectPatch {

    private static final Logger logger = LoggerFactory.getLogger(ExtraCardEffectPatch.class);

    @SpirePostfixPatch
    public static void ExtraCardPostfix(AbstractPlayer __instance, AbstractCard c, AbstractMonster monster, int energyOnUse) {

        final Map<UUID, Map<String, AbstractGameAction>> allExtraCardActions = AlchemyActions.extraCardActions;

        if (allExtraCardActions != null && allExtraCardActions.containsKey(c.uuid)) {
            final Map<String, AbstractGameAction> extraActions = allExtraCardActions.get(c.uuid);

            extraActions.forEach((final String key, final AbstractGameAction action) -> {
                action.isDone = false;
                if (action.source == null) {
                    action.source = __instance;
                }

                if (action.target == null) {
                    action.target = monster;
                }

                logger.info("Playing extra action: {}, for card: {}", action.getClass(), c.name);
                AbstractDungeon.actionManager.addToBottom(action);
            });
        }
    }
}
