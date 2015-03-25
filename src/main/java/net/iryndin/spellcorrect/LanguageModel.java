package net.iryndin.spellcorrect;

import java.util.Map;

/**
 * Created by iryndin on 22.03.2015.
 */
public class LanguageModel {
    private final Map<String, Integer> worldFrequencies;

    public LanguageModel(Map<String, Integer> worldFrequencies) {
        this.worldFrequencies = worldFrequencies;
    }

    public Map<String, Integer> getWorldFrequencies() {
        return worldFrequencies;
    }
}
