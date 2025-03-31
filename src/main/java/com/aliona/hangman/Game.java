package com.aliona.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int MAX_MISTAKES = 6;

    private final MaskedWord maskedWord;
    private final Renderer renderer;
    private final Scanner scanner;
    private final List<String> inputtedLetters = new ArrayList<>();

    private int mistakes = 0;

    public Game(Renderer renderer, MaskedWord maskedWord, Scanner scanner) {
        this.maskedWord = maskedWord;
        this.renderer = renderer;
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("\nНачинаем игру!");
        runGameLoop();
    }

    private void runGameLoop() {
        renderer.printHangman(mistakes);

        while (!isGameFinished()) {
            renderer.printMask(maskedWord.getMask());
            String letter = inputValidLetter().toLowerCase();

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

        renderer.displayResult(isWin(), maskedWord.getSecretWord());
    }

    private String inputValidLetter() {
        while (true) {
            String letter = SCANNER.nextLine();

            if (letter.length() != 1) {
                System.out.println("Введите одну букву.");
                continue;
            }

            if (!letter.matches("[А-Яа-яёЁ]")) {
                System.out.println("Введите букву русского алфавита.");
                continue;
            }

            return letter;
        }
    }

    private boolean isWin() {
        return maskedWord.isSecretWordGuessed();
    }

    private boolean isGameFinished() {
        return mistakes == MAX_MISTAKES || isWin();
    }
}