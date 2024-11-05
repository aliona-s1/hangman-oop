package com.aliona.hangman;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[н]ачать новую игру или [в]ыйти из игры");

        while (true) {
            String letter = scanner.nextLine();

            if (letter.equals("в")) {
                System.out.println("Вы вышли из игры");
                break;
            } else if (letter.equals("н")) {
                System.out.println("Вы начали игру");
                break;
            } else {
                System.out.println("[н]ачать новую игру или [в]ыйти из игры");
            }
        }
    }
}