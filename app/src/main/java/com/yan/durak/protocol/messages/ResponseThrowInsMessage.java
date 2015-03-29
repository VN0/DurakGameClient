package com.yan.durak.protocol.messages;


import com.yan.durak.entities.cards.Card;
import com.yan.durak.protocol.BaseProtocolMessage;
import com.yan.durak.protocol.data.CardData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yan-Home on 12/24/2014.
 */
public class ResponseThrowInsMessage extends BaseProtocolMessage<ResponseThrowInsMessage.ProtocolMessageData> {

    public static final String MESSAGE_NAME = "responseThrowIns";

    public ResponseThrowInsMessage(List<Card> selectedThrowInCards) {
        super();
        setMessageName(MESSAGE_NAME);
        List<CardData> cardDataList = convertCardDataList(selectedThrowInCards);
        setMessageData(new ProtocolMessageData(cardDataList));
    }

    private List<CardData> convertCardDataList(List<Card> possibleThrowInCards) {
        List<CardData> retList = new ArrayList<>();
        for (Card card : possibleThrowInCards) {
            retList.add(new CardData(card.getRank(), card.getSuit()));
        }
        return retList;
    }


    public static class ProtocolMessageData {

        @SerializedName("selectedThrowInCards")
        List<CardData> mSelectedThrowInCards;

        public ProtocolMessageData(List<CardData> selectedThrowInCards) {
            mSelectedThrowInCards = selectedThrowInCards;
        }

        public List<CardData> getSelectedThrowInCards() {
            return mSelectedThrowInCards;
        }
    }
}
