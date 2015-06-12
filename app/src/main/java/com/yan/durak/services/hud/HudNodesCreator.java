package com.yan.durak.services.hud;

import com.yan.durak.screens.BaseGameScreen;

import glengine.yan.glengine.assets.YANAssetManager;
import glengine.yan.glengine.assets.atlas.YANTextureAtlas;
import glengine.yan.glengine.nodes.YANBaseNode;
import glengine.yan.glengine.nodes.YANButtonNode;
import glengine.yan.glengine.nodes.YANCircleNode;
import glengine.yan.glengine.nodes.YANTextNode;
import glengine.yan.glengine.nodes.YANTexturedNode;
import glengine.yan.glengine.service.ServiceLocator;
import glengine.yan.glengine.util.colors.YANColor;
import glengine.yan.glengine.util.loggers.YANLogger;

/**
 * Created by yan.braslavsky on 6/12/2015.
 */
public class HudNodesCreator {

    private static final YANColor SPEECH_BUBBLE_TEXT_COLOR = YANColor.createFromHexColor(0x4F3723);

    private final HudManagementService mHudManagementService;

    public HudNodesCreator(HudManagementService hudManagementService) {
        mHudManagementService = hudManagementService;
    }

    public void createNodes(YANTextureAtlas hudAtlas) {
        //add image of glade
        putToNodeMap(HudNodes.GLADE_INDEX, createGladeImage(hudAtlas));

        //add image of fence
        putToNodeMap(HudNodes.FENCE_INDEX, createFenceImage(hudAtlas));

        //add image of the trump
        putToNodeMap(HudNodes.TRUMP_IMAGE_INDEX, createTrumpImage(hudAtlas));

        //create avatars
        putToNodeMap(HudNodes.AVATAR_BG_BOTTOM_RIGHT_INDEX, createAvatar(hudAtlas));
        putToNodeMap(HudNodes.AVATAR_BG_TOP_RIGHT_INDEX, createAvatar(hudAtlas));
        putToNodeMap(HudNodes.AVATAR_BG_TOP_LEFT_INDEX, createAvatar(hudAtlas));

        //speech bubbles
        putToNodeMap(HudNodes.BOTTOM_SPEECH_BUBBLE_INDEX, createSpeechBubble(hudAtlas));
        putToNodeMap(HudNodes.TOP_RIGHT_SPEECH_BUBBLE_INDEX, createSpeechBubble(hudAtlas));
        putToNodeMap(HudNodes.TOP_LEFT_SPEECH_BUBBLE_INDEX, createSpeechBubble(hudAtlas));

        //speechBubble text
        putToNodeMap(HudNodes.BOTTOM_SPEECH_BUBBLE_TEXT_INDEX, createSpeechBubbleText(hudAtlas));
        putToNodeMap(HudNodes.TOP_RIGHT_SPEECH_BUBBLE_TEXT_INDEX, createSpeechBubbleText(hudAtlas));
        putToNodeMap(HudNodes.TOP_LEFT_SPEECH_BUBBLE_TEXT_INDEX, createSpeechBubbleText(hudAtlas));

        //create avatar_1 icons
        putToNodeMap(HudNodes.AVATAR_ICON_BOTTOM_RIGHT_INDEX, createAvatarIcon(hudAtlas, "avatar_1.png"));
        putToNodeMap(HudNodes.AVATAR_ICON_TOP_RIGHT_INDEX, createAvatarIcon(hudAtlas, "avatar_2.png"));
        putToNodeMap(HudNodes.AVATAR_ICON_TOP_LEFT_INDEX, createAvatarIcon(hudAtlas, "avatar_3.png"));

        //create timers
        putToNodeMap(HudNodes.CIRCLE_TIMER_BOTTOM_RIGHT_INDEX, createCircleTimer());
        putToNodeMap(HudNodes.CIRCLE_TIMER_TOP_RIGHT_INDEX, createCircleTimer());
        putToNodeMap(HudNodes.CIRCLE_TIMER_TOP_LEFT_INDEX, createCircleTimer());

        //create action buttons
        putToNodeMap(HudNodes.DONE_BUTTON_INDEX, createDoneButton(hudAtlas));
        putToNodeMap(HudNodes.TAKE_BUTTON_INDEX, createTakeButton(hudAtlas));

        //end game popups
        putToNodeMap(HudNodes.YOU_WIN_IMAGE_INDEX, createYouWonImage(hudAtlas));
        putToNodeMap(HudNodes.YOU_LOOSE_IMAGE_INDEX, createYouLooseImage(hudAtlas));

        //create v button for popup
        putToNodeMap(HudNodes.V_BUTTON_INDEX, createVButton(hudAtlas));

        //TODO : add back card image to the hud atlas
        //create v button for popup
        putToNodeMap(HudNodes.MASK_CARD_INDEX, createMaskCard(hudAtlas));

        putToNodeMap(HudNodes.GLOW_INDEX, createCardGlow(hudAtlas));
        putToNodeMap(HudNodes.ROOF_INDEX, createRoof(hudAtlas));

    }

    private YANButtonNode createVButton(YANTextureAtlas hudAtlas) {
        YANButtonNode node = new YANButtonNode(hudAtlas.getTextureRegion("v_btn.png"), hudAtlas.getTextureRegion("v_btn_clicked.png"));
        node.setClickListener(new YANButtonNode.YanButtonNodeClickListener() {
            @Override
            public void onButtonClick() {
                YANLogger.log("v button clicked");
            }
        });
        node.setSortingLayer(HudManagementService.HUD_SORTING_LAYER - 2);
        return node;
    }

    private YANTexturedNode createMaskCard(YANTextureAtlas hudAtlas) {
        YANTexturedNode maskCard = new YANTexturedNode(hudAtlas.getTextureRegion("cards_back.png"));
        maskCard.setSortingLayer(HudManagementService.HUD_SORTING_LAYER);
        return maskCard;
    }

    private YANTexturedNode createYouWonImage(YANTextureAtlas hudAtlas) {
        YANTexturedNode popupImage = new YANTexturedNode(hudAtlas.getTextureRegion("you_won.png"));
        popupImage.setSortingLayer(HudManagementService.HUD_SORTING_LAYER - 3);
        return popupImage;
    }

    private YANTexturedNode createYouLooseImage(YANTextureAtlas hudAtlas) {
        YANTexturedNode popupImage = new YANTexturedNode(hudAtlas.getTextureRegion("you_lose.png"));
        popupImage.setSortingLayer(HudManagementService.HUD_SORTING_LAYER - 3);
        return popupImage;
    }

    private YANTexturedNode createGladeImage(YANTextureAtlas hudAtlas) {
        return new YANTexturedNode(hudAtlas.getTextureRegion("glade.png"));
    }

    private YANTexturedNode createFenceImage(YANTextureAtlas hudAtlas) {
        YANTexturedNode image = new YANTexturedNode(hudAtlas.getTextureRegion("fence.png"));
        image.setSortingLayer(HudManagementService.HUD_SORTING_LAYER);
        return image;
    }

    private YANTexturedNode createTrumpImage(YANTextureAtlas hudAtlas) {
        YANTexturedNode trumpImage = new YANTexturedNode(hudAtlas.getTextureRegion("trump_marker_hearts.png"));
        trumpImage.setSortingLayer(-50);
        return trumpImage;
    }

    private YANButtonNode createTakeButton(YANTextureAtlas hudAtlas) {
        return new YANButtonNode(hudAtlas.getTextureRegion("btn_take.png"), hudAtlas.getTextureRegion("btn_take.png"));
    }

    private YANButtonNode createDoneButton(YANTextureAtlas hudAtlas) {
        return new YANButtonNode(hudAtlas.getTextureRegion("btn_done.png"), hudAtlas.getTextureRegion("btn_done.png"));
    }

    private YANBaseNode createSpeechBubbleText(YANTextureAtlas hudAtlas) {
        YANTextNode yanTextNode = new YANTextNode(ServiceLocator.locateService(YANAssetManager.class).getLoadedFont(BaseGameScreen.SPEECH_BUBBLES_FONT_NAME), "I will Take This !".length());
        yanTextNode.setTextColor(SPEECH_BUBBLE_TEXT_COLOR.getR(), SPEECH_BUBBLE_TEXT_COLOR.getG(), SPEECH_BUBBLE_TEXT_COLOR.getB());

        //we are setting the longest text that will be used
        yanTextNode.setText(HudNodes.SPEECH_BUBBLE_TAKING_TEXT);
        return yanTextNode;
    }

    private YANBaseNode createSpeechBubble(YANTextureAtlas hudAtlas) {
        return new YANTexturedNode(hudAtlas.getTextureRegion("speech_bubble.png"));
    }

    private YANBaseNode createRoof(YANTextureAtlas hudAtlas) {
        return new YANTexturedNode(hudAtlas.getTextureRegion("roof.png"));
    }

    private YANCircleNode createCircleTimer() {
        YANCircleNode yanCircleNode = new YANCircleNode();
        yanCircleNode.setColor(HudManagementService.TIMER_RETALIATION_COLOR.getR(),
                HudManagementService.TIMER_RETALIATION_COLOR.getG(),
                HudManagementService.TIMER_RETALIATION_COLOR.getB());
        yanCircleNode.setClockWiseDraw(false);
        yanCircleNode.setPieCirclePercentage(1f);
        return yanCircleNode;
    }

    private YANTexturedNode createAvatar(YANTextureAtlas hudAtlas) {
        YANTexturedNode avatar = new YANTexturedNode(hudAtlas.getTextureRegion("stump_bg.png"));
        avatar.setSortingLayer(HudManagementService.HUD_SORTING_LAYER);
        return avatar;
    }

    private YANTexturedNode createAvatarIcon(YANTextureAtlas hudAtlas, String avatarTextureName) {
        return new YANTexturedNode(hudAtlas.getTextureRegion(avatarTextureName));
    }

    private YANTexturedNode createCardGlow(YANTextureAtlas hudAtlas) {
        return new YANTexturedNode(hudAtlas.getTextureRegion("card_glow.png"));
    }

    protected <T extends YANBaseNode> void putToNodeMap(@HudNodes.HudNode int nodeIndex, T node) {
        mHudManagementService.putToNodeMap(nodeIndex, node);
    }
}