package com.ms813.sts.hermetic.alchemy;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AlchemyActions {
    public static Map<UUID, Map<String, AbstractGameAction>> extraCardActions = new HashMap<>();

    public static boolean cardAlreadyHasSpecificExtraAction(final AbstractCard card, final String key) {
        final Map<String, AbstractGameAction> cardActions = extraCardActions.get(card.uuid);
        return cardActions != null && cardActions.get(key) != null;
    }

    public enum Actions {
        COAGULATE, QUICKEN, SUBLIMATE
    }

    public static final int DEFAULT_MAX_ESSENCES = 5;
    public static int CURRENT_MAX_ESSENCES = DEFAULT_MAX_ESSENCES;

    public static final int DEFAULT_MAX_AURUM = 1;
    public static int CURRENT_MAX_AURUM = DEFAULT_MAX_AURUM;

    private static final Logger logger = LoggerFactory.getLogger(AlchemyActions.class);

    public static int getNIncrease() {
        // check here for powers, relics etc
        return 0;
    }

    public static int getXIncrease() {
        // check here for powers, relics etc
        return 0;
    }

    public static void addExtraCardAction(final AbstractCard card, final AbstractGameAction action, final String key) {
        if (extraCardActions.get(card.uuid) == null) {
            extraCardActions.put(card.uuid, new HashMap<>());
        }

        extraCardActions.get(card.uuid).put(key, action);
    }
}
