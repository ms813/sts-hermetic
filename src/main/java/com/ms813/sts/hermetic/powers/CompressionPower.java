package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class CompressionPower extends AbstractPower {

    private static final Logger logger = LoggerFactory.getLogger(CompressionPower.class);
    public static final String POWER_ID = "CompressionPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public CompressionPower(
        final AbstractCreature owner,
        final int amount
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(EXAMPLE_IMG_PATH + "powers/Charge.png");
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        this.increaseMaxEssences(amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.increaseMaxEssences(stackAmount);
    }

    private void increaseMaxEssences(final int delta) {
        AlchemyActions.CURRENT_MAX_ESSENCES += delta;
        logger.info("Incrementing CURRENT_MAX_ESSENCES by {}. New max: {}", delta, AlchemyActions.CURRENT_MAX_ESSENCES);
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + AlchemyActions.CURRENT_MAX_ESSENCES;
    }
}
