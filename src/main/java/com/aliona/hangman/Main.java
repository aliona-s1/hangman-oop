package com.aliona.hangman;

import java.util.Scanner;

public class Main {
    private static final String START = "Y";
    private static final String EXIT = "N";

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary("dictionary.txt");
        Renderer renderer = new Renderer();
        Scanner scanner = new Scanner(System.in);

        renderer.printMenuMessage(START, EXIT);

        while (true) {
            String letter = scanner.nextLine().trim().toUpperCase();

            if (letter.equals(START)) {
                Game game = new Game(renderer, new MaskedWord(dictionary.getRandomWord()), scanner);
                game.start();
                renderer.printReplayPromt(START, EXIT);
            } else if (letter.equals(EXIT)) {
                renderer.printExitMessage();
                scanner.close();
                break;
            } else {
                renderer.printInvalidInput(START, EXIT);
            }
        }
    }
}