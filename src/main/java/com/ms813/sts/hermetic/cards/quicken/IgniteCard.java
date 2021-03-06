package com.ms813.sts.hermetic.cards.quicken;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ms813.sts.hermetic.HermeticMod.CARD_IMG_PATH;
import static com.ms813.sts.hermetic.HermeticMod.EXAMPLE_IMG_PATH;

public class IgniteCard extends CustomCard {

    private static final Logger logger = LoggerFactory.getLogger(IgniteCard.class);

    public static final String ID = "IgniteCard";
    private static final int cost = 0;
    private static final CardRarity rarity = CardRarity.SPECIAL;
    private static final CardTarget target = CardTarget.ENEMY;
    private static final CardType type = CardType.ATTACK;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IgniteCard(final int dmg, final int n) {
        this();
        this.baseDamage = dmg;
        this.damage = dmg;
        this.baseMagicNumber = n;
        this.magicNumber = n;
    }

    public IgniteCard() {
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
    public IgniteCard makeCopy() {
        return new IgniteCard(this.baseDamage, this.baseMagicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < baseMagicNumber; i++) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
