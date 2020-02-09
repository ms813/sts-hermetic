package com.ms813.sts.hermetic.alchemy.quicken;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import com.ms813.sts.hermetic.cards.quicken.IgniteCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickenPureAetherAction implements AlchemyActionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(QuickenPureAetherAction.class);

    private static final int BASE_DAMAGE = 4;

    @Override
    public void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source) {

        int damage = BASE_DAMAGE + AlchemyActions.getXIncrease();
        int n = tuple.aether + AlchemyActions.getNIncrease();
        logger.info("Performing quickenPureAetherAction. aether={} dmg={} n={}", tuple.aether, damage, n);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new IgniteCard(damage, n)));
    }
}
