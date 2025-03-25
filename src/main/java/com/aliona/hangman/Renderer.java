package com.aliona.hangman;

import java.util.List;

public class Renderer {
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

    public void displayResult(boolean isWin, String secretWord) {
        if (isWin) {
            System.out.printf("Поздравляем! Вы угадали слово: %s%n%n", secretWord);
        } else {
            System.out.printf("Вы проиграли! Загаданное слово: %s%n%n", secretWord);
        }
    }
}