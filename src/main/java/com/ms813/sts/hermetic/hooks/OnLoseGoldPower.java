package com.ms813.sts.hermetic.hooks;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

public interface OnLoseGoldPower {
    void onLoseGold(AbstractPlayer player, int goldAmount);
}
