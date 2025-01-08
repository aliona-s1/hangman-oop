package com.aliona.hangman;

import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final int MAX_MISTAKES = 6;
    private static final String START = "Y";
    private static final String EXIT = "N";
    private static final String[] hangmanStages =
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

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        startMenu(dictionary);
    }

    private static void startMenu(Dictionary dictionary) {
        System.out.printf("Хотите начать игру? %s/%s%n", START, EXIT);

        while (true) {
            String letter = SCANNER.nextLine().trim().toUpperCase();

            if (letter.equals(START)) {
                startGame(dictionary);
                System.out.printf("Хотите сыграть еще раз? %s/%s%n", START, EXIT);
            } else if (letter.equals(EXIT)) {
                System.out.println("Вы вышли из игры.");
                SCANNER.close();
                break;
            } else {
                System.out.printf("Некорректный ввод. Для начала игры - %s, для выхода - %s.%n", START, EXIT);
            }
        }
    }

    private static void startGame(Dictionary dictionary) {
        System.out.println("\nНачинаем игру!");

        MaskedWord maskedWord = new MaskedWord(dictionary);

        startGameLoop(maskedWord);
    }

    private static void startGameLoop(MaskedWord maskedWord) {
        int mistakes = 0;
        List<String> inputtedLetters = new ArrayList<>();
        printHangman(mistakes);

        while (!isGameFinished(mistakes, maskedWord)) {
            printMask(maskedWord.getMask());
            String letter = inputValidLetter();

            if (inputtedLetters.contains(letter)) {
                System.out.println("Эта буква уже вводилась! Попробуйте другую.");
                continue;
            }

            if (maskedWord.isLetterInWord(letter)) {
                maskedWord.updateMask(letter);
            } else {
                System.out.println("Такой буквы в слове нет!");
                mistakes++;
            }

            inputtedLetters.add(letter);
            showGameState(mistakes, inputtedLetters);
        }

        if (isWin(maskedWord)) {
            System.out.printf("Поздравляем! Вы угадали слово: %s%n%n", maskedWord.getSecretWord());
            return;
        }

        if (isLoss(mistakes)) {
            System.out.printf("Вы проиграли! Загаданное слово: %s%n%n", maskedWord.getSecretWord());
        }
    }

    private static void printMask(String mask) {
        System.out.println(mask);
    }

    private static String inputValidLetter() {
        while (true) {
            String letter = SCANNER.nextLine().toLowerCase();

            if (letter.length() != 1) {
                System.out.println("Введите одну букву.");
                continue;
            }

            if (!letter.matches("[а-яА-ЯЁё]")) {
                System.out.println("Введите букву русского алфавита.");
                continue;
            }

            return letter;
        }
    }

    private static void showGameState(int mistakes, List<String> inputtedLetters) {
        printHangman(mistakes);
        System.out.printf("Ошибок: %s из %s", mistakes, MAX_MISTAKES);
        System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
    }

    private static boolean isWin(MaskedWord maskedWord) {
        return maskedWord.getMask().equals(maskedWord.getSecretWord());
    }

    private static boolean isLoss(int mistakes) {
        return mistakes == MAX_MISTAKES;
    }

    private static boolean isGameFinished(int mistakes, MaskedWord maskedWord) {
        return isLoss(mistakes) || isWin(maskedWord);
    }

    private static void printHangman(int mistakes) {
        System.out.print(hangmanStages[mistakes]);
    }
}