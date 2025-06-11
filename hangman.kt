import kotlin.random.Random

val wordList = listOf("kotlin", "hangman", "variable", "function", "object")

// Data class for managing game state
data class HangmanGame(val secretWord: String, var guessesLeft: Int = 6) {
    val guessedLetters = mutableListOf<Char>()
    var currentState = "_".repeat(secretWord.length).toCharArray()

    fun displayWord(): String = currentState.joinToString(" ")

    fun guessLetter(letter: Char): Boolean {
        guessedLetters.add(letter)

        var found = false
        for (i in secretWord.indices) {
            if (secretWord[i] == letter) {
                currentState[i] = letter
                found = true
            }
        }

        if (!found) guessesLeft--

        return found
    }

    fun isWon(): Boolean = !currentState.contains('_')
    fun isLost(): Boolean = guessesLeft <= 0
}

// Function to start the game
fun playHangman() {
    val chosenWord = wordList.random()
    val game = HangmanGame(secretWord = chosenWord)

    println("Welcome to Hangman!")
    println("Your word has ${chosenWord.length} letters.")

    while (!game.isWon() && !game.isLost()) {
        println("\nWord: ${game.displayWord()}")
        println("Guesses left: ${game.guessesLeft}")
        print("Guess a letter: ")

        val input = readLine()?.lowercase()?.firstOrNull()

        if (input == null || !input.isLetter()) {
            println("Please enter a valid letter.")
            continue
        }

        if (input in game.guessedLetters) {
            println("You already guessed '$input'. Try a new letter.")
            continue
        }

        if (game.guessLetter(input)) {
            println("Good guess!")
        } else {
            println("Wrong guess!")
        }
    }

    if (game.isWon()) {
        println("\n🎉 You won! The word was '${game.secretWord}'.")
    } else {
        println("\n💀 Game over! The word was '${game.secretWord}'.")
    }
}

// Entry point
fun main() {
    playHangman()
}
