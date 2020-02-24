package com.ms813.sts.hermetic.hooks;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public interface OnGainGoldPower {
    void onGainGold(AbstractPlayer player, int goldAmount);
}
