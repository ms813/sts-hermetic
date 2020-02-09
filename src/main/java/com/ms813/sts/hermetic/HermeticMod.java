package com.ms813.sts.hermetic;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.ms813.sts.hermetic.cards.StrikeHermeticCard;
import com.ms813.sts.hermetic.cards.quicken.IgniteCard;
import com.ms813.sts.hermetic.cards.test.*;
import com.ms813.sts.hermetic.characters.HermeticCharacter;
import com.ms813.sts.hermetic.patches.HermeticCardColorPatch;
import com.ms813.sts.hermetic.patches.HermeticPlayerClassPatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpireInitializer
public class HermeticMod implements EditCharactersSubscriber, EditCardsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber {

    public static final String _IMG_PATH = "img/color/";
    public static final String IMG_PATH = "example/img/";

    private static final Logger logger = LoggerFactory.getLogger(HermeticMod.class);

    public HermeticMod() {
        BaseMod.subscribe(this);
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("@@@@@@@@@@ Initializing HermeticMod @@@@@@@@@@");
        final HermeticMod mod = new HermeticMod();
        final Color bgColor = new Color(255, 215, 0, 1);
        final Color backColor = new Color(255, 215, 0, 1);
        final Color frameColor = new Color(255, 215, 0, 1);
        final Color frameOutlineColor = new Color(255, 215, 0, 1);
        final Color descBoxColor = new Color(255, 215, 0, 1);
        final Color glowColor = new Color(255, 215, 0, 1);
        final String attackBg = _IMG_PATH + "attack_bg_512.png";
        final String skillBg = _IMG_PATH + "skill_bg_512.png";
        final String powerBg = _IMG_PATH + "power_bg_512.png";
        final String energyOrb = _IMG_PATH + "orb.png";
        final String attackBgPortrait = _IMG_PATH + "attack_bg_512.png";
        final String skillBgPortrait = _IMG_PATH + "skill_bg_512.png";
        final String powerBgPortrait = _IMG_PATH + "power_bg_512.png";
        final String energyOrbPortrait = _IMG_PATH + "orb.png";

        BaseMod.addColor(
            HermeticCardColorPatch.HERMETICMOD_GOLD,
            bgColor,
            attackBg, skillBg, powerBg, energyOrb,
            attackBgPortrait, skillBgPortrait, powerBgPortrait,
            energyOrbPortrait, energyOrb
        );
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("HermeticMod::receiveEditCharacters()");
        BaseMod.addCharacter(
            new HermeticCharacter(CardCrawlGame.playerName),
            IMG_PATH + "charSelect/Logo1.png",
            IMG_PATH + "charSelect/Valiant_Select_Screen.png",
            HermeticPlayerClassPatch.HERMETIC
        );
    }

    @Override
    public void receiveEditCards() {
        logger.info("HermeticMod::receiveEditCards()");
        BaseMod.addCard(new StrikeHermeticCard());
        BaseMod.addCard(new IgniteCard());

        loadTestCards();
    }

    private void loadTestCards() {
        BaseMod.addCard(new TestQuickenCard());
        BaseMod.addCard(new TestSublimateCard());
        BaseMod.addCard(new TestCoagulateCard());

        BaseMod.addCard(new TestApplyAetherCard());
        BaseMod.addCard(new TestApplySanguisCard());
        BaseMod.addCard(new TestApplyAurumCard());
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "hermetic.localization.eng/hermeticmod-cards.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "hermetic.localization.eng/hermeticmod-powers.json");
    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(
            new String[]{"aether"},
            "A hermetic essence, reacts to hermetical arts"
        );
        BaseMod.addKeyword(
            new String[]{"quicken"},
            "First of the hermetic arts. Catalyses all hermetic essences, causing offensive effects"
        );
        BaseMod.addKeyword(
            new String[]{"coagulate", "coagulation", "coagulating"},
            "Second of the hermetic arts. Solidifies all hermetic essences, causing defensive effects"
        );
        BaseMod.addKeyword(
            new String[]{"sublimate", "sublimation", "sublimating", "sublime"},
            "Third of the hermetic arts. Vapourise all hermetic essences, causing incapacitating effects"
        );
        BaseMod.addKeyword(
            new String[]{"essence", "essences"},
            "Hermetic essences imbued within the creature. Will react to hermetic arts"
        );

        BaseMod.addKeyword(
            new String[]{"art", "arts"},
            "Hermetic arts activate essences, causing drastically different effects depending on the reaction and composition"
        );

        BaseMod.addKeyword(
            new String[]{"midas"},
            "Gain 5 gold when this card is played"
        );
    }
}
