package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.IMG_PATH;

public class SanguisPower extends AbstractPower {

    private static final Logger logger = LoggerFactory.getLogger(SanguisPower.class);
    public static final String POWER_ID = "SanguisPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public SanguisPower(
        final AbstractCreature owner,
        final int amount
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(IMG_PATH + "powers/DeadlyPlay.png");
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
    }

    @Override
    public void onInitialApplication() {
        this.applyIncreases(this.amount);
        this.bindStacksToMax();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.applyIncreases(stackAmount);
        this.bindStacksToMax();
    }

    private void applyIncreases(int baseAmount) {
        final AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(ConduitPower.POWER_ID)) {
            this.amount += player.getPower(ConduitPower.POWER_ID).amount;
        }
    }

    private void bindStacksToMax() {
        if (this.amount > AlchemyActions.CURRENT_MAX_ESSENCES) {
            logger.info("Sanguis stack cap reached, setting stacks to CURRENT_MAX_ESSENCES");
            this.amount = AlchemyActions.CURRENT_MAX_ESSENCES;
            this.updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + AlchemyActions.CURRENT_MAX_ESSENCES;
    }
}
