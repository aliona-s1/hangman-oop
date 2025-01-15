package com.aliona.hangman;

import java.util.*;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
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
                Game game = new Game(new MaskedWord(dictionary), new Renderer(hangmanStages));
                game.startGame();
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
}