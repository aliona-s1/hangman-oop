package com.aliona.hangman;

import java.util.List;

public class Renderer {
    private static final String[] HANGMAN_STAGES =
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

    public void printHangman(int mistakes) { // 7
        System.out.println(HANGMAN_STAGES[mistakes]);
    }

    public void printMask(String mask) {
        System.out.println(mask);
    }

    public void printGameInfo(int mistakes, int maxMistakes, List<String> inputtedLetters) {
        System.out.printf("Ошибок: %d из %d", mistakes, maxMistakes);
        System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
    }

    public void printWin(String secretWord) {
        System.out.printf("Поздравляем! Вы угадали слово: %s%n%n", secretWord);
    }

    public void printLoss(String secretWord) {
        System.out.printf("Вы проиграли! Загаданное слово: %s%n%n", secretWord);
    }
}