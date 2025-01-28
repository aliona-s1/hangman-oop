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

    public void printMask(MaskedWord maskedWord) {
        System.out.println(maskedWord.getMask());
    }

    public void showGameInfo(int mistakes, int MAX_MISTAKES, List<String> inputtedLetters) {
        System.out.printf("Ошибок: %s из %s", mistakes, MAX_MISTAKES);
        System.out.println("\nВведенные буквы: " + String.join(",", inputtedLetters));
    }

    public void showWinMessage(MaskedWord maskedWord) {
        System.out.printf("Поздравляем! Вы угадали слово: %s%n%n", maskedWord.getSecretWord());
    }

    public void showLossMessage(MaskedWord maskedWord) {
        System.out.printf("Вы проиграли! Загаданное слово: %s%n%n", maskedWord.getSecretWord());
    }
}