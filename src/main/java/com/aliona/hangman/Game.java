package com.aliona.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int MAX_MISTAKES = 6;
    private static final Scanner scanner = new Scanner(System.in);

    private final MaskedWord maskedWord;
    private final Renderer renderer;
    private final List<String> inputtedLetters = new ArrayList<>();

    private int mistakes = 0;

    public Game(Dictionary dictionary, Renderer renderer) {
        this.maskedWord = new MaskedWord(dictionary.getRandomWord());
        this.renderer = renderer;
    }

    public void start() {
        System.out.println("\nНачинаем игру!");

        runGameLoop();
    }

    public void runGameLoop() {

        renderer.printHangman(mistakes);

        while (!isGameFinished()) {
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

        displayResult();
    }

    public String inputValidLetter() {
        while (true) {
            String letter = scanner.nextLine().toLowerCase();

            if (letter.length() != 1) {
                System.out.println("Введите одну букву.");
                continue;
            }

            if (!letter.matches("[а-яё]")) {
                System.out.println("Введите букву русского алфавита.");
                continue;
            }

            return letter;
        }
    }

    public boolean isWin() {
        return maskedWord.isSecretWordGuessed();
    }

    public boolean isLoss() {
        return mistakes == MAX_MISTAKES;
    }

    public boolean isGameFinished() {
        return isLoss() || isWin();
    }

    public void displayResult() {
        if (isWin()) {
            renderer.showWinMessage(maskedWord);
        } else if (isLoss()) {
            renderer.showLossMessage(maskedWord);
        }
    }
}