package service

import entity.*

/**
 * Service-layer Class, that provides logic for all actions, the player can make
 * @property schwimmenService as the rootService
 */
class PlayerService(private val schwimmenService: SchwimmenService) : AbstractRefreshingService(){

    /**
     * sets the hasKnocked-flag to true
     *
     * @throws KotlinNullPointerException if either game or currentPlayer are null
     */
    fun knock(){
        val player = schwimmenService.schwimmen!!.currentPlayer!!
        player.hasKnocked = true
        schwimmenService.gameService.finishTurn()
    }

    /**
     * increases the passCounter in schwimmen obj
     *
     * @throws KotlinNullPointerException if game is null
     */
    fun pass(){
        val game = schwimmenService.schwimmen!!
        game.passCounter++
        schwimmenService.gameService.finishTurn()
    }

    /**
     * swaps the hand cards and middle cards
     *
     * @throws KotlinNullPointerException if either game or currentPlayer are null
     */
    fun swapAll(){
        val player = schwimmenService.schwimmen!!.currentPlayer!!
        val middle =  schwimmenService.schwimmen!!.middle
        val temp = middle.cards
        middle.cards = player.hand
        player.hand = temp
        onAllRefreshables { refreshAfterCardChange() }
        //sleep 500 ms
        schwimmenService.gameService.finishTurn()

    }

    /**
     * swaps the chosen cards from the players hand and the middle
     *
     * @param cardOnHand position of the selected card on Hand (0..2)
     * @param cardOnMiddle position of the selected card in the middle (0..2)
     * @throws KotlinNullPointerException if either game or currentPlayer are null
     * @throws IndexOutOfBoundsException if cardOnHand or CardOnMiddle are not in 0..2
     */
    fun swapOne(cardOnHand:Int,cardOnMiddle:Int){
        if(cardOnHand !in 0..2 || cardOnMiddle !in 0..2){
            throw IndexOutOfBoundsException("Indexes for either cardOnHand or cardOnMiddle are outside of 0,1,2")
        }

        val player = schwimmenService.schwimmen!!.currentPlayer!!
        val middle = schwimmenService.schwimmen!!.middle
        val temp = middle.cards[cardOnMiddle]
        middle.cards[cardOnMiddle] = player.hand[cardOnHand]
        player.hand[cardOnHand] = temp
        onAllRefreshables { refreshAfterCardChange() }
        //sleep 500 ms
        schwimmenService.gameService.finishTurn()
    }

    /**
     * creates a new player
     *
     * if game entitys hav not been initialized, this function does it
     *
     * @param name the name of the new player
     * @return the newly created Player
     * @throws IllegalArgumentException if the playerName is empty
     * @throws NoSuchElementException if the middle has less then 3 cards(impossible)
     * @throws IllegalArgumentException if the 5th player is to be added
     */
    fun createPlayer(name:String) : Player {
        val player : Player
        if (name == "") {
            throw IllegalArgumentException()
        }

        val schwimmen: Schwimmen? = schwimmenService.schwimmen
        if (schwimmen == null) {

            //create new stack, create new middle, create this player
            val deck = crateNewDrawStack()
            val middle = Middle(arrayOf(deck.cards.removeLast(), deck.cards.removeLast(), deck.cards.removeLast()))
            player = Player(name, arrayOf(deck.cards.removeLast(), deck.cards.removeLast(), deck.cards.removeLast()))
            schwimmenService.schwimmen =
                Schwimmen(arrayListOf(player), currentPlayer = player, middle = middle, drawStack = deck)
        }
        else {
            //create this player
            val deck = schwimmen.drawStack
            player = Player(name, arrayOf(deck.cards.removeLast(), deck.cards.removeLast(), deck.cards.removeLast()))
            schwimmen.players.add(player)
            if (schwimmen.players.size > 4) {
                throw IllegalArgumentException()
            }
            /*
            else if(schwimmen.players.size == 4){
                //start game?
                //schwimmenService.gameService.startGame()
            }
            */
        }
        onAllRefreshables { refreshAfterAddPlayer() }
        return player
    }

    /**
     * a helper method, that creates a 32 Card deck
     *
     * @return DrawStack with 32 different cards shuffled
     */
     fun crateNewDrawStack(): DrawStack {
        //creating a 32 card deck
        val valueSet = CardValue.shortDeck()
        val cardList: MutableList<Card> = arrayListOf()
        for (value in valueSet) {
            cardList.add(Card(CardSuit.SPADES, value))
            cardList.add(Card(CardSuit.DIAMONDS, value))
            cardList.add(Card(CardSuit.HEARTS, value))
            cardList.add(Card(CardSuit.CLUBS, value))
        }
        cardList.shuffle()
        return DrawStack(ArrayDeque(cardList))
    }

    /**
     *  return the currentPlayers points
     *
     *  @return Float-value of the current Players
     *  @throws KotlinNullPointerException if either game or currentPlayer are null
     */
    fun getPoints() : Float{
        return schwimmenService.schwimmen!!.currentPlayer!!.getPoints()
    }
}