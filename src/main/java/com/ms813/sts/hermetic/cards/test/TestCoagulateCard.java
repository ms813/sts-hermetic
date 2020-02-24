package com.ms813.sts.hermetic.cards.test;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.actions.CoagulateAction;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class TestCoagulateCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(TestCoagulateCard.class);

    public static final String ID = "TestCoagulateCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.SKILL;
    private static final String assetPath = EXAMPLE_IMG_PATH + "cards/Blinding Flash.png";

    public TestCoagulateCard() {
        super(
            ID,
            "Test Coagulate Card",
            assetPath,
            cost,
            "Coagulate all hermetic essences on target",
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
        this.addToBot(new CoagulateAction(m, p));
    }
}
