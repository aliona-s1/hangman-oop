package com.aliona.hangman;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Set<Character> guessedLetters = new HashSet<>();

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

        while (maskedWord.contains("_")) {
            showMaskedWord(maskedWord);
            char letter = inputLetter();
            validateInputLetter(letter);
            maskedWord = revealGuessedLetters(word, maskedWord, letter);
        }
        System.out.println("Поздравляем! Вы угадали слово: " + maskedWord);
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
        return updatedMaskedWord.toString();
    }
}