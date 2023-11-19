package service

import entity.Schwimmen
import view.Refreshable

/**
 * Main class of service-layer
 *
 * taken form War-example
 */
class SchwimmenService {

    val playerService = PlayerService(this)
    val gameService = GameService(this)

    /**
     * the current Game, is null if the game did not start
     */
    var schwimmen: Schwimmen? = null

    /**
     * adds Services to refreshables
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        gameService.addRefreshable(newRefreshable)
        playerService.addRefreshable(newRefreshable)
    }
    /**
     * Adds each of the provided [newRefreshables] to all services
     * connected to this root service
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

    fun clear(){
        gameService.removeRefreshables()
        playerService.removeRefreshables()
    }
}