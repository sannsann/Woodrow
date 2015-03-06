package com.stormcloud.woodrow.DictionaryEntry;

/**
 * * Created by schhan on 2/28/15.
 */
public class DictionaryEntry {

    private String entryWord;
    private String entrySyllables;
    private String entryPronunciation;
    private String entryDefinition;


    public DictionaryEntry(String word, String syllables, String pronunciation, String definition) {

        this.entryWord = word;
        this.entrySyllables = syllables;
        this.entryPronunciation = pronunciation;
        this.entryDefinition = definition;

    }

    @Override
    public String toString() {
        return "Word: " + entryWord + "\nPronunciation: " + entryPronunciation +
                "\nDefinition: " + entryDefinition;
    }

    public String getEntryDefinition() {
        return entryDefinition;
    }
}
