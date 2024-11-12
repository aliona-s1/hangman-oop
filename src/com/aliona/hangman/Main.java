package com.aliona.hangman;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Set<String> guessedLetters = new HashSet<>();
    private static final String START = "н";
    private static final String QUIT = "в";
    private static int attemptRate = 6;
    private static String word = "телевизор";
    private static String maskedWord = "_".repeat(word.length());

    public static void main(String[] args) {
        launchMenu();
    }

    public static void launchMenu() {
        while (true) {
            System.out.println("Хотите [н]ачать новую игру или [в]ыйти из игры?");
            String letter = scanner.nextLine();

            if (letter.equals(START)) {
                startGame();
                break;
            } else if (letter.equals(QUIT)) {
                System.out.println("Вы вышли из игры.");
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
        while (!isGameFinished(maskedWord)) {
            showMaskedWord(maskedWord);
            String letter = inputLetter();
            validateInputLetter(letter);
            maskedWord = revealGuessedLetters(word, maskedWord, letter);
        }

        if (maskedWord.contains("_")) {
            System.out.println("Вы проиграли!. Загаданное слово: " + word);
        } else {
            System.out.println("Поздравляем! Вы угадали слово: " + word);
        }
    }

    public static void showMaskedWord(String maskedWord) {
        System.out.println("Текущее слово: " + maskedWord);
    }

    public static String inputLetter() {
        System.out.print("Введите букву: ");
             return scanner.nextLine().trim();
    }

    private static boolean isGameFinished(String maskedWord) {
        return attemptRate == 0 || !maskedWord.contains("_");
    }

    public static void validateInputLetter(String letter) {
        if (letter.matches("[а-яА-Я,ё,Ё]")) {
        } else {
            System.out.println("Некорректный ввод. Введите букву русского алфавита");
        }
    }

    public static String revealGuessedLetters(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        if (guessedLetters.contains(letter)) {
            System.out.println("Эта буква уже была угадана! Попробуйте другую: ");
            return maskedWord;
        }

        guessedLetters.add(letter);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i+1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i+1, letter);
            }
        }

        if (!word.contains(letter)) {
            System.out.println("Такой буквы здесь нет!");
            attemptRate--;
        }
        System.out.println("Осталось попыток: " + attemptRate);

        return updatedMaskedWord.toString();
    }
}