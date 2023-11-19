package entity

/**
 * a class that combines cardSuits and cardValue
 *
 * @property cardSuit a CardSuit Object
 * @property cardValue a CardValue Object
 */

data class Card (val cardSuit: CardSuit,val cardValue: CardValue){

    /**
     * a better fitting toString method
     *
     * @return String
     */
    override fun toString() = "$cardSuit$cardValue"

    /**
     * a function, that returns the card value in terms of points
     *
     * @return Float-value of this cards value
     */
    fun valueToFloat() =
        when(this.cardValue){
            CardValue.TWO -> 2F
            CardValue.THREE -> 3F
            CardValue.FOUR -> 4F
            CardValue.FIVE -> 5F
            CardValue.SIX -> 6F
            CardValue.SEVEN -> 7F
            CardValue.EIGHT -> 8F
            CardValue.NINE -> 9F
            CardValue.TEN -> 10F
            CardValue.JACK -> 10F
            CardValue.QUEEN -> 10F
            CardValue.KING -> 10F
            CardValue.ACE -> 11F
        }
}