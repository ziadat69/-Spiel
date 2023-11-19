package entity

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

/**
 * a class to test Cards
 */
class CardTest{

    /**
     * test creation of a card
     */
    @Test
    fun cardTest(){
        val card1 = Card(CardSuit.HEARTS,CardValue.SEVEN)
        //test value expected 7
        assertEquals(7F,card1.valueToFloat())
        //test to String
        assertEquals("♥7",card1.toString())
    }
}