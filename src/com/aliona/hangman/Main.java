package com.aliona.hangman;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String START = "да";
    private static final String EXIT = "нет";
    private static final String[] HANGMAN_STAGES =
            {"""
   ______
   |    |
   |
   |
   |
   |
-----------
""", """
   ______
   |    |
   |    0
   |
   |
   |
-----------
""", """
   ______
   |    |
   |    0
   |    |
   |
   |
-----------
""", """
   ______
   |    |
   |    0
   |   /|
   |
   |
-----------
""", """
   ______
   |    |
   |    0
   |   /|\\
   |
   |
-----------
""", """
   ______
   |    |
   |    0
   |   /|\\
   |   /
   |
-----------
""", """
   ______
   |    |
   |    0
   |   /|\\
   |   / \\
   |
-----------
"""};

    public static void main(String[] args) {
        startMenu();
    }

    public static void startMenu() {
        while (true) {
            System.out.println("\nХотите начать игру? Введите да/нет.\n");
            String letter = scanner.nextLine().trim().toLowerCase();

            if (letter.equals(START)) {
                startGame();
            } else if (letter.equals(EXIT)) {
                System.out.println("\nВы решили не начинать игру. До новый встреч!");
                break;
            } else {
                System.out.println("\nНекорректный ввод.");
            }
        }
    }

    public static void startGame() {
        System.out.println("\nВы начали игру.");
        String word = "телевизор";
        String maskedWord = "_".repeat(word.length());

        startGameLoop(word, maskedWord);
    }

    public static void startGameLoop(String word, String maskedWord) {
        AtomicInteger attemptRate = new AtomicInteger(6);
        AtomicInteger errorRate = new AtomicInteger(0);
        List<String> inputtedLetters = new ArrayList<>();
        System.out.println(HANGMAN_STAGES[errorRate.get()]);

        while (!isGameFinished(maskedWord, errorRate, word)) {
            showMaskedWord(maskedWord);
            String letter = inputLetter();

            if (!isInputLetterValid(letter)) {
                System.out.println("\nВведите одну букву русского алфавита.");
                continue;
            }

            if (isLetterAlreadyInput(letter, inputtedLetters)) {
                System.out.println("\nЭта буква уже вводилась! Попробуйте другую.");
                continue;
            }

            maskedWord = processGuessedLetter(word, letter, maskedWord, attemptRate, errorRate);
            inputtedLetters.add(letter);

            if (isWordGuessed(word, maskedWord)) {
                System.out.println("\nПоздравляем! Вы угадали слово: " + word);
                return;
            }

            System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
        }

        if (errorRate.get() == 6) {
            System.out.println("\nВы проиграли! Загаданное слово: " + word);
        }
    }

    public static void showMaskedWord(String maskedWord) {
        System.out.println("\nТекущее слово: " + maskedWord);
    }

    public static String inputLetter() {
        System.out.print("\nВведите букву: ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public static boolean isInputLetterValid(String letter) {
        return letter.matches("[а-яё]");
    }

    public static boolean isLetterAlreadyInput(String letter, List<String> inputtedLetters) {
        return inputtedLetters.contains(letter);
    }

    private static boolean isLetterInWord(String word, String letter) {
        return word.contains(letter);
    }

    public static String processGuessedLetter(String word, String letter, String maskedWord, AtomicInteger attemptRate, AtomicInteger errorRate) {
        if (!isLetterInWord(word, letter)) {
            System.out.println("\nТакой буквы в слове нет!");
            attemptRate.decrementAndGet();
            errorRate.incrementAndGet();
            System.out.println(HANGMAN_STAGES[errorRate.get()]);
            System.out.println("Осталось попыток: " + attemptRate.get());
        }
        return updateMaskedWord(word, maskedWord, letter);
    }

    public static String updateMaskedWord(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i + 1, letter);
            }
        }
        return updatedMaskedWord.toString();
    }

    private static boolean isWordGuessed(String word, String maskedWord) {
        return maskedWord.equals(word);
    }

    private static boolean isGameFinished(String maskedWord, AtomicInteger errorRate, String word) {
        return errorRate.get() == 6 || isWordGuessed(word, maskedWord);
    }
}