package entity


/**
 * The player-class of the game "Schwimmen"
 *
 * @property name the player name
 * @property hand the starting hand the player is given
 * @property hasKnocked a flag, whether the player has knocked, default: false
 * @constructor creates a Player based on his name and a given set of cards
 *
 */
class Player(val name: String, var hand: Array<Card>, var hasKnocked: Boolean = false) {
    init {
        //a plyer must have 3 cards on their hand
        require(hand.size == 3)
        //a Player needs a name that is not ""
        require(name != "")
    }

    /**
     * calculates the points of this players hand
     *
     * @return Float-value of the players points
     */
    fun getPoints():Float{
        //check if all cards in Hand have the same value
        if((hand[0].cardValue == hand[1].cardValue) && (hand[0].cardValue == hand[2].cardValue)){
            return 30.5F
        }

        var points = 0F
        val cardSuitSet = setOf(CardSuit.CLUBS,CardSuit.DIAMONDS,CardSuit.HEARTS,CardSuit.SPADES)
        //for each cardSuit in deck
        for(cardSuit in cardSuitSet){
            var sum = 0F
            //add the value of the card
            for(card:Card in hand){
                if(card.cardSuit == cardSuit){
                    sum += card.valueToFloat()
                }
            }
            //save the biggest value of sum in points
            points = points.coerceAtLeast(sum)

        }
        return points
    }

}