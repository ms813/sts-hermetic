package com.ms813.sts.hermetic.alchemy.quicken;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import com.ms813.sts.hermetic.powers.AetherPower;
import com.ms813.sts.hermetic.powers.AurumPower;
import com.ms813.sts.hermetic.powers.SanguisPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.alchemy.AlchemyTuple.tuple;

public class QuickenActions {

    private static final Logger logger = LoggerFactory.getLogger(QuickenActions.class);

    public static final QuickenActions instance = new QuickenActions();

    public AlchemyTuple apply(
        final AetherPower aether,
        final SanguisPower sanguis,
        final AbstractCreature target,
        final AbstractCreature source
    ) {
        final AlchemyTuple tuple = tuple(aether, sanguis);
        final int ae = tuple.aether;
        final int s = tuple.sanguis;

        logger.info("Applying quicken action: ae={} s={}", ae, s);

        if (s == 0 && ae == 0) {
            logger.info("Attempted to perform a quicken action but no essences were on the target!");
        }
        // rows starting east of the diagonal
        else if (s == 0 && ae > 0) {  // first row
            new QuickenPureAetherAction().accept(tuple, target, source);
        } else if (s == 1 && ae > 1) {  // second row
            new QuickenAetherGt1Sanguis1Action().accept(tuple, target, source);
        } else if (s == 2 && ae > 2) {  // third row
            logger.info("(s == 2 && ae > 2) ae={}, s={}", ae, s);
        } else if (s == 3 && ae > 3) {  // fourth row
            logger.info("(s == 3 && ae > 3) ae={}, s={}", ae, s);
        } else if (s == 4 && ae > 4) {  // fifth row
            logger.info("(s == 4 && ae > 4) ae={}, s={}", ae, s);
        }
        //columns starting south of the diagonal
        else if (s > 0 && ae == 0) {  // first column
            new QuickenPureSanguisAction().accept(tuple, target, source);
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
        } else if (s == 5 && ae == 5) {
            logger.info("(s == 5 && ae == 5) ae={}, s={}", ae, s);
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

        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, source, AetherPower.POWER_ID, tuple.aether));
        AbstractDungeon.actionManager.addToBottom((new ReducePowerAction(target, source, SanguisPower.POWER_ID, tuple.sanguis)));

        return tuple;
    }

    public AurumPower apply(
        final AurumPower aurum,
        final AbstractCreature target,
        final AbstractCreature source
    ) {
        logger.info("Nothing implemented for quicken aurum yet {}", aurum.amount);

        AbstractDungeon.actionManager.addToBottom((new ReducePowerAction(target, source, aurum, 1)));
        return aurum;
    }
}
