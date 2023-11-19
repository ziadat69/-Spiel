package view

import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import service.SchwimmenService
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual

/**
 * This scene contains a text input for the player names.
 *
 * "Add Player" adds the current player name to the game and asks for the next one.
 * "Start Game" gets  enabled after 2 players added and starts the game.
 */
class NewGameMenuScene(private val schwimmenService: SchwimmenService): MenuScene(400,1080), Refreshable {
    private var playerCount = 0


    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "Start New Game",
        font = Font(size = 22)
    )

    private val p1Label = Label(
        width = 100, height = 35,
        posX = 50, posY = 125,
        text = "Add Player #${playerCount+1}"
    )

    private var playerInput: TextField = TextField(
        width = 200, height = 35,
        posX = 150, posY = 125,
        text = "Player${playerCount+1}"
    ).apply {
        onKeyTyped = {
            addPlayerButton.isDisabled = this.text.isBlank()
        }
    }

    private val addPlayerButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 240,
        text = "addPlayer"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.playerService.createPlayer(playerInput.text)
        }

    }

    private val startGameButton = Button(
        width = 140, height = 35,
        posX = 210, posY = 280,
        text = "Start"
    ).apply {
        visual = ColorVisual(136, 221, 136)
        onMouseClicked = {
            schwimmenService.gameService.startGame()
        }
    }



    init {
        startGameButton.isDisabled = true
        addComponents(
            headlineLabel,
            p1Label,
            playerInput,
            addPlayerButton,
            startGameButton
        )
    }

    override fun refreshAfterAddPlayer() {
        playerCount += 1
        if(playerCount>=2){
            startGameButton.isDisabled = false
        }

        if(playerCount == 4){
            schwimmenService.gameService.startGame()
        }

        //println("added ${playerInput.text}")

        p1Label.text = "Add Player #${playerCount+1}"
        playerInput.text = "Player${playerCount+1}"
    }

    override fun refreshAfterStartGame() {

    }
}