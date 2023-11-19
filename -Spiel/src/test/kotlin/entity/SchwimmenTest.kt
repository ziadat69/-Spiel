package entity

import org.junit.jupiter.api.Assertions.*

import kotlin.test.*

/**
 * a class to test the schwimmen class
 */
class SchwimmenTest{


    //some objects, that are needed for schwimmen
    //middle
    private val middle = Middle(
        arrayOf(
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.SEVEN),
            Card(CardSuit.CLUBS, CardValue.SEVEN))
    )
    //drawStack
    private val cardList = listOf(
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN))
    private var drawStack = DrawStack(ArrayDeque(cardList))

    //generic hand for players
    private val cardArr = arrayOf(
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN),
        Card(CardSuit.CLUBS, CardValue.SEVEN))


    /**
     * test getter for players array
     */
    @Test
    fun getPlayers() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        assertEquals(2,schwimmen.players.size)
        assertEquals("Player1",schwimmen.players[0].name)
    }

    /**
     * test getter for passCounter
     */
    @Test
    fun getPassCounter() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        assertEquals(0,schwimmen.passCounter)
    }

    /**
     * test setter for passCounter
     */
    @Test
    fun setPassCounter() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)
        schwimmen.passCounter = 1
        assertEquals(1,schwimmen.passCounter)
    }

    /**
     * test getter for currentPlayer
     */
    @Test
    fun getCurrentPlayer() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        assertEquals(null,schwimmen.currentPlayer)
        schwimmen.currentPlayer = schwimmen.players[0]
        assertEquals("Player1",schwimmen.currentPlayer?.name)
    }

    /**
     * test setter for currentPlayer
     */
    @Test
    fun setCurrentPlayer() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        schwimmen.currentPlayer = schwimmen.players[0]
        assertEquals("Player1",schwimmen.currentPlayer?.name)
    }

    /**
     * test getter for middle
     */
    @Test
    fun getMiddle() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        assertEquals(middle.cards[0].cardValue,schwimmen.middle.cards[0].cardValue)
    }

    /**
     * test getter for drawStack
     */
    @Test
    fun getDrawStack() {
        val player1 = Player("Player1",cardArr)
        val player2 = Player("Player2",cardArr)
        val playerArr = ArrayList(listOf(player1,player2))
        val schwimmen = Schwimmen(playerArr,0, middle = middle, drawStack = drawStack)

        assertEquals(drawStack.cards.first().cardValue,schwimmen.drawStack.cards.first().cardValue)
    }
}