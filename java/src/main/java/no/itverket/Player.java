package no.itverket;

import java.util.ArrayList;
import java.util.List;

class Player {

    final private List<Card> hand = new ArrayList<>();
    private int total;
    private boolean isDealer;

    Player(boolean isDealer) {
        this.isDealer = isDealer;
    }

    List<Card> getHand() {
        return hand;
    }

    int getTotal() {
        return total;
    }

    void setTotal(int total) {
        this.total = total;
    }

    boolean isDealer() {
        return isDealer;
    }

}