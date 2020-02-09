package com.ms813.sts.hermetic.alchemy.quicken;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.ms813.sts.hermetic.alchemy.AlchemyActionConsumer;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickenAetherGt1Sanguis1Action implements AlchemyActionConsumer {

    private static final Logger logger = LoggerFactory.getLogger(QuickenAetherGt1Sanguis1Action.class);

    private static final int BASE_DAMAGE = 4;

    @Override
    public void accept(AlchemyTuple tuple, AbstractCreature target, AbstractCreature source) {
        int damage = BASE_DAMAGE + AlchemyActions.getXIncrease();
        int n = tuple.aether + AlchemyActions.getNIncrease() - 1;
        int vuln = (int) Math.floor(n / 2d);
        if (vuln == 0) {
            vuln = 1;
        }

        logger.info("Performing quickenAction. aether={} sanguis={} dmg={} vuln={} n={}", tuple.aether, tuple.sanguis, damage, vuln, n);

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
            target, source, new VulnerablePower(target, vuln, false), vuln)
        );

        for (int i = 0; i < n; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(source, damage), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
