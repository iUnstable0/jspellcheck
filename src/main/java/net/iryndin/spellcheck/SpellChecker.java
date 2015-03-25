package net.iryndin.spellcheck;

import net.iryndin.util.IOUtils;
import net.iryndin.util.TextTokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 */
public class SpellChecker {

    private final Set<String> w1Words;
    private final Set<String> w2Words;
    private final Pattern digitsPattern = Pattern.compile("[0-9]+");
    private final Set<String> dict;

    public SpellChecker() {
        try {
            w1Words = Collections.unmodifiableSet(new HashSet<>(readResourceFile("w1.txt")));
            w2Words = Collections.unmodifiableSet(new HashSet<>(readResourceFile("w2.txt")));
            dict = Collections.unmodifiableSet(new HashSet<>(readResourceFile("dictionary.txt", "webster.txt")));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static List<String> readResourceFile(String... resourcePath) throws IOException {
        if (resourcePath == null || resourcePath.length == 0) {
            return Collections.emptyList();
        } else if (resourcePath.length == 1) {
            return readResourceFile(resourcePath[0]);
        } else {
            List<String> list = readResourceFile(resourcePath[0]);
            for (int i=1; i<resourcePath.length; i++) {
                list.addAll(readResourceFile(resourcePath[i]));
            }
            return list;
        }
    }

    private static List<String> readResourceFile(String resourcePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(SpellChecker.class.getClassLoader().getResourceAsStream(resourcePath)))) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                list.add(line);
            }
            return list;
        }
    }

    public List<String> spellcheck(File file) throws IOException {
        return spellcheck(IOUtils.fileToString(file));
    }

    public List<String> spellcheck(String text) {
        List<String> tokens = new TextTokenizer().tokenizeCasePreserve(text);
        List<String> incorrectTokens = new ArrayList<>();
        for (String token : tokens) {
            if (!checkToken(token)) {
                incorrectTokens.add(token);
            }
        }
        return incorrectTokens;
    }

    private boolean checkToken(String token) {
        if (digitsPattern.matcher(token).matches()) {
            return true;
        }
        String lower = token.toLowerCase();
        if (token.length() == 1) {
            return w1Words.contains(lower);
        } else if (token.length() == 2) {
            return w2Words.contains(lower);
        } else {
            return dict.contains(lower);
        }
    }

    public static void main(String[] args) throws IOException {
        SpellChecker sc = new SpellChecker();
        List<String> incorrect = sc.spellcheck(new File("c:/prj/spellcheck/webster.txt"));
        IOUtils.writeLinesToFile("incorrect-webster.txt", new TreeSet<>(incorrect));
    }
}
