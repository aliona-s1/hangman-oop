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

        Renderer renderer = new Renderer(hangmanStages);
        renderer.printHangman(mistakes);

        while (!isGameFinished(mistakes, maskedWord)) {
            String mask = maskedWord.getMask();
            renderer.printMask(mask);
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
            renderer.printHangman(mistakes);
            renderer.showGameInfo(mistakes, MAX_MISTAKES, inputtedLetters);
        }

        if (maskedWord.isSecretWordGuessed()) {
            System.out.printf("Поздравляем! Вы угадали слово: %s%n%n", maskedWord.getSecretWord());
            return;
        }

        if (isLoss(mistakes)) {
            System.out.printf("Вы проиграли! Загаданное слово: %s%n%n", maskedWord.getSecretWord());
        }
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

    private static boolean isLoss(int mistakes) {
        return mistakes == MAX_MISTAKES;
    }

    private static boolean isGameFinished(int mistakes, MaskedWord maskedWord) {
        return isLoss(mistakes) || maskedWord.isSecretWordGuessed();
    }
}