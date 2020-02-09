package com.ms813.sts.hermetic.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;

import static com.ms813.sts.hermetic.HermeticMod.IMG_PATH;

public class StrikeHermeticCard extends CustomCard {

    public static final String ID = "Strike_Hermetic";
    public static final String NAME = "Strike";
    public static final String DESCRIPTION = "Deal !D! damage.";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int POOL = 1;
    private static final CardRarity rarity = CardRarity.BASIC;
    private static final CardTarget target = CardTarget.ENEMY;

    public StrikeHermeticCard() {
        super(
            ID,
            NAME,
            IMG_PATH + "cards/Strike.png",
            COST,
            DESCRIPTION,
            AbstractCard.CardType.ATTACK,
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            rarity,
            target
        );

        this.tags.add(CardTags.STARTER_STRIKE);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.actionManager.addToBottom(
            new DamageAction(
                monster,
                new DamageInfo(player, this.damage, this.damageTypeForTurn),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL
            )
        );
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrikeHermeticCard();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
