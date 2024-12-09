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
    private static final String MASKING_SYMBOL = "*";

    public static void main(String[] args) {
        initializeDictionary();
        while (true) {
            System.out.printf("\nХотите начать игру?\n%s - начать | %s - выйти\n", START, EXIT);
            String letter = SCANNER.nextLine().trim().toLowerCase();

            if (letter.equals(START)) {
                startGame();
            } else if (letter.equals(EXIT)) {
                System.out.println("\nВы вышли из игры.");
                SCANNER.close();
                break;
            } else {
                System.out.println("\nНекорректный ввод.");
            }
        }
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
                throw new RuntimeException("Словарь пуст.");
            }
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException("Файл не найден.");
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
        printHangman(mistakes);

        while (!isGameFinished(maskedWord, mistakes, word)) {
            showMaskedWord(maskedWord);

            String letter = inputValidLetter(inputtedLetters);

            mistakes = updateMistakes(word, letter, mistakes);
            maskedWord = updateMaskedWord(word, maskedWord, letter);
            inputtedLetters.add(letter);

            if (isWin(word, maskedWord)) {
                System.out.println("\nПоздравляем! Вы угадали слово: " + word + "\n");
                return;
            }
            showGameState(mistakes, inputtedLetters);
        }

        if (isLoss(mistakes)) {
            System.out.println("\nВы проиграли! Загаданное слово: " + word + "\n");
        }
    }

    private static void showMaskedWord(String maskedWord) {
        System.out.println(maskedWord);
    }

    private static String inputValidLetter(List<String> inputtedLetters) {
        while (true) {
            System.out.print("\nВведите букву: ");
            String letter = SCANNER.nextLine().toLowerCase();

            if (letter.length() != 1) {
                System.out.println("Введите одну букву.");
            } else if (letter.matches("[а-яА-ЯЁё]")) {
                if (!inputtedLetters.contains(letter)) {
                    return letter;
                }
                System.out.println("\nЭта буква уже вводилась! Попробуйте другую.");
            } else {
                System.out.println("\nВведите букву русского алфавита.");
            }
        }
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

    private static void printHangman(int mistakes) {
        String[] hangmanStages =
                {"""
   ______
   |    |
   |
   |
   |
   |
===========
""", """
   ______
   |    |
   |    0
   |
   |
   |
===========
""", """
   ______
   |    |
   |    0
   |    |
   |
   |
===========
""", """
   ______
   |    |
   |    0
   |   /|
   |
   |
===========
""", """
   ______
   |    |
   |    0
   |   /|\\
   |
   |
===========
""", """
   ______
   |    |
   |    0
   |   /|\\
   |   /
   |
===========
""", """
   ______
   |    |
   |    0
   |   /|\\
   |   / \\
   |
===========
"""};
        System.out.println(hangmanStages[mistakes]);
    }

    private static void showGameState(int mistakes, List<String> inputtedLetters) {
        printHangman(mistakes);
        System.out.println("Ошибок: " + mistakes + " из " + MAX_MISTAKES);
        System.out.println("Введенные буквы: " + String.join(",", inputtedLetters) + "\n");
    }

    private static boolean isWin(String word, String maskedWord) {
        return maskedWord.equals(word);
    }

    private static boolean isLoss(int mistakes) {
        return mistakes == MAX_MISTAKES;
    }

    private static boolean isGameFinished(String maskedWord, int mistakes, String word) {
        return isLoss(mistakes) || isWin(word, maskedWord);
    }
}