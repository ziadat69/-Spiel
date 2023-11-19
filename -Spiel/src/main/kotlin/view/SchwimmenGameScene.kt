package view

import entity.CardSuit
import entity.CardValue
import service.CardImageLoader
import service.SchwimmenService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Orientation
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.visual.ImageVisual
import tools.aqua.bgw.visual.Visual

/**
 * This is the main Scene for the game. The current player is always at the bottom, indicated by his name and score.
 * He can flip his cards by pressing show cards, and then select a move. swapOne has as default values the left cards,
 * and the last 2 selected cards(of the previous player). The other players are indicated my some covered cards around
 * the table
 *
 */

class SchwimmenGameScene(private val schwimmenService: SchwimmenService):
    BoardGameScene(1920, 1080), Refreshable {

    private var playerCardSelect = 0
    private var middleCardSelect = 0

    private val playerNameLabel = Label(
        width = 100, height = 35,
        posX = 80, posY = 900,
        text = "PlaceHolder"
    )

    private val playerScoreLabel = Label(
        width = 100, height = 35,
        posX = 80, posY = 940,
        text = "Score: "
    )
    private val allCardsSwapButton = Button(
        width = 140, height = 35,
        posX = 600, posY = 1000,
        text = "Swap All Cards"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.playerService.swapAll()
        }
    }
    private val oneCardSwapButton = Button(
        width = 140, height = 35,
        posX = 750, posY = 1000,
        text = "Swap One Card"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.playerService.swapOne(playerCardSelect,middleCardSelect)
        }
    }
    private val passButton = Button(
        width = 140, height = 35,
        posX = 900, posY = 1000,
        text = "Pass"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.playerService.pass()
        }
    }

    private val knockButton = Button(
        width = 140, height = 35,
        posX = 1050, posY = 1000,
        text = "Knock"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.playerService.knock()
        }
    }

    private val showButton =  Button(
        width = 140, height = 35,
        posX = 725, posY = 640,
        text = "show your Cards"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            playerCards.forEach { it.flip() }
            this.isDisabled = true
            allCardsSwapButton.isDisabled = false
            oneCardSwapButton.isDisabled = false
            passButton.isDisabled = false
            knockButton.isDisabled = false
        }
    }

    private var playerCards = LinearLayout<CardView>(
    posX = 600, posY = 680,
    width = 600, height = 200,
    spacing = 5,
    visual = Visual.EMPTY,
    orientation = Orientation.HORIZONTAL,
    alignment = Alignment.TOP_LEFT
    )

    private var middle = LinearLayout<CardView>(
        posX = 600, posY = 280,
        width = 600, height = 200,
        spacing = 5,
        visual = Visual.EMPTY,
        orientation = Orientation.HORIZONTAL,
        alignment = Alignment.TOP_LEFT
    )
    private var cardStack = CardStack<CardView>(
        posX = 1200, posY = 280,
        visual = Visual.EMPTY,
        alignment = Alignment.TOP_LEFT
    )

    init{
        background = ColorVisual(108, 168, 59)
        addComponents(
            playerNameLabel,
            playerScoreLabel,
            allCardsSwapButton,
            oneCardSwapButton,
            passButton,
            knockButton,
            middle,
            cardStack,
            showButton,
            playerCards

        )
    }

    /**
     * Create the other Players around the Table.
     * They are dummy CardViews with no other functionality
     */
    private fun addOtherPlayers(){
        val game = schwimmenService.schwimmen!!
        val cardImageLoader = CardImageLoader()
        for(i in 0 until game.players.size-1){
            var linStack : LinearLayout<CardView>
            when(i) {
                0 -> linStack = LinearLayout(
                    posX = 960, posY = 20,
                    width = 300, height = 100,
                    spacing = 5,
                    visual = Visual.EMPTY,
                    orientation = Orientation.HORIZONTAL,
                    alignment = Alignment.TOP_CENTER
                )

                1 -> linStack = LinearLayout(
                    posX = 20, posY = 540,
                    width = 300, height = 100,
                    spacing = 5,
                    visual = Visual.EMPTY,
                    orientation = Orientation.VERTICAL,
                    alignment = Alignment.TOP_CENTER
                )

                2 -> linStack = LinearLayout(
                    posX = 1900, posY = 540,
                    width = 300, height = 100,
                    spacing = 5,
                    visual = Visual.EMPTY,
                    orientation = Orientation.VERTICAL,
                    alignment = Alignment.TOP_CENTER
                )

                else -> linStack = LinearLayout(
                    posX = 960, posY = 20,
                    width = 300, height = 100,
                    spacing = 5,
                    visual = Visual.EMPTY,
                    orientation = Orientation.HORIZONTAL,
                    alignment = Alignment.TOP_CENTER
                )
            }


            for( j in 0..2){

                val cardView = CardView(
                    height = 100,
                    width = 130,
                    front = ImageVisual(cardImageLoader.frontImageFor(CardSuit.DIAMONDS,CardValue.ACE)),
                    back = ImageVisual(cardImageLoader.backImage))
                linStack.add(cardView)
                }
                addComponents(linStack)
            }
        }


    /**
     * Sets everything up for a player exchange
     *
     * First: change visibility for player swap
     * Then: reload middle and Stack
     * Then: set to current player (score, cards and name)
     *
     */
    override fun refreshAfterTurn() {
        showButton.isDisabled = false
        allCardsSwapButton.isDisabled = true
        oneCardSwapButton.isDisabled = true
        passButton.isDisabled = true
        knockButton.isDisabled = true

        val game = schwimmenService.schwimmen!!
        val cardImageLoader = CardImageLoader()

        //change/set middle
        //call refreshAfterCardChange
        this.refreshAfterCardChange()
        //set cardStack
        cardStack.clear()
        for(card in game.drawStack.cards){
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit,card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage))
            cardStack.add(cardView)
        }

        //set Player stats
        val player =schwimmenService.schwimmen!!.currentPlayer!!
        playerScoreLabel.text = "Score: ${schwimmenService.playerService.getPoints()}"
        playerNameLabel.text = player.name

        //set player cards

        playerCards.clear()
        for ((i, card) in player.hand.withIndex()){
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit,card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage))
            cardView.apply { onMouseClicked ={
                playerCardSelect = i
            }
            }
            playerCards.add(cardView)
        }
    }

    /**
     * this is the exact implementation for changing the middle
     */
    override fun refreshAfterCardChange() {
        val game = schwimmenService.schwimmen!!
        val cardImageLoader = CardImageLoader()
        //change/set middle
        middle.clear()
        for ((i, card) in game.middle.cards.withIndex()){
            val cardView = CardView(
                height = 200,
                width = 130,
                front = ImageVisual(cardImageLoader.frontImageFor(card.cardSuit,card.cardValue)),
                back = ImageVisual(cardImageLoader.backImage))
            cardView.apply { onMouseClicked ={
                middleCardSelect =  i
            } }
            cardView.flip()
            middle.add(cardView)

        }
    }

    /**
     * This initializes the start of a game.
     * It sets the other players up and the buttons
     */
    override fun refreshAfterStartGame() {
        this.refreshAfterTurn()
        addOtherPlayers()
    }

}