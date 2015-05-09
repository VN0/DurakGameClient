package com.yan.durak.session.states.impl;

import com.yan.durak.service.ServiceLocator;
import com.yan.durak.service.services.CardsTouchProcessorService;
import com.yan.durak.service.services.PileLayouterManagerService;
import com.yan.durak.service.services.PileManagerService;
import com.yan.durak.session.states.IActivePlayerState;

/**
 * Created by Yan-Home on 4/26/2015.
 */
public class OtherPlayerTurnState implements IActivePlayerState {
    @Override
    public ActivePlayerStateDefinition getStateDefinition() {
        return ActivePlayerStateDefinition.OTHER_PLAYER_TURN;
    }

    @Override
    public void resetState() {

    }

    @Override
    public void applyState() {
        ServiceLocator.locateService(CardsTouchProcessorService.class).unRegister();
        ServiceLocator.locateService(PileLayouterManagerService.class).getPileLayouterForPile(ServiceLocator.locateService(PileManagerService.class).getBottomPlayerPile()).layout();
    }
}
