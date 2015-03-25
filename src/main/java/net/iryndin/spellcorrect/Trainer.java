package net.iryndin.spellcorrect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by iryndin on 22.03.2015.
 */
public class Trainer {

    public static LanguageModel train(List<String> words) {
        Map<String, Integer> map = new HashMap<>(1024*1024);
        for (String w : words) {
            Integer i = map.get(w);
            if (i ==null) {
                i = 0;
            }
            map.put(w, ++i);
        }
        return new LanguageModel(map);
    }
}
