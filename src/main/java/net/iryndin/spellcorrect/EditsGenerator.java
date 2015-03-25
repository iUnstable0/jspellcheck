package net.iryndin.spellcorrect;

import java.util.*;

/**
 *
 */
public class EditsGenerator {

    private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static List<String[]> generateSplits(String s) {
        if (s.isEmpty()) {
            return Collections.emptyList();
        }
        List<String[]> list = new ArrayList<>(s.length()+10);
         for (int i=0; i<s.length(); i++) {
            String[] a = {s.substring(0, i), s.substring(i, s.length())};
             list.add(a);
        }
        list.add(new String[]{s, ""});
        return list;
    }

    public static List<String> generateDeletes(String s) {
        if (s.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(s.length()+10);
        for (int i=0; i<s.length(); i++) {
            String a = (i>0 ? s.substring(0,i) : "") + s.substring(i+1);
            list.add(a);
        }
        return list;
    }

    public static List<String> generateTrasposes(String s) {
        if (s.isEmpty()) {
            return Collections.emptyList();
        }
        char[] cc = s.toCharArray();
        List<String> list = new ArrayList<>(s.length()+10);
        for (int i=0; i<cc.length-1; i++) {
            char[] a = Arrays.copyOf(cc, cc.length);
            a[i] = cc[i+1];
            a[i+1] = cc[i];
            list.add(new String(a));
        }
        return list;
    }

    public static List<String> generateReplaces(String s) {
        if (s.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(s.length()*ALPHABET.length);
        for (int i=0; i<s.length(); i++) {
            for (char c : ALPHABET) {
                String a = (i > 0 ? s.substring(0, i) : "") + c + s.substring(i + 1);
                list.add(a);
            }
        }
        return list;
    }

    public static List<String> generateInserts(String s) {
        if (s.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(s.length()*ALPHABET.length);
        for (int i=0; i<s.length(); i++) {
            for (char c : ALPHABET) {
                String a = (i > 0 ? s.substring(0, i) : "") + c + s.substring(i);
                list.add(a);
            }
        }
        return list;
    }

    public static Set<String> generateEdits1(Collection<String> col) {
        Set<String> all = new HashSet<>();
        for (String s : col) {
            all.addAll(generateEdits1(s));
        }
        return all;
    }

    public static Set<String> generateEdits1(String s) {
        Set<String> all = new HashSet<>(generateDeletes(s));
        all.addAll(generateTrasposes(s));
        all.addAll(generateTrasposes(s));
        all.addAll(generateReplaces(s));
        all.addAll(generateInserts(s));
        return all;
    }

    public static Set<String> generateEdits2(String w) {
        Set<String> set1 = generateEdits1(w);
        Set<String> set2 = generateEdits1(set1);
        set1.addAll(set2);
        return set1;
    }

    public static void main(String[] args) {
        String word = "something";
        /*
        List<String[]> splits = generateSplits(word);
        for (String[] a : splits) {
            System.out.println(Arrays.toString(a));
        }
        */
        Collection<String> all = generateEdits2(word);
        //for (String a : all) {
        //    System.out.println(a);
       // }
        System.out.println(all.size());
    }
}
