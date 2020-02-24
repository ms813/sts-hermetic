package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.hooks.OnGainGoldPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.POWER_IMG_PATH;

public class GlitteringAegisPower extends AbstractPower implements OnGainGoldPower {

    private static final Logger logger = LoggerFactory.getLogger(GlitteringAegisPower.class);
    public static final String POWER_ID = "GlitteringAegisPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public GlitteringAegisPower(
        final AbstractCreature owner,
        final int amount
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(String.format(POWER_IMG_PATH, ID));
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onGainGold(final AbstractPlayer player, final int goldAmount) {
        logger.info("{} gained {} gold!", player.name, goldAmount);
        this.addToBot(new GainBlockAction(player, this.amount));
    }
}
