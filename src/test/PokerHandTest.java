package test;

import main.HandValue;
import main.PokerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokerHandTest {
    @Test
    public void testInputWrong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("JS 2H 5S 9S"));

        assertEquals("Wrong input. Must be 5 cards", exception.getMessage());
    }

    @Test
    public void testInputCardWrong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new PokerHand("JS 21S 5H 7S KS"));

        assertEquals("Wrong card: 21S", exception.getMessage());
    }

    @Test
    public void testHighCard() {
        PokerHand highCard1 = new PokerHand("JS 2H 5S KD 9S");
        PokerHand highCard2 = new PokerHand("QS TS 5D 7S 9S");
        PokerHand highCard3 = new PokerHand("QS TS AD 7S KS");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(highCard1);
        hands.add(highCard2);
        hands.add(highCard3);

        assertEquals(HandValue.HighCard, highCard1.getHandValue());
        assertEquals(HandValue.HighCard, highCard2.getHandValue());
        assertEquals(HandValue.HighCard, highCard3.getHandValue());

        Collections.sort(hands);
        assertEquals(highCard3, hands.get(0));
        assertEquals(highCard1, hands.get(1));
    }

    @Test
    public void testPair() {
        PokerHand pair1 = new PokerHand("9H 9C KS 3H 2D");
        PokerHand pair2 = new PokerHand("9H 9C 7S 4S 2H");
        PokerHand pair3 = new PokerHand("9H KC KS 4S 2H");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(pair1);
        hands.add(pair2);
        hands.add(pair3);

        assertEquals(HandValue.Pair, pair1.getHandValue());
        assertEquals(HandValue.Pair, pair2.getHandValue());
        assertEquals(HandValue.Pair, pair3.getHandValue());

        Collections.sort(hands);
        assertEquals(pair3, hands.get(0));
        assertEquals(pair1, hands.get(1));
    }

    @Test
    public void testTwoPair() {
        PokerHand twoPair1 = new PokerHand("8H KC 8S 3H KD");
        PokerHand twoPair2 = new PokerHand("9H 9C 4S 7S 4H");
        PokerHand twoPair3 = new PokerHand("9H 9C 3S 4S 3H");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(twoPair1);
        hands.add(twoPair2);
        hands.add(twoPair3);

        assertEquals(HandValue.TwoPairs, twoPair1.getHandValue());
        assertEquals(HandValue.TwoPairs, twoPair2.getHandValue());
        assertEquals(HandValue.TwoPairs, twoPair3.getHandValue());

        Collections.sort(hands);
        assertEquals(twoPair1, hands.get(0));
        assertEquals(twoPair3, hands.get(2));
    }

    @Test
    public void testThreeOfAKind() {
        PokerHand threeOfAKind1 = new PokerHand("8H 8C 8S 3H KD");
        PokerHand threeOfAKind2 = new PokerHand("9H 9C 9S 4S QH");
        PokerHand threeOfAKind3 = new PokerHand("9H 9C 9S 4S 7H");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(threeOfAKind1);
        hands.add(threeOfAKind2);
        hands.add(threeOfAKind3);

        assertEquals(HandValue.ThreeOfAKind, threeOfAKind1.getHandValue());
        assertEquals(HandValue.ThreeOfAKind, threeOfAKind2.getHandValue());
        assertEquals(HandValue.ThreeOfAKind, threeOfAKind3.getHandValue());

        Collections.sort(hands);
        assertEquals(threeOfAKind2, hands.get(0));
        assertEquals(threeOfAKind3, hands.get(1));
    }

    @Test
    public void testStraight() {
        PokerHand straight1 = new PokerHand("JH 9C TS QH 8D");
        PokerHand straight2 = new PokerHand("9C KS JH QD TD");
        PokerHand straight3 = new PokerHand("6H 7C TS 9H 8D");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(straight1);
        hands.add(straight2);
        hands.add(straight3);

        assertEquals(HandValue.Straight, straight1.getHandValue());
        assertEquals(HandValue.Straight, straight2.getHandValue());
        assertEquals(HandValue.Straight, straight3.getHandValue());

        Collections.sort(hands);
        assertEquals(straight2, hands.get(0));
        assertEquals(straight3, hands.get(2));
    }

    @Test
    public void testFlush() {
        PokerHand flush1 = new PokerHand("JH JH TH QH 8H");
        PokerHand flush2 = new PokerHand("9D KD 3D QD TD");
        PokerHand flush3 = new PokerHand("6C 8C TC 9C 8C");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(flush1);
        hands.add(flush2);
        hands.add(flush3);

        assertEquals(HandValue.Flush, flush1.getHandValue());
        assertEquals(HandValue.Flush, flush2.getHandValue());
        assertEquals(HandValue.Flush, flush3.getHandValue());

        Collections.sort(hands);
        assertEquals(flush2, hands.get(0));
        assertEquals(flush3, hands.get(2));
    }

    @Test
    public void testFullHouse() {
        PokerHand fullHouse1 = new PokerHand("JS TS JS TS TS");
        PokerHand fullHouse2 = new PokerHand("JS AS JS AS AS");
        PokerHand fullHouse3 = new PokerHand("4S QS QS 4S QS");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(fullHouse1);
        hands.add(fullHouse2);
        hands.add(fullHouse3);

        assertEquals(HandValue.FullHouse, fullHouse1.getHandValue());
        assertEquals(HandValue.FullHouse, fullHouse2.getHandValue());
        assertEquals(HandValue.FullHouse, fullHouse3.getHandValue());

        Collections.sort(hands);
        assertEquals(fullHouse2, hands.get(0));
        assertEquals(fullHouse1, hands.get(2));
    }

    @Test
    public void testFourOfAKind() {
        PokerHand fourOfAKind1 = new PokerHand("TS TH JD TS TS");
        PokerHand fourOfAKind2 = new PokerHand("AD AS JH AS AH");
        PokerHand fourOfAKind3 = new PokerHand("4D QS QS QH QS");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(fourOfAKind1);
        hands.add(fourOfAKind2);
        hands.add(fourOfAKind3);

        assertEquals(HandValue.FourOfAKind, fourOfAKind1.getHandValue());
        assertEquals(HandValue.FourOfAKind, fourOfAKind2.getHandValue());
        assertEquals(HandValue.FourOfAKind, fourOfAKind3.getHandValue());

        Collections.sort(hands);
        assertEquals(fourOfAKind2, hands.get(0));
        assertEquals(fourOfAKind1, hands.get(2));
    }

    @Test
    public void testStraightFlush() {
        PokerHand straightFlush1 = new PokerHand("JH 9H TH QH 8H");
        PokerHand straightFlush2 = new PokerHand("9C KC JC QC TC");
        PokerHand straightFlush3 = new PokerHand("6S 7S TS 9S 8S");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(straightFlush1);
        hands.add(straightFlush2);
        hands.add(straightFlush3);

        assertEquals(HandValue.StraightFlush, straightFlush1.getHandValue());
        assertEquals(HandValue.StraightFlush, straightFlush2.getHandValue());
        assertEquals(HandValue.StraightFlush, straightFlush3.getHandValue());

        Collections.sort(hands);
        assertEquals(straightFlush2, hands.get(0));
        assertEquals(straightFlush3, hands.get(2));
    }

    @Test
    public void testStraightFlushWin() {
        PokerHand straightFlush = new PokerHand("JH 9H TH QH 8H");
        PokerHand twoPair = new PokerHand("8H KC 8S 3H KD");
        PokerHand highCard = new PokerHand("QS TS 5D 7S 9S");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(straightFlush);
        hands.add(twoPair);
        hands.add(highCard);

        Collections.sort(hands);
        assertEquals(straightFlush, hands.get(0));
    }

    @Test
    public void testTwoPairWin() {
        PokerHand pair = new PokerHand("9H 9C KS 3H 2D");
        PokerHand twoPair = new PokerHand("9H KC 9S 3H KD");
        PokerHand highCard = new PokerHand("QH TS 5D 7S 9S");

        List<PokerHand> hands = new ArrayList<>();
        hands.add(pair);
        hands.add(twoPair);
        hands.add(highCard);

        Collections.sort(hands);
        assertEquals(twoPair, hands.get(0));
    }
}