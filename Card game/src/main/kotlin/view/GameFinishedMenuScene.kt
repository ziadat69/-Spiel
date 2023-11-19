package view


import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import kotlin.system.exitProcess

/**
 * This gets displayed, after the game has finished.
 * This scene lists all players and their points in order of placement
 */
class GameFinishedMenuScene(private val schwimmenApplication: SchwimmenApplication): MenuScene(700,1080), Refreshable {

    private val headlineLabel = Label(
        width = 300, height = 50, posX = 50, posY = 50,
        text = "ScoreBoard",
        font = Font(size = 22)
    )


    init {
        // dark green for "Casino table" flair
        background = ColorVisual(200, 200, 200)

        addComponents(
            headlineLabel
        )

    }

    /**
     * When the game finishes, this method sets all labels and buttons up.
     */
    override fun refreshAfterGameEnd(result: List<Pair<String, Float>>){
        var j = 0
        for((i,pair) in result.withIndex()){
            val playerNameLabel =  Label(
                width = 300, height = 35, posX = 50, posY = 100 +i*50,
                text = "${i+1}. ${pair.first}"
            )
            val playerScoreLabel = Label(
                width = 300, height = 35, posX = 400, posY = 100+i*50,
                text = "${pair.second}"
            )
            addComponents(playerNameLabel,playerScoreLabel)
            j = i
        }

        val newGameButton = Button(
            width = 140, height = 35,
            posX = 50, posY = 100+(j+1)*50,
            text = "New Game"
        ).apply {
            visual = ColorVisual(136, 221, 136)
            onMouseClicked = {
                schwimmenApplication.refreshAfterNewGame()
            }

        }

        val endGameButton = Button(
            width = 140, height = 35,
            posX = 400, posY = 100+(j+1)*50,
            text = "Exit Game"
        ).apply {
            visual = ColorVisual(136, 221, 136)
            onMouseClicked = {
                exitProcess(0)
            }

        }
        addComponents(newGameButton,endGameButton)

    }
}