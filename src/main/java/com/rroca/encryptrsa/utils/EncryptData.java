package com.rroca.encryptrsa.utils;

/**
 * EncryptData class used in API
 */
public class EncryptData {
    private String seed;
    private String text;

    /**
     * @return seed
     */
    public String getSeed() {
        return seed;
    }

    /**
     * @param seed the seed
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }

    /**
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }
}
