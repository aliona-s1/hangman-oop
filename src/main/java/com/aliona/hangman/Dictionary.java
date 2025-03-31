package com.aliona.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
    private static final Random RANDOM = new Random();
    private static final String DICTIONARY_PATH = "dictionary.txt";
    private final List<String> words = new ArrayList<>();

    public Dictionary() {
        initializeDictionary();
    }

    public String getRandomWord() {
        return words.get(RANDOM.nextInt(words.size()));
    }

    private void initializeDictionary() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DICTIONARY_PATH);
             Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }

            if (words.isEmpty()) {
                throw new RuntimeException("Словарь пуст.");
            }
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException("Файл не найден.");
        }
    }
}