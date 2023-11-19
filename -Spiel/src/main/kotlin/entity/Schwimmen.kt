package entity

/**
 * the main Entity-Layer class of the "Schwimmen"-game
 *
 * @property players an Array(List) of the players
 * @property passCounter a counter, how many times the players have passed default = 0
 * @property currentPlayer this can be NULL. Used to keep track of the current player
 * @property middle the middle card array
 * @property drawStack the drawstack
 */
class Schwimmen(
    val players : ArrayList<Player>,
    var passCounter: Int = 0,
    var currentPlayer : Player? = null,
    val middle : Middle,
    val drawStack: DrawStack
    )