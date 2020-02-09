package com.ms813.sts.hermetic.alchemy.coagulate;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoagulatePureAetherAction implements AlchemyActionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CoagulatePureAetherAction.class);

    private static final int BASE_BLOCK = 4;

    @Override
    public void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source) {

        int block = BASE_BLOCK + AlchemyActions.getXIncrease();
        int n = tuple.aether + AlchemyActions.getNIncrease();
        logger.info("Performing coagulatePureAetherAction. aether={} block={} n={}", tuple.aether, block, n);

        for (int i = 0; i < n; i++) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(source, block));
        }
    }
}