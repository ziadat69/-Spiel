package entity

import org.junit.jupiter.api.Assertions.*
import kotlin.test.*

/**
 * a class to test the middle
 */
class MiddleTest{

    /**
    *  test functionality of the class Middle
    */
    @Test
    fun middleTest(){
        val card1 = Card(CardSuit.CLUBS,CardValue.SEVEN)
        val card2 = Card(CardSuit.HEARTS,CardValue.SEVEN)
        val card3 = Card(CardSuit.DIAMONDS,CardValue.SEVEN)
        val card4 = Card(CardSuit.CLUBS,CardValue.ACE)
        val card5 = Card(CardSuit.SPADES,CardValue.QUEEN)

        val middle = Middle(arrayOf(card1,card2,card3))
        //cards are the same test with card.value
        assertEquals(card1.valueToFloat(),middle.cards[0].valueToFloat())
        assertEquals(card2.valueToFloat(),middle.cards[1].valueToFloat())
        assertEquals(card3.valueToFloat(),middle.cards[2].valueToFloat())

        //exchange cards then test those cards
        middle.cards[0]= card4
        assertEquals(card4.valueToFloat(),middle.cards[0].valueToFloat())
        //swap the whole array
        middle.cards = arrayOf(card3,card4,card5)
        assertEquals(card3.valueToFloat(),middle.cards[0].valueToFloat())
        assertEquals(card4.valueToFloat(),middle.cards[1].valueToFloat())
        assertEquals(card5.valueToFloat(),middle.cards[2].valueToFloat())
    }
}