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
        System.out.println("\nХотите сыграть?");
        while (true) {
            System.out.printf("\nВведите:\n%s - чтобы начать игру.\n%s - чтобы выйти из приложения.", START, EXIT);
            System.out.print("\nВаш выбор: ");
            String letter = SCANNER.nextLine().trim().toLowerCase();

            if (letter.equals(START)) {
                startGame();
            } else if (letter.equals(EXIT)) {
                System.out.println("\nВы вышли из приложения.");
                break;
            } else {
                System.out.println("\nНекорректный ввод.");
            }
        }
    }

    public static void startGame() {
        initializeDictionary();

        System.out.println("\nНачинаем игру!");

        String word = WORDS.get(RANDOM.nextInt(WORDS.size()));
        String maskedWord = "_".repeat(word.length());

        startGameLoop(word, maskedWord);
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

    public static void startGameLoop(String word, String maskedWord) {
        int attemptRate = 6;
        int errorRate = 0;
        List<String> inputtedLetters = new ArrayList<>();
        System.out.println(HANGMAN_STAGES[errorRate]);

        while (!isGameFinished(maskedWord, errorRate, word)) {
            showMaskedWord(maskedWord);
            String letter = inputLetter();

            if (!isInputLetterValid(letter)) {
                System.out.println("\nВведите одну букву русского алфавита.");
            } else if (isLetterAlreadyInput(letter, inputtedLetters)) {
                System.out.println("\nЭта буква уже вводилась! Попробуйте другую.");
            } else {
                int[] result = processGuessedLetter(word, letter, attemptRate, errorRate);
                attemptRate = result[0];
                errorRate = result[1];

                maskedWord = updateMaskedWord(word, maskedWord, letter);
                inputtedLetters.add(letter);
            }

            if (isWordGuessed(word, maskedWord)) {
                System.out.println("\nПоздравляем! Вы угадали слово: " + word);
                return;
            }
            System.out.println(HANGMAN_STAGES[errorRate]);
            System.out.println("Введенные буквы: " + String.join(",", inputtedLetters));
            System.out.println("Осталось попыток: " + attemptRate);

        }

        if (errorRate == 6) {
            System.out.println("\nВы проиграли! Загаданное слово: " + word);
        }
    }

    public static void showMaskedWord(String maskedWord) {
        System.out.println("Текущее слово: " + maskedWord);
    }

    public static String inputLetter() {
        System.out.print("\nВведите букву: ");
        return SCANNER.nextLine().toLowerCase();
    }

    public static boolean isInputLetterValid(String letter) {
        return letter.matches("[а-яА-ЯЁё]");
    }

    public static boolean isLetterAlreadyInput(String letter, List<String> inputtedLetters) {
        return inputtedLetters.contains(letter);
    }

    private static boolean isLetterInWord(String word, String letter) {
        return word.contains(letter);
    }

    public static int[] processGuessedLetter(String word, String letter, int attemptRate, int errorRate) {
        if (!isLetterInWord(word, letter)) {
            attemptRate--;
            errorRate++;
            System.out.println("\nТакой буквы в слове нет!");
        }
        return new int[]{attemptRate, errorRate};
    }

    public static String updateMaskedWord(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equalsIgnoreCase(letter)) {
                updatedMaskedWord.replace(i, i + 1, currentLetter);
            }
        }
        return updatedMaskedWord.toString();
    }

    private static boolean isWordGuessed(String word, String maskedWord) {
        return maskedWord.equals(word);
    }

    private static boolean isGameFinished(String maskedWord, int errorRate, String word) {
        return errorRate == 6 || isWordGuessed(word, maskedWord);
    }
}