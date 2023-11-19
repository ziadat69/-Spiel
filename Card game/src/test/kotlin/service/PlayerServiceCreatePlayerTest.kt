package service

import entity.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import kotlin.test.assertFailsWith

/**
 * a Class that tests, if the createPlayer method works
 */
class PlayerServiceCreatePlayerTest {
    private lateinit var service : SchwimmenService
    private lateinit var playerService: PlayerService

    /**
     * create a new playerService and schwimmenService for a clean access
     */
    @BeforeEach
    fun setup(){
        service = SchwimmenService()
        playerService = service.playerService
    }

    /**
     * tests if the function catches if a player has an empty name
     */
    @Test
    fun createPlayer1() {
        //test if the player can be created with "" as name
        assertFailsWith<IllegalArgumentException> { playerService.createPlayer("") }

    }

    /**
     * positive test, should always work, no bad inputs etc
     */
    @Test
    fun createPlayer2(){
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        // test if 4 player are able to be added
        val name1 = "player1"
        val name2 = "player2"
        val name3 = "player3"
        val name4 = "player4"
        playerService.createPlayer(name1)
        playerService.createPlayer(name2)
        playerService.createPlayer(name3)
        playerService.createPlayer(name4)
        val game = service.schwimmen!!
        //all players are different
        assert(game.players[0].name != game.players[1].name)
        assert(game.players[0].name != game.players[2].name)
        assert(game.players[0].name != game.players[3].name)
        //all players have different first cards
        assert(game.players[0].hand[0] != game.players[1].hand[0])
        assert(game.players[0].hand[0] != game.players[2].hand[0])
        assert(game.players[0].hand[0] != game.players[3].hand[0])
        assert(testRefreshable.refreshAfterAddPlayer)
    }

    /**
     * tests if this method catches if more than 4 players are added
     */
    @Test
    fun createPlayer3(){
        // test if 5 players are unable to be added
        val name1 = "player1"
        val name2 = "player2"
        val name3 = "player3"
        val name4 = "player4"
        val name5 = "player5"
        playerService.createPlayer(name1)
        playerService.createPlayer(name2)
        playerService.createPlayer(name3)
        playerService.createPlayer(name4)
        assertFailsWith<IllegalArgumentException>{playerService.createPlayer(name5)}
    }

    /**
     * tests if this method calls refreshAfterPlayerAdd 4 times
     */
    @Test
    fun createPlayer4(){
        // test if 4 players are able to be added
        val testRefreshable = TestRefreshable()
        service.addRefreshable(testRefreshable)
        val name1 = "player1"
        val name2 = "player2"
        val name3 = "player3"
        val name4 = "player4"
        val name5 = "player5"
        playerService.createPlayer(name1)
        assert(testRefreshable.refreshAfterAddPlayer)
        testRefreshable.reset()
        playerService.createPlayer(name2)
        assert(testRefreshable.refreshAfterAddPlayer)
        testRefreshable.reset()
        playerService.createPlayer(name3)
        assert(testRefreshable.refreshAfterAddPlayer)
        testRefreshable.reset()
        playerService.createPlayer(name4)
        assert(testRefreshable.refreshAfterAddPlayer)
        testRefreshable.reset()

    }

}