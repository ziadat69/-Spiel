package service

import view.Refreshable


/**
 * [Refreshable] implementation that refreshes nothing, but remembers
 * if a refresh method has been called (since last [reset])
 */
class TestRefreshable: Refreshable {

    var refreshAfterNewGame: Boolean = false
        private set

    var refreshAfterGameEnd: Boolean = false
        private set

    var refreshAfterCardChange: Boolean = false
        private set

    var refreshAfterTurn: Boolean = false
        private set

    var refreshAfterAddPlayer: Boolean = false
        private set

    var refreshAfterStartGame: Boolean = false
        private set

    /**
     * resets all *Called properties to false
     */
    fun reset() {
        refreshAfterNewGame = false
        refreshAfterGameEnd = false
        refreshAfterCardChange = false
        refreshAfterTurn = false
        refreshAfterAddPlayer = false
        refreshAfterStartGame = false
    }


    override fun refreshAfterNewGame() {
        refreshAfterNewGame = true
    }

    override fun refreshAfterGameEnd(result: List<Pair<String,Float>>) {
        refreshAfterGameEnd = true
    }

    override fun refreshAfterCardChange(){
        refreshAfterCardChange = true
    }

    override fun refreshAfterTurn(){
        refreshAfterTurn = true
    }

    override fun refreshAfterAddPlayer(){
        refreshAfterAddPlayer = true
    }

    override fun refreshAfterStartGame(){
        refreshAfterStartGame = true
    }
}