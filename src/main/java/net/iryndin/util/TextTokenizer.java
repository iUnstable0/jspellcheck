package net.iryndin.util;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by iryndin on 22.03.2015.
 */
public class TextTokenizer {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public List<String> tokenize(String text) {
        List<String> list = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(text, " \t\n\r\f~`!@#$%^&*()-_+={[}]?/>.<,\\|", false);
        while (st.hasMoreTokens()) {
            String token = st.nextToken().toLowerCase();
            StringBuilder sb = new StringBuilder(token.length());
            for (char c : token.toCharArray()) {
                if (Character.isAlphabetic(c)) {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                list.add(sb.toString());
            }
        }
        return list;
    }

    public List<String> tokenizeCasePreserve(String text) {
        List<String> list = new LinkedList<>();
        StringTokenizer st = new StringTokenizer(text, " \t\n\r\f~`!@#$%^&*()-_+={[}]?/>.<,\\|", false);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            StringBuilder sb = new StringBuilder(token.length());
            for (char c : token.toCharArray()) {
                if (Character.isAlphabetic(c)) {
                    sb.append(c);
                }
            }
            if (sb.length() > 0) {
                list.add(sb.toString());
            }
        }
        return list;
    }


    /*
    public static void main(String[] args) throws IOException {
        String text = IOUtils.toString(new FileInputStream("C:\\prj\\jdbf\\jdbf.git\\README.md"));
        System.out.println(new TextTokenizer().tokenize(text));
    }
    */
}
