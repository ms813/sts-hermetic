package com.ms813.sts.hermetic.alchemy.quicken;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ChokePower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;

public class QuickenPureSanguisAction implements AlchemyActionConsumer {

    private static final int BASE_X = 1;

    // apply vulnerability = ceil(log2(n) + x)
    // apply strangle = ceiling(log2(n - 2) + 2 + x)
    @Override
    public void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source) {

        final int n = tuple.sanguis + AlchemyActions.getNIncrease();
        final int x = BASE_X + AlchemyActions.getXIncrease();

        int vuln = (int) Math.ceil(log(n, 2) + x);

        double chokeLog = log(n - 2, 2);
        int choke;
        if (Double.isNaN(chokeLog) || Double.isInfinite(chokeLog)) {
            choke = 0;
        } else {
            choke = (int) Math.ceil(chokeLog + 2 + x);
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
            target, source, new VulnerablePower(target, vuln, false), vuln)
        );

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
            target, source, new ChokePower(target, choke), vuln)
        );
    }

    public static double log(final double x, int base) {
        if (base < 1) {
            throw new IllegalArgumentException("Base of logarithm cannot be < 1");
        }

        return Math.log(x) / Math.log(base);
    }
}
