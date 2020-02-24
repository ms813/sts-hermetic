package com.ms813.sts.hermetic.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ExhaustBlurEffect;
import com.megacrit.cardcrawl.vfx.ExhaustEmberEffect;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CoinEffects {

    public static void explosion(
        final AbstractCreature owner,
        final int coinCount,
        final float startX,
        final float startY,
        final float width,
        final float height
    ) {
        ellipse(owner, coinCount, startX, startY, width, height, false, true);
    }

    public static void implosion(
        final AbstractCreature owner,
        final int coinCount,
        final float endX,
        final float endY,
        final float width,
        final float height,
        final boolean showGainEffect
    ) {
        ellipse(owner, coinCount, endX, endY, width, height, showGainEffect, false);
    }

    private static void ellipse(
        final AbstractCreature owner,
        final int coinCount,
        final float centerX,
        final float centerY,
        final float width,
        final float height,
        final boolean showGainEffect,
        final boolean outwards
    ) {
        final List<GainPennyEffect> effects = new ArrayList<>();
        for (int i = 0; i < coinCount; i++) {
            final float angle = (float) Math.toRadians(((float) i / coinCount) * 360f);
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);

            float aX, bX, aY, bY;
            if (outwards) {
                aX = centerX;
                aY = centerY;
                bX = centerX + x * width;
                bY = centerY + y * height;
            } else {
                aX = centerX + x * width;
                aY = centerY + y * height;
                bX = centerX;
                bY = centerY;
            }
            effects.add(
                new GainPennyEffect(owner, aX, aY, bX, bY, showGainEffect)
            );
        }
        AbstractDungeon.effectList.addAll(effects);
    }

    public static void randomVacuum(
        final AbstractCreature owner,
        final int coinCount,
        final float endX,
        final float endY,
        final boolean showGainEffect
    ) {
        final List<GainPennyEffect> effects = new ArrayList<>();
        for (int i = 0; i < coinCount; i++) {
            float x = ThreadLocalRandom.current().nextInt(0, Settings.WIDTH);
            float y = ThreadLocalRandom.current().nextInt(0, Settings.HEIGHT);

            effects.add(
                new GainPennyEffect(owner, x, y, Settings.WIDTH - x, Settings.HEIGHT - y, showGainEffect)
            );
        }
        AbstractDungeon.effectList.addAll(effects);
    }

    public static void coinExhaust(final int startX, final int startY, final int endX, final int endY, final int coins){
        AbstractDungeon.effectList.add(new CoinExhaustEffect(startX, startY, endX, endY, coins));
    }

    public static class CoinExhaustEffect extends AbstractGameEffect {

        private final int startX;
        private final int startY;
        private final int endX;
        private final int endY;
        private final int coins;

        CoinExhaustEffect(final int startX, final int startY, final int endX, final int endY, int coins) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.coins = coins;
            this.duration = 1.0f;
        }

        @Override
        public void update() {
            if (this.duration == 1.0F) {
                for(int i = 0; i < coins; i++){
                    AbstractDungeon.effectsQueue.add(new GainPennyEffect(AbstractDungeon.player, startX, startY, endX, endY, false));
                }
            } else if(this.duration <= 0.5f){
                CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);

                int i;
                for (i = 0; i < coins; ++i) {
                    AbstractDungeon.effectsQueue.add(new ExhaustBlurEffect(this.endX, this.endY));
                }

                for (i = 0; i < coins; ++i) {
                    AbstractDungeon.effectsQueue.add(new ExhaustEmberEffect(this.endX, this.endY));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            }
        }

        @Override
        public void render(SpriteBatch spriteBatch) {
        }

        @Override
        public void dispose() {

        }
    }
}
