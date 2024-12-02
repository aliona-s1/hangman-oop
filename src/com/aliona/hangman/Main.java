package com.aliona.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Random RANDOM = new Random();
    private static final List<String> WORDS = new ArrayList<>();

    private static final String START = "н";
    private static final String EXIT = "в";
    private static final int MAX_MISTAKES = 6;
    private static final String MASKING_SYMBOL = "_";

    public static void main(String[] args) {
        initializeDictionary();
        startMenu();
    }

    private static void initializeDictionary() {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("dictionary.txt");
             Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (!word.isEmpty()) {
                    WORDS.add(word);
                }
            }

            if (WORDS.isEmpty()) {
                System.out.println("\nИзвините, ошибка!");
                System.exit(1);
            }
        } catch (NullPointerException | IOException e) {
            System.out.println("\nИзвините, ошибка!");
            System.exit(1);
        }
    }

    private static void startMenu() {
        while (true) {
            System.out.printf("\nХотите сыграть?\n%s - начать игру\n%s - выйти из приложения\n", START, EXIT);
            System.out.print("\nВаш выбор: ");
            String letter = SCANNER.nextLine().trim().toLowerCase();

            if (letter.equals(START)) {
                startGame();
            } else if (letter.equals(EXIT)) {
                System.out.println("\nВы вышли из приложения.");
                SCANNER.close();
                break;
            } else {
                System.out.println("\nНекорректный ввод.");
            }
        }
    }

    private static void startGame() {

        System.out.println("\nНачинаем игру!");

        String word = WORDS.get(RANDOM.nextInt(WORDS.size()));
        String maskedWord = MASKING_SYMBOL.repeat(word.length());

        startGameLoop(word, maskedWord);
    }

    private static void startGameLoop(String word, String maskedWord) {
        int mistakes = 0;
        List<String> inputtedLetters = new ArrayList<>();

        while (!isGameFinished(maskedWord, mistakes, word)) {
            printHangman(mistakes);
            showMaskedWord(maskedWord);
            String letter = inputLetter();

            if (!isInputLetterValid(letter)) {
                System.out.println("\nВведите одну букву русского алфавита.");
            } else if (isLetterAlreadyInput(letter, inputtedLetters)) {
                System.out.println("\nЭта буква уже вводилась! Попробуйте другую.");
            } else {
                mistakes = updateMistakes(word, letter, mistakes);
                maskedWord = updateMaskedWord(word, maskedWord, letter);
                inputtedLetters.add(letter);
            }

            if (isWordGuessed(word, maskedWord)) {
                System.out.println("\nПоздравляем! Вы угадали слово: " + word);
                return;
            }
            showGameState(mistakes, inputtedLetters);
        }

        if (mistakes == MAX_MISTAKES) {
            System.out.println("\nВы проиграли! Загаданное слово: " + word);
        }
    }

    private static void showMaskedWord(String maskedWord) {
        System.out.println("Загаданное слово: " + maskedWord);
    }

    private static String inputLetter() {
        System.out.print("\nВведите букву: ");
        return SCANNER.nextLine().toLowerCase();
    }

    private static boolean isInputLetterValid(String letter) {
        return letter.matches("[а-яА-ЯЁё]");
    }

    private static boolean isLetterAlreadyInput(String letter, List<String> inputtedLetters) {
        return inputtedLetters.contains(letter);
    }

    private static int updateMistakes(String word, String letter, int mistakes) {
        if (!word.contains(letter)) {
            mistakes++;
            System.out.println("\nТакой буквы в слове нет!");
        }
        return mistakes;
    }

    private static String updateMaskedWord(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i + 1, currentLetter);
            }
        }
        return updatedMaskedWord.toString();
    }

    private static boolean isWordGuessed(String word, String maskedWord) {
        return maskedWord.equals(word);
    }

    private static void printHangman(int mistakes) {
        String[] HANGMAN_STAGES =
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
        System.out.println(HANGMAN_STAGES[mistakes]);
    }

    private static void showGameState(int mistakes, List<String> inputtedLetters) {
        System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
        System.out.println("Ошибок: " + mistakes + " из " + MAX_MISTAKES);
    }

    private static boolean isGameFinished(String maskedWord, int mistakes, String word) {
        return mistakes == MAX_MISTAKES || isWordGuessed(word, maskedWord);
    }
}