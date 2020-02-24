package com.ms813.sts.hermetic.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class DefendHermeticCard extends CustomCard {

    public static final String ID = "DefendHermeticCard";
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final CardRarity rarity = CardRarity.BASIC;
    private static final CardTarget target = CardTarget.SELF;
    private static final CardType type = CardType.SKILL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public DefendHermeticCard() {
        super(
            ID,
            cardStrings.NAME,
            EXAMPLE_IMG_PATH + "cards/Defend.png",
            COST,
            cardStrings.DESCRIPTION,
            type,
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            rarity,
            target
        );

        this.tags.add(CardTags.STARTER_DEFEND);
        this.baseBlock = BLOCK;
        this.block = baseBlock;
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        this.addToBot(new GainBlockAction(player, player, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
