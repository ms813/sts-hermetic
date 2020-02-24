package com.ms813.sts.hermetic.cards.basic;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import com.ms813.sts.hermetic.powers.AetherPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.CARD_IMG_PATH;

public class FirstPrinciplesCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(FirstPrinciplesCard.class);

    public static final String ID = "FirstPrinciplesCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.BASIC;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.SKILL;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public FirstPrinciplesCard() {
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
        this.baseBlock = 3;
        this.block = this.baseBlock;
        this.baseMagicNumber = 1;
        this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        if(!upgraded){
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(final AbstractPlayer player, final AbstractMonster monster) {
        this.addToBot(new GainBlockAction(player, this.block));
        this.addToBot(new ApplyPowerAction(monster, player, new AetherPower(monster, this.magicNumber)));
    }
}
