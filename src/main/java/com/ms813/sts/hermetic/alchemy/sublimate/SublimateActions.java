package com.ms813.sts.hermetic.alchemy.sublimate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import com.ms813.sts.hermetic.effects.CoinEffects;
import com.ms813.sts.hermetic.powers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.ms813.sts.hermetic.alchemy.AlchemyTuple.tuple;

public class SublimateActions extends AlchemyActions {

    private static final Logger logger = LoggerFactory.getLogger(SublimateActions.class);

    public static final SublimateActions instance = new SublimateActions();

    public final Map<AlchemyTuple, AlchemyActionConsumer> actions = new HashMap<>();

    public AlchemyTuple apply(
        final AetherPower aether,
        final SanguisPower sanguis,
        final AbstractCreature target,
        final AbstractCreature source
    ) {
        final AlchemyTuple tuple = tuple(aether, sanguis);
        final int ae = tuple.aether;
        final int s = tuple.sanguis;

        logger.info("Applying sublimate action: ae={} s={}", ae, s);

        if (s == 0 && ae == 0) {
            logger.info("Attempted to perform a sublimate action but no essences were on the target!");
        }
        // rows starting east of the diagonal
        else if (s == 0 && ae > 0) {  // first row
            logger.info("(s == 0 && ae > 0) ae={}, s={}", ae, s);
        } else if (s == 1 && ae > 1) {  // second row
            logger.info("(s == 1 && ae > 1) ae={}, s={}", ae, s);
        } else if (s == 2 && ae > 2) {  // third row
            logger.info("(s == 2 && ae > 2) ae={}, s={}", ae, s);
        } else if (s == 3 && ae > 3) {  // fourth row
            logger.info("(s == 3 && ae > 3) ae={}, s={}", ae, s);
        } else if (s == 4 && ae > 4) {  // fifth row
            logger.info("(s == 4 && ae > 4) ae={}, s={}", ae, s);
        }
        //columns starting south of the diagonal
        else if (ae == 0) {  // first column
            logger.info("(s > 0 && ae == 0) ae={}, s={}", ae, s);
        } else if (s > 1 && ae == 1) { // second column
            logger.info("(s > 1 && ae == 1) ae={}, s={}", ae, s);
        } else if (s > 2 && ae == 2) { // third column
            logger.info("(s > 2 && ae == 2) ae={}, s={}", ae, s);
        } else if (s > 3 && ae == 3) { // fourth column
            logger.info("(s > 3 && ae == 3) ae={}, s={}", ae, s);
        } else if (s > 4 && ae == 4) { // fifth column
            logger.info("(s > 4 && ae == 4) ae={}, s={}", ae, s);
        }
        //diagonal
        else if (s == 1 && ae == 1) {
            logger.info("(s == 1 && ae == 1) ae={}, s={}", ae, s);
        } else if (s == 2 && ae == 2) {
            logger.info("(s == 2 && ae == 2) ae={}, s={}", ae, s);
        } else if (s == 3 && ae == 3) {
            logger.info("(s == 3 && ae == 3) ae={}, s={}", ae, s);
        } else if (s == 4 && ae == 4) {
            logger.info("(s == 4 && ae == 4) ae={}, s={}", ae, s);
            addToBot(new ApplyPowerAction(source, source, new CompressionPower(source, 1), 1));
        } else if (s == 5 && ae == 5) {
            addToBot(new ApplyPowerAction(source, source, new ConduitPower(source, 1)));
        }
        // diagonal beyond 5
        else if (s == ae) {
            logger.info("(s == 5 && ae == 5) ae={}, s={}", ae, s);
        }
        // rows after 5
        else if (s > 5 && ae > s) {
            logger.info("(s > 5 && ae > s) ae={}, s={}", ae, s);
        }
        // columns after 5
        else if (ae > 5 && s > ae) {
            //not sure how to handle this
            logger.info("(ae > 5 && s > ae) ae={}, s={}", ae, s);
        } else {
            logger.info("Some condition was missed? ae={} s={}", ae, s);
        }

        addToBot(new ReducePowerAction(target, source, AetherPower.POWER_ID, tuple.aether));
        addToBot(new ReducePowerAction(target, source, SanguisPower.POWER_ID, tuple.sanguis));
        return tuple;
    }

    public AurumPower apply(
        final AurumPower aurum,
        final AbstractCreature target,
        final AbstractCreature source
    ) {
        if (aurum == null) {
            logger.info("Sublimate aurum but no essences to activate");
            return null;
        }

        final int amount = aurum.amount;
        if (amount == 1) {
            addToBot(new ApplyPowerAction(
                source, source, new ProjectionPower(source, 5)
            ));
            CardCrawlGame.sound.play("GOLD_JINGLE");

            CoinEffects.explosion(source, amount * 100, target.hb.cX, target.hb.cY, Settings.WIDTH, Settings.HEIGHT);
        }
        addToBot(new ReducePowerAction(target, source, aurum, aurum.amount));
        return aurum;
    }

    private void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
}