package no.itverket;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

class Deck {
    LinkedList<Card> cards;

    Deck() {
        cards = new LinkedList<>();
        for (Suit suit : Suit.values()) {
            for (int i = 1; i < 6; i++) {
                cards.offer(new Card(suit, i));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
