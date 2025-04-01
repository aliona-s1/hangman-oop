package com.aliona.hangman;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Dictionary {
    private static final Random RANDOM = new Random();
    private final List<String> words = new ArrayList<>();
    private final String path;

    public Dictionary(String path) {
        this.path = path;
        initialize();
    }

    public String getRandomWord() {
        return words.get(RANDOM.nextInt(words.size()));
    }

    private void initialize() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
             Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }

            if (words.isEmpty()) {
                throw new RuntimeException("The dictionary is empty.");
            }
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException("File is not found.");
        }
    }
}