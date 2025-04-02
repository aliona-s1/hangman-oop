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
        renderer.printStartMessage();
        runGameLoop();
    }

    private void runGameLoop() {
        renderer.printHangman(mistakes);

        while (!isFinished()) {
            renderer.printMask(maskedWord.getMask());
            String letter = inputValidLetter().toLowerCase();

            if (inputtedLetters.contains(letter)) {
                renderer.printLetterAlreadyUsed();
                continue;
            }

            if (maskedWord.isLetterInWord(letter)) {
                maskedWord.updateMask(letter);
            } else {
                renderer.printLetterNotInWord();
                mistakes++;
            }

            inputtedLetters.add(letter);
            renderer.printHangman(mistakes);
            renderer.printGameInfo(mistakes, MAX_MISTAKES, inputtedLetters);
        }

        if (isWin()) {
            renderer.printWin(maskedWord.getSecretWord());
        } else {
            renderer.printLoss(maskedWord.getSecretWord());
        }
    }

    private String inputValidLetter() {
        while (true) {
            String letter = scanner.nextLine();

            if (letter.length() != 1) {
                renderer.printEnterOneLetterPromt();
                continue;
            }

            if (!letter.matches("[А-Яа-яёЁ]")) {
                renderer.printRussianLetterPromt();
                continue;
            }

            return letter;
        }
    }

    private boolean isWin() {
        return maskedWord.isSecretWordGuessed();
    }

    private boolean isLoss() {
        return mistakes == MAX_MISTAKES;
    }

    private boolean isFinished() {
        return isLoss() || isWin();
    }
}