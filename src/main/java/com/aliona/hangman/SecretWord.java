package com.aliona.hangman;

public class SecretWord {
    private static final String MASKING_SYMBOL = "*";
    private final String word;
    private String maskedWord;

    public SecretWord(Dictionary dictionary) {
        this.word = dictionary.getRandomWord();
        this.maskedWord = MASKING_SYMBOL.repeat(word.length());
    }

    public String getWord() {
        return word;
    }

    public String getMaskedWord() {
        return maskedWord;
    }

    public void showMaskedWord() {
        System.out.println(maskedWord);
    }

    public boolean isLetterInWord(String letter) {
        return word.contains(letter);
    }

    public void updateMaskedWord(String letter) {
        StringBuilder updatedMaskedWord = new StringBuilder(maskedWord);

        for (int i = 0; i < word.length(); i++) {
            String currentLetter = word.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMaskedWord.replace(i, i + 1, currentLetter);
            }
        }
        this.maskedWord = updatedMaskedWord.toString();
    }
}