package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.effects.CoinEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;
import static com.ms813.sts.hermetic.alchemy.AlchemyActions.CURRENT_MAX_AURUM;

public class AurumPower extends AbstractPower {

    private static float TALK_DURATION = 5.0f;
    private static boolean firstTime = true;
    private static String[] dialog = {
        "For so... many years...",
        "So... beautiful...",
        "It... worked...",
        "The Work... is complete...",
        "The Work... it is done...",
        "So much blood... for my art...",
        "Blood... for the Work...",
    };

    private static final Logger logger = LoggerFactory.getLogger(AurumPower.class);
    public static final String POWER_ID = "AurumPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AurumPower(
        final AbstractCreature owner,
        final int amount
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(EXAMPLE_IMG_PATH + "powers/FullAwakening.png");
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + CURRENT_MAX_AURUM;
    }

    @Override
    public void onInitialApplication() {
        if (firstTime) {
            AbstractDungeon.actionManager.addToBottom(
                new TalkAction(true, dialog[ThreadLocalRandom.current().nextInt(0, dialog.length)], TALK_DURATION, TALK_DURATION)
            );
            firstTime = false;
        }
        this.owner.tint.changeColor(Color.YELLOW.cpy());
        CoinEffects.implosion(owner, amount * 100, owner.hb.cX, owner.hb.cY, Settings.WIDTH, Settings.HEIGHT, false);
        CardCrawlGame.sound.play("GOLD_JINGLE");
        this.checkStacks(this.amount);
    }

    @Override
    public void stackPower(final int stackAmount) {
        super.stackPower(stackAmount);
        this.checkStacks(stackAmount);
    }

    private void checkStacks(final int stackAmount) {
        if (this.amount > CURRENT_MAX_AURUM) {
            logger.info("Aurum stack cap reached, setting stacks to CURRENT_MAX_AURUM ({})", CURRENT_MAX_AURUM);
            this.amount = CURRENT_MAX_AURUM;
            this.updateDescription();
        }
    }

    @Override
    public void onRemove() {
        this.owner.tint.changeColor(Color.WHITE.cpy());
    }
}
