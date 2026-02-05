
import java.util.Scanner;

public class HangmanGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Make a player (tracks guesses + letters guessed)
        Player player = new Player();

        // Pick a random word and hide it with stars
        Word word = new Word();

        System.out.println("Welcome to Hangman! You have to guess the mystery word in 5 guesses or less.\n");

        // Keep playing until the player runs out of guesses or finishes the word
        while (player.getGuessesLeft() > 0 && !word.isWordComplete()) {

            // Show the hangman drawing based on how many wrong guesses so far
            System.out.println(getHangmanArt(player.getWrongGuesses()));

            // Show the hidden word (ex: *A**E)
            System.out.println("Mystery Word: " + word.getUserWord());

            // Show how many guesses are left
            System.out.println("Guesses Left: " + player.getGuessesLeft());

            // Show what letters the player already tried
            System.out.println("Letters Guessed: " + player.getLettersGuessed());

            System.out.print("Enter a letter: ");
            String input = scanner.next();

            // Make sure the player only typed ONE character
            if (input.length() != 1) {
                System.out.println("Invalid input. Please enter ONE letter.\n");
                continue;
            }

            char c = input.charAt(0);

            // Make sure it's actually a letter (A–Z)
            if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                System.out.println("Invalid input. Please enter a LETTER.\n");
                continue;
            }

            // Convert to uppercase so everything matches
            String letter = ("" + c).toUpperCase();

            // Check if the player already guessed this letter
            if (player.hasGuessed(letter)) {
                System.out.println("You already guessed that letter!\n");
                continue;
            }

            // Add the letter to the list of guessed letters
            player.addLetterGuess(letter);

            // If the letter is in the word, reveal it. Otherwise, lose a guess.
            if (word.revealLetter(letter)) {
                System.out.println("CORRECT!\n");
            } else {
                System.out.println("INCORRECT\n");
                player.decreaseGuess();
            }
        }

        // Show the final hangman drawing
        System.out.println(getHangmanArt(player.getWrongGuesses()));

        // Tell the player if they won or lost
        if (word.isWordComplete()) {
            System.out.println("Congratulations! You guessed the word: " + word.getAnswerWord());
        } else {
            System.out.println("You lost! The word was: " + word.getAnswerWord());
        }

        scanner.close();
    }

    // Returns the hangman picture depending on how many wrong guesses the player made
    public static String getHangmanArt(int wrong) {

        if (wrong == 0) {
            return
            "  +---+\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        }

        if (wrong == 1) {
            return
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "      |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        }

        if (wrong == 2) {
            return
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            "  |   |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        }

        if (wrong == 3) {
            return
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|   |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        }

        if (wrong == 4) {
            return
            "  +---+\n" +
            "  |   |\n" +
            "  O   |\n" +
            " /|\\  |\n" +
            "      |\n" +
            "      |\n" +
            "=========";
        }

        // Final stage (5 wrong guesses)
        return
        "  +---+\n" +
        "  |   |\n" +
        "  O   |\n" +
        " /|\\  |\n" +
        " / \\  |\n" +
        "      |\n" +
        "=========";
    }
}

class Player {

    private int guessesLeft;
    private String lettersGuessed;

    public Player() {
        // Player starts with 5 guesses
        guessesLeft = 5;

        // This will store letters like: "A B C "
        lettersGuessed = "";
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public String getLettersGuessed() {
        return lettersGuessed.trim();
    }

    // Lose one guess
    public void decreaseGuess() {
        guessesLeft--;
    }

    // Add a guessed letter to the list
    public void addLetterGuess(String letter) {
        lettersGuessed += letter + " ";
    }

    // Check if the player already guessed this letter
    public boolean hasGuessed(String letter) {
        return lettersGuessed.indexOf(letter + " ") != -1;
    }

    // Wrong guesses = total allowed (5) minus guesses left
    public int getWrongGuesses() {
        return 5 - guessesLeft;
    }
}

class Word {

    private String answerWord; // The real word
    private String userWord;   // The hidden version (stars)

    // Picks a random word from a list of 10
    public static String selectRandomWord() {
        int idx = (int)(Math.random() * 10);

        if (idx == 0) return "CLASS";
        if (idx == 1) return "SCHOOL";
        if (idx == 2) return "KEYBOARD";
        if (idx == 3) return "MOUSE";
        if (idx == 4) return "PAPER";
        if (idx == 5) return "PHONE";
        if (idx == 6) return "MONITOR";
        if (idx == 7) return "CONTROLLER";
        if (idx == 8) return "APPLE";
        return "SAMSUNG";
    }

    public Word() {
        // Pick the word
        answerWord = selectRandomWord();

        // Make a hidden version of it (same length, all stars)
        userWord = "";
        for (int i = 0; i < answerWord.length(); i++) {
            userWord += "*";
        }
    }

    public String getAnswerWord() {
        return answerWord;
    }

    public String getUserWord() {
        return userWord;
    }

    // Reveal all spots where the guessed letter appears
    public boolean revealLetter(String letter) {
        boolean found = false;
        String newWord = "";

        // Go through each letter of the answer word
        for (int i = 0; i < answerWord.length(); i++) {
            String current = answerWord.substring(i, i + 1);

            // If the guessed letter matches, reveal it
            if (current.compareTo(letter) == 0) {
                newWord += letter;
                found = true;
            } else {
                // Otherwise keep whatever was already shown (* or a revealed letter)
                newWord += userWord.substring(i, i + 1);
            }
        }

        // Update the hidden word
        userWord = newWord;
        return found;
    }

    // Check if the player has fully guessed the word
    public boolean isWordComplete() {
        return userWord.compareTo(answerWord) == 0;
    }
}

