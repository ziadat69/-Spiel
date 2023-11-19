package entity

import org.junit.jupiter.api.Assertions.*
import kotlin.test.*

/**
 * a class to test the drawStack
 */
class DrawStackTest{

    /**
     * test functionality of the class DrawStack
     */
    @Test
    fun drawStackTest(){
        val stack = ArrayDeque<Card>()
        val deck = CardValue.shortDeck()
        //create a small stack of cards
        for (value in deck){
            stack.add(Card(CardSuit.SPADES,value))
        }
        val drawStack = DrawStack(stack)

        assertEquals(stack.size,drawStack.cards.size)
    }
}