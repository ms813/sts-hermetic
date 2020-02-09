package com.ms813.sts.hermetic.cards.test;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.actions.SublimateAction;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.IMG_PATH;

public class TestSublimateCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(TestSublimateCard.class);

    public static final String ID = "TestSublimateCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.SKILL;
    private static final String assetPath = IMG_PATH + "cards/Blinding Flash.png";

    public TestSublimateCard() {
        super(
            ID,
            "Test Sublimate Card",
            assetPath,
            cost,
            "Sublimate all hermetic essences on target",
            type,
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            rarity,
            target
        );
        this.baseMagicNumber = this.magicNumber = 2;
    }

    @Override
    public void upgrade() {
        this.magicNumber += 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SublimateAction(m, p));
    }
}
