package com.ms813.sts.hermetic.alchemy;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.powers.AetherPower;
import com.ms813.sts.hermetic.powers.SanguisPower;

import java.util.Objects;

import static com.ms813.sts.hermetic.alchemy.AlchemyActions.CURRENT_MAX_ESSENCES;

public class AlchemyTuple {
    public final int aether;
    public final int sanguis;

    public AlchemyTuple(int aether, int sanguis) {
        this.aether = aether;
        this.sanguis = sanguis;
    }

    private static int boundsCheck(AbstractPower p) {
        if (p == null) {
            return 0;
        } else if (p.amount > CURRENT_MAX_ESSENCES) {
            return CURRENT_MAX_ESSENCES;
        } else {
            return Math.max(p.amount, 0);
        }
    }

    public static AlchemyTuple tuple(final AetherPower aether, final SanguisPower sanguis) {
        return new AlchemyTuple(boundsCheck(aether), boundsCheck(sanguis));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlchemyTuple that = (AlchemyTuple) o;
        return aether == that.aether &&
            sanguis == that.sanguis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(aether, sanguis);
    }

    @Override
    public String toString() {
        return "AlchemyTuple{" +
            "aether=" + aether +
            ", sanguis=" + sanguis +
            '}';
    }
}
