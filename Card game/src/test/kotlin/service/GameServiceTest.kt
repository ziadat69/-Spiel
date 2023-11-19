package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Schwimmen
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * This class tests methods of GameService
 */
class GameServiceTest {

    private lateinit var service : SchwimmenService
    private lateinit var gameService : GameService
    private lateinit var playerService: PlayerService
    private lateinit var game : Schwimmen

    /**
     * create a new gameService and schwimmenService for a clean access
     */
    @BeforeEach
    fun setup(){
        service = SchwimmenService()
        playerService = service.playerService
        gameService = service.gameService
        playerService.createPlayer("player1")
        playerService.createPlayer("player2")
        game = service.schwimmen!!
    }

    /**
     * tests gameStart
     */
    @Test
    fun startGame() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        gameService.startGame()
        assertNotNull(game.currentPlayer)
        assert(testRefreshable.refreshAfterStartGame)
    }


    /**
     * tests finishTurn
     *
     * The first block checks if a private helper method calculates teh next player correctly
     * The 2nd block checks if the hasKocked flag has influence over the game ending
     * The 3rd block check if the drawStack size decrees after enough passes
     * The last block checks if an empty stack can end the game
     */
    @Test
    fun finishTurn() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        //test if calculate [calculateNextCurrentPlayer] does the right thing, by comparing their names
        game.currentPlayer = game.players[0]
        gameService.finishTurn()
        assertEquals(game.players[1].name,game.currentPlayer!!.name)
        gameService.finishTurn()
        assertEquals(game.players[0].name,game.currentPlayer!!.name)
        assert(testRefreshable.refreshAfterTurn)
        testRefreshable.reset()
        //test if it sets the next player then evaluates has knocked
        game.currentPlayer!!.hasKnocked = true
        gameService.finishTurn()
        assert(!game.currentPlayer!!.hasKnocked)
        //evaluate when the player is finished and the game calls finish game
        gameService.finishTurn()
        //checking if game has finished
        assert(game.currentPlayer!!.hasKnocked)
        assert(testRefreshable.refreshAfterGameEnd)
        testRefreshable.reset()
        //reset has knocked flag
        game.players.forEach { player -> player.hasKnocked = false }

        //checking if the drawStack gets smaller
        val drawSize = game.drawStack.cards.size
        game.passCounter++
        gameService.finishTurn()
        assertEquals(1,game.passCounter)
        assertEquals(drawSize,game.drawStack.cards.size)
        //now it should make a new middle
        game.passCounter++
        gameService.finishTurn()
        assertEquals(0,game.passCounter)
        assertNotEquals(drawSize,game.drawStack.cards.size)

        //test if an empty stack finishes the game
        game.drawStack.cards = ArrayDeque(arrayListOf(Card(CardSuit.DIAMONDS,CardValue.ACE)))
        game.passCounter = 2
        gameService.finishTurn()
        //test if game is finished

        assert(testRefreshable.refreshAfterGameEnd)
        testRefreshable.reset()
    }

    /**
     * test if the game can be ended
     *
     * How does one test this
     */
    @Test
    fun endGame() {
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        gameService.endGame()
        assert(testRefreshable.refreshAfterGameEnd)
        testRefreshable.reset()
    }

    /**
     * Tests if calculateScore return a correctly ordered List
     */
    @Test
    fun calculateScore() {
        //set player 2 to a 31 points hand
        //set player 1 to a 30.5 points hand
        game.players[0].hand = arrayOf(Card(CardSuit.CLUBS, CardValue.ACE ),
                                       Card(CardSuit.DIAMONDS, CardValue.ACE),
                                       Card(CardSuit.HEARTS, CardValue.ACE))

        game.players[1].hand = arrayOf(Card(CardSuit.CLUBS, CardValue.ACE ),
                                       Card(CardSuit.CLUBS, CardValue.QUEEN),
                                       Card(CardSuit.CLUBS, CardValue.KING))
        val list = gameService.calculateScore()
        assertEquals(game.players[1].name,list[0].first)
        assertEquals(31f,list[0].second)
        assertEquals(game.players[0].name,list[1].first)
        assertEquals(30.5f,list[1].second)
        }

    /**
     * Tests a getter for schwimmenService
     */

    @Test
    fun getSchwimmenService() {
        assertEquals(service.schwimmen!!.players[0].name,gameService.schwimmenService.schwimmen!!.players[0].name)
    }

}