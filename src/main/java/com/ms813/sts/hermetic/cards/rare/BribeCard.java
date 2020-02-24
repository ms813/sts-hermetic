package com.ms813.sts.hermetic.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.BetterDrawPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.effects.CoinEffects;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.ms813.sts.hermetic.HermeticMod.CARD_IMG_PATH;

public class BribeCard extends CustomCard {
    private static final Logger logger = LoggerFactory.getLogger(BribeCard.class);

    public static final String ID = "BribeCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private boolean played = false;

    public BribeCard() {
        super(
            ID,
            cardStrings.NAME,
            String.format(CARD_IMG_PATH, ID),
            cost,
            cardStrings.DESCRIPTION,
            type,
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            rarity,
            target
        );
        this.baseMagicNumber = 20;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            this.exhaust = false;
        }
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        player.gold -= this.magicNumber;
        if(player.gold < 0){
            player.gold = 0;
        }
        final List<AbstractCard> handBefore = new ArrayList<>(player.hand.group);
        this.addToBot(new BetterDrawPileToHandAction(1));
        final List<AbstractCard> handAfter = new ArrayList<>(player.hand.group);
        handAfter.removeAll(handBefore);

        if(handAfter.size() == 1){
            handAfter.get(0).setCostForTurn(0);
        } else {
            logger.error("More than 1 card in handAfter for Bribe!");
        }

        played = true;
    }

    @Override
    public void onMoveToDiscard() {
        final AbstractPlayer player = AbstractDungeon.player;
        if(this.played){
            CoinEffects.coinExhaust((int) player.hb.cX, (int) player.hb.cY, Settings.WIDTH / 2, Settings.HEIGHT / 2, this.magicNumber);
            this.played = false;
        }
    }

    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        final boolean canUseThis = p.gold >= this.magicNumber;
        final boolean canUseSuper = super.canUse(p, m);

        if (!canUseSuper) {
            return false;
        } else if (!canUseThis) {
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[ThreadLocalRandom.current().nextInt(0, cardStrings.EXTENDED_DESCRIPTION.length)];
            return false;
        }
        return true;
    }
}
