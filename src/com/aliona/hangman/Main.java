package com.aliona.hangman;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Character> guessedLetters = new HashSet<>();
    private static int ATTEMP_RATE = 6;

    public static void main(String[] args) {
        launchMenu();
    }

    public static void launchMenu() {
        while (true) {
            System.out.println("[н]ачать новую игру или [в]ыйти из игры.");
            String letter = scanner.nextLine();

            if (letter.equals("в")) {
                System.out.println("Вы вышли из игры.");
                break;
            } else if (letter.equals("н")) {
                startGame();
                break;
            } else {
                System.out.println("Некорректный ввод.");
            }
        }
    }

    public static void startGame() {
        System.out.println("Вы начали игру.");
        startGameLoop();
    }

    public static void startGameLoop() {
        String word = "телевизор";
        String maskedWord = "_".repeat(word.length());

        while (!isGameFinished(maskedWord)) {
            showMaskedWord(maskedWord);
            char letter = inputLetter();
            validateInputLetter(letter);
            maskedWord = revealGuessedLetters(word, maskedWord, letter);
        }

        if (maskedWord.contains("_")) {
            System.out.println("Вы проиграли!");
        } else {
            System.out.println("Поздравляем! Вы угадали слово: " + maskedWord);
        }
    }

    private static boolean isGameFinished(String maskedWord) {
        return ATTEMP_RATE == 0 || !maskedWord.contains("_");
    }

    public static void showMaskedWord(String maskedWord) {
        System.out.println("Текущее слово: " + maskedWord);
    }

    public static char inputLetter() {
        System.out.print("Введите букву: ");
        return scanner.nextLine().charAt(0);
    }

    public static void validateInputLetter(char letter) {
        if ((letter >= 'а' && letter <= 'я') || (letter >= 'А' && letter <= 'Я')) {
        } else {
            System.out.println("Некорректный ввод. Введите букву русского алфавита");
        }
    }

    public static String revealGuessedLetters(String word, String maskedWord, char letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        if (guessedLetters.contains(letter)) {
            System.out.println("Эта буква уже была угадана! Попробуйте другую: ");
            return maskedWord;
        }

        guessedLetters.add(letter);

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                updatedMaskedWord.setCharAt(i, letter);
            }
        }

        if (!word.contains(Character.toString(letter))) {
            System.out.println("Такой буквы здесь нет!");
            ATTEMP_RATE--;
        }
        System.out.println("Осталось попыток: " + ATTEMP_RATE);

        return updatedMaskedWord.toString();
    }
}