import java.util.Scanner;

public class HangmanGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player player = new Player();
        Word word = new Word();

        System.out.println("Welcome to Hangman! You have to guess the mystery word in 5 guesses or less.");

        while (player.getGuessesLeft() > 0 && !word.isWordComplete()) {
            System.out.println("Mystery Word: " + word.getUserWord());
            System.out.println("Guesses Left: " + player.getGuessesLeft());
            System.out.println("Letters Guessed: " + player.getLettersGuessed());
            System.out.print("Enter a letter: ");
            String letter = scanner.next();
            player.addLetterGuess(letter);

            if (word.revealLetter(letter)) {
                System.out.println("CORRECT!");
            } else {
                System.out.println("INCORRECT");
                player.decreaseGuess();
            }
            System.out.println();
        }

        if (word.isWordComplete()) {
            System.out.println("Congratulations! You guessed the word: " + word.getAnswerWord());
        } else {
            System.out.println("You lost! The word was: " + word.getAnswerWord());
        }
        scanner.close();
    }
}

class Player {
    private int guessesLeft;
    private String lettersGuessed;

    public Player() {
        guessesLeft = 5;
        lettersGuessed = "";
    }

    public int getGuessesLeft() {
        return guessesLeft;
    }

    public String getLettersGuessed() {
        return lettersGuessed;
    }

    public void decreaseGuess() {
        guessesLeft--;
    }

    public void addLetterGuess(String letter) {
        lettersGuessed += letter + " ";
    }
}

class Word {
    static String[] Words = {"Class", "School", "Keyboard", "Mouse", "Paper", "Phone", "Monitor", "Controller", "Apple", "Samsung"};
    private String answerWord;
    private String userWord = "";

    static String getWord(int index) {
        return Words[index];
    }

    public Word() {
        int idx = (int) (Math.random() * 10);
        answerWord = getWord(idx);
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

    public boolean revealLetter(String letter) {
        boolean found = false;
        String newWord = "";

        for (int i = 0; i < answerWord.length(); i++) {
            String current = answerWord.substring(i, i + 1);

            if (current.equalsIgnoreCase(letter)) {
                newWord += current;
                found = true;
            } else {
                newWord += userWord.substring(i, i + 1);
            }
        }

        userWord = newWord;
        return found;
    }

    public boolean isWordComplete() {
        return userWord.equals(answerWord);
    }
}
