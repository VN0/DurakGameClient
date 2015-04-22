package com.yan.durak.models;

import com.yan.durak.gamelogic.cards.Card;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by ybra on 20/04/15.
 */
public class PileModel {

    private final Collection<Card> mCardsInPile;
    private final Collection<Card> mUnmodifiableCardsCollection;
    private int mPileIndex;

    public PileModel(int PileIndex) {
        this.mCardsInPile = new HashSet<>();
        this.mUnmodifiableCardsCollection = Collections.unmodifiableCollection(mCardsInPile);
    }

    /**
     * Returns a card with provided rank and suit if it is in the pile
     *
     * @return card if it is in the pile or null otherwise
     */
    public Card getCardByRankAndSuit(String rank, String suit) {
        for (Card card : mCardsInPile) {
            if (card.getRank().equals(rank) && card.getSuit().equals(suit))
                return card;
        }

        return null;
    }

    public void addCard(Card card) {
        mCardsInPile.add(card);
    }

    public void removeCard(Card movedCard) {
        mCardsInPile.remove(movedCard);
    }

    public int getPileIndex() {
        return mPileIndex;
    }

    public void setPileIndex(int pileIndex) {
        mPileIndex = pileIndex;
    }

    public Collection<Card> getCardsInPile() {
        return mUnmodifiableCardsCollection;
    }

    public boolean isCardInPile(Card card) {
        return mCardsInPile.contains(card);
    }

}