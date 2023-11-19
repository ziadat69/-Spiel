package service

import entity.*



/**
 * Service-layer Class, that provides logic for the game(like finish game)
 * @property schwimmenService as the rootService
 */
class GameService(val schwimmenService : SchwimmenService) : AbstractRefreshingService(){

    /**
     * Notifies the Gui about gameStart
     *
     * sets the currentPlayer
     *
     * @throws KotlinNullPointerException if schwimmen was not initialized
     */
    fun startGame(){
        val game = schwimmenService.schwimmen!!
        game.currentPlayer = game.players[0]
        onAllRefreshables { refreshAfterStartGame() }
    }

    /**
     * This function handles everything, that happens after the player has finished their turn.
     *
     * it sets the new player, the has knocked flag and passed counter
     * @throws IllegalStateException if either game or current player are null
     */
    fun finishTurn(){
        val game = schwimmenService.schwimmen!!
        game.currentPlayer = (calculateNextCurrentPlayer(game))

        val currentP = game.currentPlayer!!
        if(currentP.hasKnocked){
            endGame()
            return
        }

        if(game.passCounter==game.players.size){
            if(game.drawStack.cards.size<3){
                endGame()
                return
            }
            else{
                game.middle.cards = newMiddle(game)
                game.passCounter = 0
            }
        }
        onAllRefreshables{refreshAfterTurn()}

    }

    /**
     * function that gets called, when a game finishes
     */
    fun endGame(){
        onAllRefreshables { refreshAfterGameEnd(calculateScore()) }
    }

    /**
     * calculates the scores for all players
     *
     * @return a list of Pairs containing (playerName,points)
     */
    fun calculateScore() : List<Pair<String,Float>>{

        val game = schwimmenService.schwimmen!!
        val list = arrayListOf<Pair<String,Float>>()
        for (player in game.players){
            list.add(Pair(player.name,player.getPoints()))
        }
        list.sortByDescending { it.second }
        return list
    }

    /**
     * a helper Method to calculate the next current player
     *
     * This method iterates over players, to find the old currentPlayer. If he is found, set nextPlayerPos to his
     * Position +1 mod players.size. This ensures even the last player has a next player(the first one)
     * @param game the current gamestate
     * @return the new current player
     */
    private fun calculateNextCurrentPlayer(game : Schwimmen) : Player{
        // first card is the easiest method to compare 2 players
        val currFirstCard = game.currentPlayer!!.hand[0]
        //stores, where the current player is
        var nextPlayerPos = 0
        for ((iteration, player) in game.players.withIndex()){
            if(player.hand[0] == currFirstCard){
                //if the current is found, calculate the next players pos
                nextPlayerPos = (iteration+1) % game.players.size
            }
        }
        return game.players[nextPlayerPos]
    }

    /**
     * helper method to create a new array for Middle
     *
     * @param game the current game for easy access
     * @return the array of cards for middle
     */
    private fun newMiddle(game : Schwimmen): Array<Card>{
        val deck = game.drawStack.cards
        val card1 = deck.removeLast()
        val card2 = deck.removeLast()
        val card3 = deck.removeLast()
        return arrayOf(card1,card2,card3)
    }
}