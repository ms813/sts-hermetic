package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class AffluenceAblationPower extends AbstractPower {

    private static final Logger logger = LoggerFactory.getLogger(AffluenceAblationPower.class);
    public static final String POWER_ID = "AffluenceAblationPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public AffluenceAblationPower(
        final AbstractCreature owner,
        final int amount
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(EXAMPLE_IMG_PATH + "powers/FullAwakening.png");
        this.amount = amount;
        this.updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        owner.loseGold(this.amount);
        this.flash();
        return Math.max(damageAmount - this.amount, 0);
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + amount + ".";
    }
}
