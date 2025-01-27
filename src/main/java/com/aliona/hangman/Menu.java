package com.aliona.hangman;

import java.util.Scanner;

public class Menu {
    private static final String START = "Y";
    private static final String EXIT = "N";
    private final Scanner scanner;
    private final Renderer renderer;

    public Menu(Scanner scanner, Renderer renderer) {
        this.scanner = scanner;
        this.renderer = renderer;
    }

    public void startMenu(Dictionary dictionary) {
        System.out.printf("Хотите начать игру? %s/%s%n", START, EXIT);

        while (true) {
            String letter = scanner.nextLine().trim().toUpperCase();

            if (letter.equals(START)) {
                Game game = new Game(dictionary, renderer, scanner);
                game.startGame();
                System.out.printf("Хотите сыграть еще раз? %s/%s%n", START, EXIT);
            } else if (letter.equals(EXIT)) {
                System.out.println("Вы вышли из игры.");
                scanner.close();
                break;
            } else {
                System.out.printf("Некорректный ввод. Для начала игры - %s, для выхода - %s.%n", START, EXIT);
            }
        }
    }
}