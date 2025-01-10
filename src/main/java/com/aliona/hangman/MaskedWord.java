package com.aliona.hangman;

public class MaskedWord {
    private static final String MASKING_SYMBOL = "*";
    private final String secretWord;
    private String mask;

    public MaskedWord(Dictionary dictionary) {
        this.secretWord = dictionary.getRandomWord();
        this.mask = MASKING_SYMBOL.repeat(secretWord.length());
    }

    public String getSecretWord() {
        return secretWord;
    }

    public String getMask() {
        return mask;
    }

    public boolean isLetterInWord(String letter) {
        return secretWord.contains(letter);
    }

    public void updateMask(String letter) {
        StringBuilder updatedMask = new StringBuilder(mask);

        for (int i = 0; i < secretWord.length(); i++) {
            String currentLetter = secretWord.substring(i, i + 1);

            if (currentLetter.equals(letter)) {
                updatedMask.replace(i, i + 1, currentLetter);
            }
        }
        this.mask = updatedMask.toString();
    }

    public boolean isSecretWordGuessed() {
        return mask.equals(secretWord);
    }
}