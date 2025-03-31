package com.aliona.hangman;

import java.util.Scanner;

public class Main {
    private static final String START = "Y";
    private static final String EXIT = "N";

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Renderer renderer = new Renderer();
        Scanner scanner = new Scanner(System.in);

        System.out.printf("Хотите начать игру? %s/%s%n", START, EXIT);

        while (true) {
            String letter = scanner.nextLine().trim().toUpperCase();

            if (letter.equals(START)) {
                Game game = new Game(renderer, new MaskedWord(dictionary.getRandomWord()), scanner);
                game.start();
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