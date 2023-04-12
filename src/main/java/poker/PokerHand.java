package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static poker.HandValue.*;
import static poker.HandValue.ROYAL_FLUSH;

public class PokerHand implements Comparable<PokerHand> {
    private List<Card> cards = new ArrayList<>();
    private List<Rank> ranks = new ArrayList<>();
    private HandValue handValue;

    public PokerHand(String hand) {
        String[] inputCards = hand.split(" ");
        if (inputCards.length != 5)
            throw new IllegalArgumentException("Wrong input. Must be 5 cards");
        for (String c: inputCards) {
            String[] values = c.split("");
            Rank rank = null;
            Suit suit = null;
            try {
                for (Rank r: Rank.values()) {
                    if (r.getName().equals(values[0]))
                        rank = r;
                }
                if (rank == null)
                    throw  new IllegalArgumentException();
                suit = Suit.valueOf(values[1]);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Wrong card: " + c);
            }

            this.cards.add(new Card(rank, suit));
        }

        Collections.sort(this.cards);
        determineHandValue();
    }

    /**
     * Determining hand value
     */
    private void determineHandValue() {
        // number of cards of different ranks
        long countRanks = cards.stream().map(Card::getRank).distinct().count();

        // grouping cards by rank
        Map<Rank, Long> groupedCard = cards.stream().collect(groupingBy(Card::getRank, counting()));
        // extract ranks and sort by combination and then by highest card
        ranks = groupedCard.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .map(Map.Entry::getKey).collect(Collectors.toList());

        // Check if it is flush
        boolean flush = cards.stream().map(Card::getSuit).distinct().count() == 1;

        if (countRanks == 5 && checkStraight(ranks)) { // Straight or StraightFlush
            handValue = flush ? ranks.get(0).equals(Rank.A) ? ROYAL_FLUSH : STRAIGHT_FLUSH : STRAIGHT;
        } else if (countRanks == 2) { // FullHouse or FourOfAKind
            handValue = groupedCard.containsValue(3L) ? FULL_HOUSE : FOUR_OF_A_KIND;
        } else if (flush){  // Flush or HighCard
            handValue = FLUSH;
        } else if (countRanks == 3) {  // TwoPairs or ThreeOfAKind
            handValue = groupedCard.containsValue(2L) ? TWO_PAIRS : THREE_OF_A_KIND;
        } else if (countRanks == 4) { // Pair
            handValue = PAIR;
        } else { // HighCard
            handValue = HIGH_CARD;
        }

    }

    public List<Rank> getRanks() {
        return ranks;
    }

    public HandValue getHandValue() {
        return handValue;
    }

    @Override
    public int compareTo(PokerHand other) {
        return comparing(PokerHand::getHandValue)
                .thenComparing(this::compareHighestCard)
                .compare(this, other);
    }

    @Override
    public String toString() {
        return handValue + ": " + cards;
    }

    /**
     * Compare two hands by highest card
     * @param hand1 - first hand
     * @param hand2 - second hand
     * @return a negative integer, zero, or a positive integer as hand1 the highest cart is less than,
     *          equal to, or greater than hand2 the highest cart.
     */
    private int compareHighestCard(PokerHand hand1, PokerHand hand2) {
        int cmp = 0;
        for (int i = 0; i < hand1.getRanks().size(); i++) {
            cmp = hand1.getRanks().get(i).compareTo(hand2.getRanks().get(i));
            if (cmp != 0) return cmp;
        }
        return cmp;
    }

    /**
     * Check if hand value is Straight
     * @return true if it is straight, false otherwise
     */
    private boolean checkStraight(List<Rank> ranks) {
        for (int i = 0; i < ranks.size()-1; i++) {
            if ((ranks.get(i).getValue() - ranks.get(i+1).getValue()) != 1)
                return false;
        }
        return true;
    }
}
