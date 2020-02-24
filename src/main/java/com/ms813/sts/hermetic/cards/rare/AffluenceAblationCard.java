package com.ms813.sts.hermetic.cards.rare;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import com.ms813.sts.hermetic.powers.AffluenceAblationPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.CARD_IMG_PATH;

public class AffluenceAblationCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(AffluenceAblationCard.class);

    public static final String ID = "AffluenceAblationCard";
    private static final int cost = 2;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.POWER;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AffluenceAblationCard() {
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
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        this.addToBot(new ApplyPowerAction(player, player, new AffluenceAblationPower(player, 1)));
    }
}
