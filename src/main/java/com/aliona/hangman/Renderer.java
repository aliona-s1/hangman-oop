package com.aliona.hangman;

import java.util.List;

public class Renderer {
    private final String[] hangmanStages;

    public Renderer(String[] hangmanStages) {
        this.hangmanStages = hangmanStages;
    }

    public void printHangman(int mistakes) {
        System.out.print(hangmanStages[Math.min(mistakes, hangmanStages.length - 1)]);
    }

    public void printMask(String mask) {
        System.out.println(mask);
    }

    public void showGameInfo(int mistakes, int MAX_MISTAKES, List<String> inputtedLetters) {
        System.out.printf("Ошибок: %s из %s", mistakes, MAX_MISTAKES);
        System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
    }
}