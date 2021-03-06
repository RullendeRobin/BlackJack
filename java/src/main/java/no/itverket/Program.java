package no.itverket;

import java.util.*;

public class Program {

    public static void main(String[] args) {
        final Deck deck = new Deck();
        final Player dealer = new Player(true);
        final Player player = new Player(false);
        final Scanner scanner = new Scanner(System.in);


        //Shuffle deck
        deck.shuffle();

        //Dealer draws
        takeTurn(deck, dealer);
        while (true) {

            System.out.println("Stand, Hit");
            final String read = scanner.nextLine();

            if (read.equals("Hit") || read.equals("h")) {
                takeTurn(deck, player);
                if (player.getTotal() > 21) {
                    System.out.println("Bust, dealer wins.");
                    break;
                }
            } else if (read.equals("Stand") || read.equals("s")) {
                while (dealer.getTotal() < 17) {
                    takeTurn(deck, dealer);
                }
                announceWinner(dealer.getTotal(), player.getTotal());
                break;
            }
        }
    }

    //Prints current status of the game
    private static void prettyPrint(Card card, int total, boolean isDealer) {
        int rank = card.rank;
        String msg;
        if (isDealer) {
            msg = "Dealer has drawn %s %s. Total is %s";
        } else {
            msg = "Hit with %s %s. Your total is %s";
        }
        if (rank > 10 || rank == 1) {
            System.out.println(String.format(msg, card.suit, Face.getFace(rank), total));
        } else {
            System.out.println(String.format(msg, card.suit, card.rank, total));
        }
    }

    //Calculates total score of hand
    private static int calculateTotal(List<Card> hand) {
        int total = 0;
        int aces = 0;

        for (Card card : hand) {
            //Check if card is an Ace
            if (Face.getFace(card.rank) == Face.A) {
                aces += 1;
            } else {
                total += Math.min(card.rank, 10);
            }
        }
        //
        if (aces != 0) {
            NavigableSet<Integer> allCombinations = new TreeSet<>();
            //Add baseline
            allCombinations.add(total);
            //Loops through all aces and adds all possible combinations
            for (int i = 0; i < aces; i++) {
                Set<Integer> newCombinations = new HashSet<>();
                for (int j : allCombinations) {
                    newCombinations.addAll(Arrays.asList(j+1, j+11));
                }
                //Remove old combinations and add new
                allCombinations.clear();
                allCombinations.addAll(newCombinations);
            }

            try {
                //Greatest value less than or equal to 21, null if none
                total = allCombinations.floor(21);
            } catch (NullPointerException e) {
                total = allCombinations.first();
            }
        }

        return total;
    }

    //Draws a card from the deck
    private static Card drawCard(Deck deck, List<Card> hand) {
        final Card card = deck.cards.remove();
        hand.add(card);
        return card;
    }

    //Draws a card and prints updated results, returns new total
    private static void takeTurn(Deck deck, Player player) {
        List<Card> hand = player.getHand();
        Card card = drawCard(deck, hand);
        int total = calculateTotal(hand);
        prettyPrint(card, total, player.isDealer());
        player.setTotal(total);
    }

    //Declares winner
    private static void announceWinner(int dealerTotal, int playerTotal) {
        if (dealerTotal > 21) {
            System.out.println("Dealer busts, congratulations, you have won.");
        } else if (dealerTotal > playerTotal) {
            System.out.println("Dealer has won.");
        } else if (dealerTotal < playerTotal) {
            System.out.println("Congratulations, you have won.");
        } else {
            System.out.println("The round has ended in a draw.");
        }
    }

}
