package com.aliona.hangman;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static final String START = "н";
    private static final String EXIT = "в";

    public static void main(String[] args) {
        startMenu();
    }

    public static void startMenu() {
        while (true) {
            System.out.println("Хотите [н]ачать новую игру или [в]ыйти из игры?");
            String letter = scanner.nextLine();

            if (letter.equals(START)) {
                startGame();
                break;
            } else if (letter.equals(EXIT)) {
                System.out.println("Вы вышли из игры.");
                break;
            } else {
                System.out.println("Некорректный ввод.");
            }
        }
    }

    public static void startGame() {
        System.out.println("Вы начали игру.");
        String word = "телевизор";
        String maskedWord = "_".repeat(word.length());

        startGameLoop(word, maskedWord);
    }

    public static void startGameLoop(String word, String maskedWord) {
        AtomicInteger attemptRate = new AtomicInteger(6);
        List<String> inputtedLetters = new ArrayList<>();

        while (!isGameFinished(maskedWord, attemptRate)) {
            showMaskedWord(maskedWord);
            String letter = inputLetter();

            if (!isInputLetterValid(letter)) {
                System.out.println("Введите одну букву русского алфавита.");
                continue;
            }

            if (isLetterAlreadyInput(letter, inputtedLetters)) {
                System.out.println("Эта буква уже вводилась! Попробуйте другую.");
                continue;
            }

            maskedWord = processGuessedLetter(word, letter, maskedWord, attemptRate);

            inputtedLetters.add(letter);
            System.out.println("Введенные буквы: " + String.join(",", inputtedLetters));
        }

        if (maskedWord.contains("_")) {
            System.out.println("Вы проиграли! Загаданное слово: " + word);
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

    public static boolean isInputLetterValid(String letter) {
        return letter.matches("[а-яА-ЯёЁ]");
    }

    public static boolean isLetterAlreadyInput(String letter, List<String> inputtedLetters) {
        return inputtedLetters.contains(letter);
    }

    private static boolean isLetterInWord(String word, String letter) {
        return word.contains(letter);
    }

    public static String processGuessedLetter(String word, String letter, String maskedWord, AtomicInteger attemptRate) {
        if (!isLetterInWord(word, letter)) {
            System.out.println("Такой буквы в слове нет! Попробуйте другую.");
            attemptRate.decrementAndGet();
        }
        System.out.println("Осталось попыток: " + attemptRate.get());

        return updateMaskedWord(word, maskedWord, letter);
    }

    public static String updateMaskedWord(String word, String maskedWord, String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i + 1, letter);
            }
        }
        return updatedMaskedWord.toString();
    }

    private static boolean isGameFinished(String maskedWord, AtomicInteger attemptRate) {
        return attemptRate.get() == 0 || !maskedWord.contains("_");
    }
}