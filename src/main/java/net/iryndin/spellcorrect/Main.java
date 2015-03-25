package net.iryndin.spellcorrect;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws IOException {
        LanguageModel lm = LanguageModelBuilder.build("dict.txt");
        SpellCorrecter sc = new SpellCorrecter(lm);
        Map<String, String> test = new HashMap<>();
        test.put("acess", "access");
        test.put("accesing", "accessing");
        for (String w : test.keySet()) {
            String s = sc.correct(w);
            System.out.println(s);
        }
    }
}
