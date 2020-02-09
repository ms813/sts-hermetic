package com.ms813.sts.hermetic.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bludgeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.relics.UnceasingTop;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.ms813.sts.hermetic.HermeticMod;
import com.ms813.sts.hermetic.cards.test.*;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import com.ms813.sts.hermetic.patches.HermeticPlayerClassPatch;

import java.util.ArrayList;

public class HermeticCharacter extends CustomPlayer {

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 100;
    public static final int MAX_HP = 100;
    public static final int STARTING_GOLD = 49;
    public static final int HAND_SIZE = 5;

    public static final String CHARACTER_IMG_PATH = HermeticMod.IMG_PATH + "char/";
    public static final String MY_CHARACTER_SHOULDER_2 = CHARACTER_IMG_PATH + "valiant_shoulder2.png"; // campfire pose
    public static final String MY_CHARACTER_SHOULDER_1 = CHARACTER_IMG_PATH + "valiant_shoulder.png"; // another campfire pose
    public static final String MY_CHARACTER_CORPSE = CHARACTER_IMG_PATH + "corpse_valiant.png"; // dead corpse
    public static final String MY_CHARACTER_SKELETON_ATLAS = CHARACTER_IMG_PATH + "idle/skeleton.atlas"; // spine animation atlas
    public static final String MY_CHARACTER_SKELETON_JSON = CHARACTER_IMG_PATH + "idle/skeleton.json"; // spine animation json

    private static final String ORB_PATH = CHARACTER_IMG_PATH + "orb/";
    private static final String VALIANT_LAYER1_PATH = ORB_PATH + "layer1.png";
    private static final String VALIANT_LAYER2_PATH = ORB_PATH + "layer2.png";
    private static final String VALIANT_LAYER3_PATH = ORB_PATH + "layer3.png";
    private static final String VALIANT_LAYER4_PATH = ORB_PATH + "layer4.png";
    private static final String VALIANT_LAYER5_PATH = ORB_PATH + "layer5.png";
    private static final String VALIANT_LAYER6_PATH = ORB_PATH + "layer6.png";
    private static final String VALIANT_LAYER1D_PATH = ORB_PATH + "layer1d.png";
    private static final String VALIANT_LAYER2D_PATH = ORB_PATH + "layer2d.png";
    private static final String VALIANT_LAYER3D_PATH = ORB_PATH + "layer3d.png";
    private static final String VALIANT_LAYER4D_PATH = ORB_PATH + "layer4d.png";
    private static final String VALIANT_LAYER5D_PATH = ORB_PATH + "layer5d.png";
    private static final String VALIANT_VFX_PATH = ORB_PATH + "vfx.png";
    private static final String ANIMATION_PATH = CHARACTER_IMG_PATH + "animation/Idle_final_sizetest.scml";

    public static final String[] orbTextures = {
        VALIANT_LAYER1_PATH,
        VALIANT_LAYER2_PATH,
        VALIANT_LAYER3_PATH,
        VALIANT_LAYER4_PATH,
        VALIANT_LAYER5_PATH,
        VALIANT_LAYER6_PATH,
        VALIANT_LAYER1D_PATH,
        VALIANT_LAYER2D_PATH,
        VALIANT_LAYER3D_PATH,
        VALIANT_LAYER4D_PATH,
        VALIANT_LAYER5D_PATH
    };

    public HermeticCharacter(final String name) {
        super(name, HermeticPlayerClassPatch.HERMETIC, orbTextures, VALIANT_VFX_PATH, null, new SpriterAnimation(ANIMATION_PATH));

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 220.0F * Settings.scale);

        initializeClass(null,
            MY_CHARACTER_SHOULDER_2, // required call to load textures and setup energy/loadout
            MY_CHARACTER_SHOULDER_1,
            MY_CHARACTER_CORPSE,
            getLoadout(),
            20.0F,
            -10.0F,
            220.0F,
            290.0F,
            new EnergyManager(ENERGY_PER_TURN)
        );

        loadAnimation(MY_CHARACTER_SKELETON_ATLAS, MY_CHARACTER_SKELETON_JSON, 1.0F);

        final AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        final ArrayList<String> cards = new ArrayList<>();
//        cards.add("Strike_Hermetic");
        cards.add(TestApplyAetherCard.ID);
        cards.add(TestApplyAurumCard.ID);
        cards.add(TestApplySanguisCard.ID);
        cards.add(TestQuickenCard.ID);
        cards.add(TestCoagulateCard.ID);
        cards.add(TestSublimateCard.ID);
        return cards;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        final ArrayList<String> relics = new ArrayList<>();

        relics.add(UnceasingTop.ID);

        UnlockTracker.markRelicAsSeen(UnceasingTop.ID);

        return relics;
    }

    @Override
    public ArrayList<AbstractCard> getCardPool(final ArrayList<AbstractCard> tmpPool) {

        CardLibrary.cards.forEach((cardId, card) -> {
            if (card.color == HermeticCardColorPatch.HERMETICMOD_GOLD
                && card.rarity != AbstractCard.CardRarity.BASIC
                && (!UnlockTracker.isCardLocked(cardId) || Settings.treatEverythingAsUnlocked())) {
                tmpPool.add(card);
            }
        });

//        if (ModHelper.isModEnabled("Red cards")) {
        CardLibrary.addRedCards(tmpPool);
//        }
        if (ModHelper.isModEnabled("Green cards")) {
            CardLibrary.addGreenCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Blue cards")) {
            CardLibrary.addBlueCards(tmpPool);
        }
        if (ModHelper.isModEnabled("Colorless cards")) {
            CardLibrary.addColorlessCards(tmpPool);
        }

        return tmpPool;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
            "Hermetic",
            "Big sword dude",
            STARTING_HP,
            MAX_HP,
            0,
            STARTING_GOLD,
            HAND_SIZE,
            this,
            getStartingRelics(),
            getStartingDeck(),
            false
        );
    }

    @Override
    public String getTitle(final PlayerClass playerClass) {
        return playerClass.name();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return HermeticCardColorPatch.HERMETICMOD_GOLD;
    }

    @Override
    public Color getCardRenderColor() {
        return Color.GOLD.cpy();
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Bludgeon();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.GOLD.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 10;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("HEART_BEAT", MathUtils.random(-0.2F, 0.2F));
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "HEART_BEAT";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Hermetic";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new HermeticCharacter("The Hermetic");
    }

    @Override
    public String getSpireHeartText() {
        return "Schplop that heart";
    }

    @Override
    public Color getSlashAttackColor() {
        return Color.GOLD.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
            AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
            AbstractGameAction.AttackEffect.SLASH_VERTICAL,
            AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    @Override
    public String getVampireText() {
        return "Vampiru no want smerry brood from u";
    }
}
