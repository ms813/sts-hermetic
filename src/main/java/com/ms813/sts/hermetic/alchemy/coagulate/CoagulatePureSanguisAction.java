package com.ms813.sts.hermetic.alchemy.coagulate;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoagulatePureSanguisAction implements AlchemyActionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CoagulatePureSanguisAction.class);

    @Override
    public void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source) {
        logger.info("Not implemented");
    }
}