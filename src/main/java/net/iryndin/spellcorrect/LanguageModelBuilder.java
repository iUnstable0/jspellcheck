package net.iryndin.spellcorrect;

import net.iryndin.util.IOUtils;
import net.iryndin.util.TextTokenizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by iryndin on 22.03.2015.
 */
public class LanguageModelBuilder {

    public static LanguageModel build(String dictpath) throws IOException {
        String text = IOUtils.toString(new FileInputStream(dictpath));
        List<String> words = new TextTokenizer().tokenize(text);
        return Trainer.train(words);
    }
}
