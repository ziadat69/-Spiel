package entity

import org.junit.jupiter.api.Assertions.*
import kotlin.test.*

/**
 * a class to test the player
 */
class PlayerTest{

    /**
     * test the default with the hand being of the same type
     */
    @Test
    fun testPlayer1(){
        //test data
        val playerName ="player1"
        val card1 = Card(CardSuit.CLUBS,CardValue.ACE)
        val card2 = Card(CardSuit.CLUBS,CardValue.QUEEN)
        val card3 = Card(CardSuit.CLUBS,CardValue.TEN)
        val player = Player(playerName,arrayOf(card1,card2,card3))
        //test cases
        assertEquals(playerName,player.name)
        assertFalse(player.hasKnocked)
        //test points in hand expected 31
        assertEquals(31F,player.getPoints())
    }

    /**
     * test the default with the hand being of the same value this player has later knocked
     */
    @Test
    fun testPlayer2(){
        //test data
        val playerName ="player2"
        val card1 = Card(CardSuit.CLUBS,CardValue.SEVEN)
        val card2 = Card(CardSuit.HEARTS,CardValue.SEVEN)
        val card3 = Card(CardSuit.DIAMONDS,CardValue.SEVEN)
        val player = Player(playerName,arrayOf(card1,card2,card3))
        //test cases
        assertEquals(playerName,player.name)
        assertFalse(player.hasKnocked)
        player.hasKnocked = true
        //set has knocked to true
        assert(player.hasKnocked)
        //test points in hand expected 30.5
        assertEquals(30.5F,player.getPoints())
    }
    /**
     * test, whether the cards of a player can be changed later
     */
    @Test
    fun testPlayer3(){
        //test data
        val playerName ="player3"
        val card1 = Card(CardSuit.CLUBS,CardValue.SEVEN)
        val card2 = Card(CardSuit.HEARTS,CardValue.SEVEN)
        val card3 = Card(CardSuit.DIAMONDS,CardValue.SEVEN)
        val card4 = Card(CardSuit.CLUBS,CardValue.ACE)
        val card5 = Card(CardSuit.SPADES,CardValue.QUEEN)
        val player = Player(playerName,arrayOf(card1,card2,card3))
        //test cases
        //test if value are not same
        assertNotEquals(card4.valueToFloat(),player.hand[0].valueToFloat())
        //swap if card 4 to hand[0]
        player.hand[0] = card4
        //test if value got changed
        assertEquals(card4.valueToFloat(),player.hand[0].valueToFloat())
        //test get points with 3 diffrent suits and symbols expected: 11
        player.hand[1] = card5
        assertEquals(11F,player.getPoints())

    }
}