package net.iryndin.spellcorrect;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by iryndin on 22.03.2015.
 */
public class SpellCorrecter {

    private final LanguageModel languageModel;

    public SpellCorrecter(LanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    public String correct(String word) {
        Set<String> knownWords = new HashSet<>();
        knownWords.addAll(getKnownWords(word));
        if (knownWords.isEmpty()) {
            knownWords.addAll(getKnownWords(EditsGenerator.generateEdits1(word)));
        }
        if (knownWords.isEmpty()) {
            knownWords.addAll(getKnownWords(EditsGenerator.generateEdits2(word)));
        }
        if (knownWords.isEmpty()) {
            knownWords.add(word);
        }
        String corrected = null;
        int max = Integer.MIN_VALUE;
        for (String w : knownWords) {
            Integer freq = languageModel.getWorldFrequencies().get(w);
            if (freq != null && freq > max) {
                max = freq;
                corrected = w;
            }
        }
        return corrected;
    }

    public Set<String> getKnownWords(String... col) {
        Set<String> set = new HashSet<>();
        for (String s : col) {
            if (languageModel.getWorldFrequencies().containsKey(s)) {
                set.add(s);
            }
        }
        return set;
    }

    public Set<String> getKnownWords(Collection<String> col) {
        Set<String> set = new HashSet<>();
        for (String s : col) {
            if (languageModel.getWorldFrequencies().containsKey(s)) {
                set.add(s);
            }
        }
        return set;
    }
}
