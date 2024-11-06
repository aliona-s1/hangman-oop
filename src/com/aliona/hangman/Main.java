package com.aliona.hangman;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("[н]ачать новую игру или [в]ыйти из игры.");
            String letter = scanner.nextLine();

            if (letter.equals("в")) {
                System.out.println("Вы вышли из игры.");
                break;
            } else if (letter.equals("н")) {
                System.out.println("Вы начали игру.");
                showMaskedWord();
                break;
            } else {
                System.out.println("Некорректный ввод.");
            }
        }
    }

    public static void showMaskedWord() {
        String word = "телевизор";
        String maskedWord = word.replaceAll(".", "_");

        System.out.println("Текущее слово: " + maskedWord);
    }

    public static void revealGuessedLetters() {
        // TODO: implement
    }
}