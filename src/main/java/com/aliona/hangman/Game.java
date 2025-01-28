package com.aliona.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int MAX_MISTAKES = 6;
    private static final Scanner scanner = new Scanner(System.in);

    private final MaskedWord maskedWord;
    private final Renderer renderer;

    public Game(Dictionary dictionary, Renderer renderer) {
        this.maskedWord = new MaskedWord(dictionary.getRandomWord());
        this.renderer = renderer;
    }

    public void start(Renderer renderer) {
        System.out.println("\nНачинаем игру!");

        startGameLoop(maskedWord, renderer);
    }

    public static void startGameLoop(MaskedWord maskedWord, Renderer renderer) {
        int mistakes = 0;
        List<String> inputtedLetters = new ArrayList<>();

        renderer.printHangman(mistakes);

        while (!isGameFinished(mistakes, maskedWord)) {
            renderer.printMask(maskedWord);
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

        if (isWin(maskedWord)) {
            renderer.showWinMessage(maskedWord);
            return;
        }

        if (isLoss(mistakes)) {
            renderer.showLossMessage(maskedWord);
        }
    }

    public static String inputValidLetter() {
        while (true) {
            String letter = scanner.nextLine().toLowerCase();

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

    private static boolean isWin(MaskedWord maskedWord) {
        return maskedWord.isSecretWordGuessed();
    }

    private static boolean isLoss(int mistakes) {
        return mistakes == MAX_MISTAKES;
    }

    private static boolean isGameFinished(int mistakes, MaskedWord maskedWord) {
        return isLoss(mistakes) || isWin(maskedWord);
    }
}