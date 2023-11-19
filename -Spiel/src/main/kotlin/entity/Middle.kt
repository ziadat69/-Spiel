package entity

/**
 * a Container class of the middle 3 cards
 *
 * @property cards an Array of 3 Cards for the middle
 */
class Middle(var cards : Array<Card>) {
    init {
        //the middle must have 3 cards
        require(cards.size == 3)
    }
}