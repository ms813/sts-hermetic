package com.ms813.sts.hermetic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectionAction extends AbstractGameAction {

    private static final Logger logger = LoggerFactory.getLogger(ProjectionAction.class);

    public ProjectionAction(final AbstractCreature source, final int amount) {
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void update() {
        CardCrawlGame.sound.play("GOLD_JINGLE");

        logger.info("stealing {} gold", amount);

        for (int i = 0; i < amount; i++) {
            logger.info("source={} target={} ", source.name, target.name);
            final GainPennyEffect effect = new GainPennyEffect(source, target.hb.cX, target.hb.cY, source.hb.cX, source.hb.cY, false);
            AbstractDungeon.effectList.add(effect);
        }
        addToBot(new GainGoldAction(amount));
        this.isDone = true;
    }
}
