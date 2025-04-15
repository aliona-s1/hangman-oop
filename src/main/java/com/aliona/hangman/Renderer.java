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

    public void printMenuMessage(String start, String exit) {
        System.out.printf("Хотите начать игру? %s/%s%n", start, exit);
    }

    public void printReplayPromt(String start, String exit) {
        System.out.printf("Хотите сыграть еще раз? %s/%s%n", start, exit);
    }

    public void printExitMessage() {
        System.out.println("Вы вышли из игры.");
    }

    public void printInvalidInput(String start, String exit) {
        System.out.printf("Некорректный ввод. Для начала игры - %s, для выхода - %s.%n", start, exit);
    }

    public void printStartGameMessage() {
        System.out.println("Начинаем игру!");
    }

    public void printLetterAlreadyUsed() {
        System.out.println("Эта буква уже вводилась! Попробуйте другую.");
    }

    public void printLetterNotInWord() {
        System.out.println("Такой буквы в слове нет!");
    }

    public void printEnterOneLetterPromt() {
        System.out.println("Введите одну букву.");
    }

    public void printRussianLetterPromt() {
        System.out.println("Введите букву русского алфавита.");
    }

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