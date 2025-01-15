package com.aliona.hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private static final int MAX_MISTAKES = 6;

    private final MaskedWord maskedWord;
    private final Renderer renderer;
    private final Scanner scanner;

    private int mistakes = 0;
    private List<String> inputtedLetters = new ArrayList<>();

    public Game(MaskedWord maskedWord, Renderer renderer) {
        this.maskedWord = maskedWord;
        this.renderer = renderer;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        renderer.showStartMessage();
        renderer.printHangman(mistakes);

        while (!isGameFinished()) {
            playGame();
        }

        handleGameEnd();
    }

    public void playGame() {
        renderer.printMask(maskedWord);
        String letter = inputValidLetter();

        if (inputtedLetters.contains(letter)) {
            System.out.println("Эта буква уже вводилась! Попробуйте другую.");
        }

        if (maskedWord.isLetterInWord(letter)) {
            maskedWord.updateMask(letter);
        } else {
            System.out.println("Такой буквы в слове нет!");
            mistakes++;
            renderer.printHangman(mistakes);
        }

        inputtedLetters.add(letter);
        renderer.showGameInfo(mistakes, MAX_MISTAKES, inputtedLetters);
    }

    public String inputValidLetter() {

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

    public boolean isGameFinished() {
        return mistakes == MAX_MISTAKES || maskedWord.isSecretWordGuessed();
    }

    public void handleGameEnd() {

        if (maskedWord.isSecretWordGuessed()) {
            renderer.showWinMessage(maskedWord);
        } else if (mistakes == MAX_MISTAKES) {
            renderer.showLossMessage(maskedWord);
        }
    }
}