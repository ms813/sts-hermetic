package com.ms813.sts.hermetic.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.ms813.sts.hermetic.alchemy.AlchemyTuple;
import com.ms813.sts.hermetic.alchemy.sublimate.SublimateActions;
import com.ms813.sts.hermetic.powers.AetherPower;
import com.ms813.sts.hermetic.powers.AurumPower;
import com.ms813.sts.hermetic.powers.SanguisPower;

public class SublimateAction extends AbstractGameAction {

    public SublimateAction(
        final AbstractCreature target,
        final AbstractCreature source
    ) {
        this.target = target;
        this.source = source;
    }

    @Override
    public void update() {
        final AetherPower aether = (AetherPower) target.getPower(AetherPower.POWER_ID);
        final SanguisPower sanguis = (SanguisPower) target.getPower(SanguisPower.POWER_ID);


        final AlchemyTuple tuple = SublimateActions.instance.apply(aether, sanguis, target, source);

        final AurumPower aurum = (AurumPower) target.getPower(AurumPower.POWER_ID);
        if (aurum != null) {
            SublimateActions.instance.apply(aurum, target, source);
        }

        this.isDone = true;
    }
}
