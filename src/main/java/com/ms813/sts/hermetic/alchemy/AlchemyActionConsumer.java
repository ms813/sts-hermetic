package com.ms813.sts.hermetic.alchemy;

import com.megacrit.cardcrawl.core.AbstractCreature;

@FunctionalInterface
public interface AlchemyActionConsumer {
    void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source);
}
