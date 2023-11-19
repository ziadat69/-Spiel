package view

import service.SchwimmenService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * Implementation of the Game schwimmen
 */
class SchwimmenApplication : BoardGameApplication("Schwimmen"), Refreshable {

    //Central service, holds current game
    private var schwimmenService = SchwimmenService()

    //Scenes
    //main game scene
    private var gameScene = SchwimmenGameScene(schwimmenService)
    //scoreboard scene
    private var gameEndScene = GameFinishedMenuScene(this)
    //add players scene
    private var gameStartScene = NewGameMenuScene(schwimmenService)


    init{
        initialize()
    }

    //moved initialization in to its onw function for easy access
    private fun initialize() {
        schwimmenService.clear()
        schwimmenService.addRefreshables(
            this,
            gameScene,
            gameEndScene,
            gameStartScene
        )
        this.showGameScene(gameScene)
        this.showMenuScene(gameStartScene,0)
    }

    override fun refreshAfterStartGame() {
        this.hideMenuScene()
    }

    override fun refreshAfterGameEnd(result: List<Pair<String, Float>>) {
        this.showMenuScene(gameEndScene)

    }

    override fun refreshAfterNewGame() {
        this.hideMenuScene()
        //create new services and scenes, so that entity layer and other stored values are reset
        schwimmenService = SchwimmenService()
        schwimmenService.schwimmen = null
        gameStartScene = NewGameMenuScene(schwimmenService)
        gameScene = SchwimmenGameScene(schwimmenService)
        gameEndScene = GameFinishedMenuScene(this)

        initialize()
    }
}

