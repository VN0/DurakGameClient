package com.yan.durak.msg_processor.subprocessors.impl;

import com.yan.durak.communication.sender.GameServerMessageSender;
import com.yan.durak.gamelogic.cards.Card;
import com.yan.durak.gamelogic.communication.protocol.data.CardData;
import com.yan.durak.gamelogic.communication.protocol.messages.RequestRetaliatePilesMessage;
import com.yan.durak.models.PileModel;
import com.yan.durak.msg_processor.subprocessors.BaseMsgSubProcessor;
import com.yan.durak.service.ServiceLocator;
import com.yan.durak.service.services.HudManagementService;
import com.yan.durak.service.services.PileLayouterManagerService;
import com.yan.durak.service.services.PileManagerService;
import com.yan.durak.session.GameInfo;
import com.yan.durak.session.states.impl.OtherPlayerTurnState;
import com.yan.durak.session.states.impl.RetaliationState;

import java.util.ArrayList;
import java.util.List;

import glengine.yan.glengine.nodes.YANButtonNode;
import glengine.yan.glengine.util.object_pool.YANObjectPool;

/**
 * Created by ybra on 17/04/15.
 */
public class RequestRetaliatePilesMsgSubProcessor extends BaseMsgSubProcessor<RequestRetaliatePilesMessage> {

    private final GameServerMessageSender mMessageSender;
    private final List<List<Card>> mRetaliationList;
    private final GameInfo mGameInfo;

    public RequestRetaliatePilesMsgSubProcessor(final GameInfo gameInfo, final GameServerMessageSender messageSender) {
        super();
        this.mMessageSender = messageSender;
        this.mRetaliationList = new ArrayList<>();
        this.mGameInfo = gameInfo;

        //take button can be used to take all the field piles to player's hand
        setupTakeButton(gameInfo);
    }

    private void setupTakeButton(final GameInfo gameInfo) {
        //take click listener is cached by the hud fragment , so no need to cache it locally
        ServiceLocator.locateService(HudManagementService.class).setTakeButtonClickListener(new YANButtonNode.YanButtonNodeClickListener() {

            //used to cache cards for later removal
            private List<Card> _cardsToRemoveCachedList;

            @Override
            public void onButtonClick() {
                //hide the take button
                ServiceLocator.locateService(HudManagementService.class).hideTakeButton();

                PileManagerService pileManagerService = ServiceLocator.locateService(PileManagerService.class);

                _cardsToRemoveCachedList = new ArrayList<>();

                //return all field pile cards to player hands
                for (PileModel pileModel : pileManagerService.getFieldPiles()) {

                    _cardsToRemoveCachedList.clear();
                    for (Card cardInFieldPile : pileModel.getCardsInPile()) {

                        //add this card to player pile
                        pileManagerService.getBottomPlayerPile().addCard(cardInFieldPile);
                        //remove the card from field pile
                        _cardsToRemoveCachedList.add(cardInFieldPile);
                    }

                    //remove all the cards that we moved to player hands from this pile
                    for (Card card : _cardsToRemoveCachedList) {
                        pileModel.removeCard(card);
                    }
                }

                //disable the hand of the player by setting another state
                gameInfo.setActivePlayerState(YANObjectPool.getInstance().obtain(OtherPlayerTurnState.class));

                //layout the bottom player pile
                ServiceLocator.locateService(PileLayouterManagerService.class).getPileLayouterForPile(pileManagerService.getBottomPlayerPile()).layout();

                //just send empty message to the
                //send the response
                ServiceLocator.locateService(GameServerMessageSender.class).sendResponseRetaliatePiles(null);
            }
        });
    }

    @Override
    public void processMessage(RequestRetaliatePilesMessage serverMessage) {

        if (!(mGameInfo.getActivePlayerState() instanceof RetaliationState))
            throw new IllegalStateException("Currently game must be at Retaliation state , but was at " + mGameInfo.getActivePlayerState());

        RetaliationState retaliationState = (RetaliationState) mGameInfo.getActivePlayerState();
        retaliationState.resetState();

        //retaliation set should be clean at this point
        List<RetaliationState.RetaliationSet> pendingRetaliationSets = retaliationState.getPendingRetaliationCardSets();

        //each list/pile should contain only one card that is pending retaliation
        for (List<CardData> cardDatas : serverMessage.getMessageData().getPilesBeforeRetaliation()) {

            //TODO : Pool , not allocate
            Card pendingCard = new Card(cardDatas.get(0).getRank(), cardDatas.get(0).getSuit());

            //add the card as a covered that waiting retaliation
            RetaliationState.RetaliationSet retSet = YANObjectPool.getInstance().obtain(RetaliationState.RetaliationSet.class);
            retSet.setCoveredCard(pendingCard);

            //add set to pending retaliation sets
            pendingRetaliationSets.add(retSet);
        }

        //raise take button
        ServiceLocator.locateService(HudManagementService.class).showTakeButton();

    }
}