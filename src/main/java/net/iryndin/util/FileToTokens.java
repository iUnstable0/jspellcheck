package net.iryndin.util;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by iryndin on 24.03.2015.
 */
public class FileToTokens {

    public static Collection<String> getTokens(String filename) throws IOException {
        String text = IOUtils.fileToString(filename);
        List<String> words = new TextTokenizer().tokenize(text);
        return new TreeSet<>(words);
    }

    public static void main(String[] args) throws IOException {
        Collection<String> tokens = getTokens("big.txt");
        IOUtils.writeLinesToFile("dict.txt", tokens);


    }
}
