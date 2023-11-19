package service

import entity.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertFailsWith

/**
 * This class test all methods of playerService (except create new Player)
 */
class PlayerServiceTest {
    private lateinit var service : SchwimmenService
    private lateinit var playerService: PlayerService
    private lateinit var game : Schwimmen

    /**
     * create a new playerService and schwimmenService for a clean access
     */
    @BeforeEach
    fun setup(){
        service = SchwimmenService()
        playerService = service.playerService
        playerService.createPlayer("player1")
        playerService.createPlayer("player2")
        game = service.schwimmen!!
    }

    /**
     *  tests knocked
     */
    @Test
    fun knock() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        playerService.knock()
        assert(game.players[0].hasKnocked)
        //both can happen after the end of a turn
        assert(testRefreshable.refreshAfterTurn||testRefreshable.refreshAfterGameEnd)
    }

    /**
     * tests pass
     */
    @Test
    fun pass() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        val passCounter = game.passCounter
        playerService.pass()
        assertEquals(passCounter+1,game.passCounter)
        //both can happen after the end of a turn
        assert(testRefreshable.refreshAfterTurn||testRefreshable.refreshAfterGameEnd)
    }

    /**
     * tests swapAll
     */
    @Test
    fun swapAll() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        val mid = game.middle.cards
        val hand = game.players[0].hand
        playerService.swapAll()
        assertEquals(mid,game.players[0].hand)
        assertEquals(hand,game.middle.cards)
        //both can happen after the end of a turn
        assert(testRefreshable.refreshAfterCardChange)
        assert(testRefreshable.refreshAfterTurn||testRefreshable.refreshAfterGameEnd)
    }

    /**
     * tests swapOne 3 times
     */
    @Test
    fun swapOne() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        val mid = game.middle.cards
        val mid0 = mid[0].toString()
        val mid2 = mid[2].toString()
        playerService.swapOne(0,0)
        assertEquals(mid0,game.players[0].hand[0].toString())
        //checking in 2nd test on player 2 since swap method cycles to through schwimmen.players
        playerService.swapOne(2,2)
        assertEquals(mid2,game.players[1].hand[2].toString())
        //tests if turn ends correctly
        assert(testRefreshable.refreshAfterCardChange)
        assert(testRefreshable.refreshAfterTurn||testRefreshable.refreshAfterGameEnd)
        //check if it fails when parameters are out of bound
        assertFailsWith<IndexOutOfBoundsException> {playerService.swapOne(4,0)  }
        assertFailsWith<IndexOutOfBoundsException> {playerService.swapOne(3,-1)  }

    }

    /**
     * tests getPoints
     *
     * It tests the getPoints in playerService the first 2 tests are on set hands the 3rd is on a random hand form the
     * drawStack
     * after each test the current player needs to be set, since getPoints works on currentPlayer
     */
    @Test
    fun getPoints() {
        //set playerHands to some specific easy to calculate hands
        //player 3 will be random
        playerService.createPlayer("player3")
        game.players[0].hand = arrayOf(Card(CardSuit.CLUBS, CardValue.ACE ),
                                       Card(CardSuit.CLUBS, CardValue.QUEEN),
                                       Card(CardSuit.CLUBS, CardValue.KING))
        game.players[1].hand =  arrayOf(Card(CardSuit.CLUBS, CardValue.ACE ),
                                        Card(CardSuit.DIAMONDS, CardValue.ACE),
                                        Card(CardSuit.HEARTS, CardValue.ACE))
        //needing to change the currentPlayer, so that playerService knows who the current player is
        assertEquals(31f,playerService.getPoints())
        game.currentPlayer = game.players[1]
        assertEquals(30.5f,playerService.getPoints())
        game.currentPlayer = game.players[2]
        assertEquals(game.players[2].getPoints(),playerService.getPoints())
    }
}