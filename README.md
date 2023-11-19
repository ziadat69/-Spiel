# -Spiel

**Schwimmen (Game) - Read Me**

**Materials and Setup**

Schwimmen is a card game for 2-4 players played with a standard 32-card Skat deck:

{Clubs, Spades, Hearts, Diamonds} x {7, 8, 9, 10, Jack, Queen, King, Ace}

At the start, each player is dealt (secretly) three cards. Three additional cards are placed face up in the center, and the remaining cards form the draw pile.

**Scoring**

The goal is to have the highest point total at the end. The highest sum in one of the four suits is counted, with {Jack, Queen, King} each worth 10 points and the Ace worth 11 points. The maximum achievable score is 31 points (Ace plus two 10-point cards). Three cards of different suits but the same rank (e.g., 3×8 or 3xQueen) yield 30.5 points.

*Examples of Point Calculations:*
- Diamond-9, Diamond-Jack, Diamond-Ace → 30 points
- Heart-7, Heart-10, Heart-Jack → 27 points
- Spade-Ace, Spade-7, Clubs-Queen → 18 points
- Heart-Jack, Diamond-Queen, Clubs-King → 10 points
- Clubs-8, Diamond-8, Spade-8 → 30.5 points

**Gameplay**

Starting with the first player, players take turns until the game ends (conditions explained in the End of Game section). Each player can choose from four actions:

1. Swap all cards: The player takes all cards from the center and puts all their cards from hand into the center.
2. Swap one card: Exchange a single card from hand with one from the center.
3. Pass: If a player doesn't want to swap cards, they can pass. After all players pass consecutively, the three center cards go to the discard pile, and three new cards are drawn from the draw pile to the center.
4. Knock: If a player believes they have a good hand, they can knock. Similar to passing, no card exchanges occur. After a player knocks, all other players have one more turn. The game ends after this round.

**End of Game**

The game can end in two ways. The usual way is when a player knocks, and after everyone else has had one more turn, the game ends. Alternatively, if all players pass and there are not enough cards left in the draw pile to replenish the center with three cards, the game ends immediately.

The winner at the end of the game is the player with the highest score. In case of a tie, the game is declared a draw.

**Program Requirements**

The program should control the game flow and enforce the rules. Additional features not directly based on the rules should be implemented:

- Configurable 2-4 players with names at program start.
- Players choose their actions one by one on the same screen (Hotseat mode).
- At the end of the game, display the point results and announce the winning player.
