package com.ms813.sts.hermetic.cards.test;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import com.ms813.sts.hermetic.powers.AurumPower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class TestApplyAurumCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(TestApplyAurumCard.class);

    public static final String ID = "TestApplyAurumCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.RARE;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.SKILL;
    private static final String assetPath = EXAMPLE_IMG_PATH + "cards/Tear Soul.png";

    public TestApplyAurumCard() {
        super(
            ID,
            "Test Apply Aurum Card",
            assetPath,
            cost,
            "Apply !M! Aurum essence",
            type,
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            rarity,
            target
        );
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void upgrade() {
        this.magicNumber += 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new AurumPower(m, this.magicNumber)));
    }
}
