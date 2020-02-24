package com.ms813.sts.hermetic.cards.coagulate;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.CARD_IMG_PATH;

public class AetherShieldCard extends CustomCard {
    private static final Logger logger = LoggerFactory.getLogger(AetherShieldCard.class);

    public static final String ID = "AetherShieldCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.SPECIAL;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AetherShieldCard(final int block, final int n) {
        this();
        this.baseBlock = block;
        this.block = block;
        this.baseMagicNumber = n;
        this.magicNumber = n;
    }

    public AetherShieldCard() {
        super(
            ID,
            cardStrings.NAME,
            String.format(CARD_IMG_PATH, ID),
            cost,
            cardStrings.DESCRIPTION,
            type,
            CardColor.COLORLESS,
            rarity,
            target
        );

        this.exhaust = true;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public AetherShieldCard makeCopy() {
        return new AetherShieldCard(this.baseBlock, this.baseMagicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < baseMagicNumber; i++) {
            this.addToBot(new GainBlockAction(p, block));
        }
    }
}
