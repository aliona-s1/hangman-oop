package com.aliona.hangman;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Scanner scanner = new Scanner(System.in);

        Menu menu = new Menu(scanner);
        menu.startMenu(dictionary);
    }
}