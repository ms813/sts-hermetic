package com.ms813.sts.hermetic.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.ms813.sts.hermetic.actions.ProjectionAction;
import com.ms813.sts.hermetic.alchemy.AlchemyActions;
import com.ms813.sts.hermetic.effects.CoinEffects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import static com.ms813.sts.hermetic.HermeticMod.IMG_PATH;

public class ProjectionPower extends AbstractPower {

    private static final Logger logger = LoggerFactory.getLogger(ProjectionPower.class);
    public static final String POWER_ID = "ProjectionPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final float TALK_DURATION = 3.0f;
    private final int coinsPerUse;

    public ProjectionPower(
        final AbstractCreature owner,
        final int coinsPerUse
    ) {
        this.ID = POWER_ID;
        this.name = powerStrings.NAME;
        this.owner = owner;
        this.img = new Texture(IMG_PATH + "powers/FullAwakening.png");
        this.coinsPerUse = coinsPerUse;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0];
    }

    @Override
    public void onInitialApplication() {
        this.owner.tint.color.set(Color.YELLOW.cpy());
    }

    @Override
    public void onRemove() {
        this.owner.tint.changeColor(Color.WHITE.cpy());
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {

        final AbstractPlayer player = AbstractDungeon.player;

        if (AlchemyActions.cardAlreadyHasSpecificExtraAction(card, "projection")) {
            AbstractDungeon.actionManager.addToBottom(
                new TalkAction(true, "That card already has 'projection'!", TALK_DURATION, TALK_DURATION)
            );
            return;
        }

        this.flash();
        logger.info("player x={}, y={}", player.drawX, player.drawY);
        CoinEffects.implosion(owner, 500,  player.drawX, player.drawY, Settings.WIDTH / 2f, Settings.HEIGHT / 2f, false);
        AlchemyActions.addExtraCardAction(card, new ProjectionAction(owner, coinsPerUse), "projection");
        card.name += " (g)";
        card.rawDescription += " Gain 5 gold when this card is played.";

        //todo this doesnt work when saving and loading, need to investigate how to write this when saving and reinstantiate when loading

        final List<AbstractCard> cardFromMaster = player.masterDeck.group.stream().filter(c -> c.uuid.equals(card.uuid)).collect(Collectors.toList());
        if(!cardFromMaster.isEmpty()){
            player.masterDeck.removeCard(cardFromMaster.get(0));
            player.masterDeck.addToBottom(card.makeSameInstanceOf());
        }

        try {
            AbstractCard.class.getDeclaredMethod("initializeTitle").invoke(card);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        card.initializeDescription();

        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ProjectionPower.POWER_ID));
    }
}
