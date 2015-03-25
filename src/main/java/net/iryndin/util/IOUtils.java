package net.iryndin.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: iryndin
 * Date: 06.03.13
 * Time: 19:57
 */
public class IOUtils {

    public static final String UTF8 = "UTF-8";

    public static byte[] urlToBytes(String url) throws IOException {
        return toBytes(new URL(url).openConnection().getInputStream());
    }

    public static void bytes2file(byte[] bytes, String filepath) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filepath)) {
            out.write(bytes);
        }
    }

    public static byte[] toBytes(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 1024);
        byte[] buf = new byte[64 * 1024];
        int n;
        while ((n = input.read(buf)) != -1) {
            baos.write(buf, 0, n);
        }
        return baos.toByteArray();
    }

    private static final String CHARSET_MARKER = "charset=";

    public static String urlToString(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        InputStream is = conn.getInputStream();
        String contentType = conn.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int i = contentType.indexOf(CHARSET_MARKER);
            if (i > -1) {
                String charsetName = contentType.substring(i + CHARSET_MARKER.length());
                return toString(is, charsetName);
            }
        }
        return toString(is, UTF8);
    }

    public static String urlToString(String url, int timeoutMillis) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setConnectTimeout(timeoutMillis);
        InputStream is = conn.getInputStream();
        String contentType = conn.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int i = contentType.indexOf(CHARSET_MARKER);
            if (i > -1) {
                String charsetName = contentType.substring(i + CHARSET_MARKER.length());
                return toString(is, charsetName);
            }
        }
        return toString(is, UTF8);
    }

    public static String urlToStringPOST(String url, String postString) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)(new URL(url).openConnection());
        conn.setRequestMethod("POST");
        // set POST params
        {
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postString);
            wr.flush();
            wr.close();
        }
        InputStream is = conn.getInputStream();
        String contentType = conn.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int i = contentType.indexOf(CHARSET_MARKER);
            if (i > -1) {
                String charsetName = contentType.substring(i + CHARSET_MARKER.length());
                return toString(is, charsetName);
            }
        }
        return toString(is, UTF8);
    }

    public static String urlToStringPOST(String url, String postString, String requesetContentType) throws IOException {
        HttpURLConnection conn = (HttpURLConnection)(new URL(url).openConnection());
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", requesetContentType);
        // set POST params
        {
            conn.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postString);
            wr.flush();
            wr.close();
        }
        InputStream is = conn.getInputStream();
        String contentType = conn.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int i = contentType.indexOf(CHARSET_MARKER);
            if (i > -1) {
                String charsetName = contentType.substring(i + CHARSET_MARKER.length());
                return toString(is, charsetName);
            }
        }
        return toString(is, UTF8);
    }

    public static String urlToString(String url, boolean followHttp30xRedirects) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
        conn.setInstanceFollowRedirects(followHttp30xRedirects);
        InputStream is = conn.getInputStream();
        String contentType = conn.getContentType();
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int i = contentType.indexOf(CHARSET_MARKER);
            if (i > -1) {
                String charsetName = contentType.substring(i + CHARSET_MARKER.length());
                return toString(is, charsetName);
            }
        }
        return toString(is, UTF8);
    }

    public static String toString(InputStream input) throws IOException {
        return toString(input, UTF8);
    }

    public static String toString(InputStream input, String charset) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(input, charset)) {
            StringBuilder sb = new StringBuilder(1024 * 32);
            char[] buf = new char[1024];
            int n;
            while ((n = reader.read(buf)) != -1) {
                sb.append(buf, 0, n);
            }
            return sb.toString();
        }
    }

    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int n;
        while ((n = input.read(buffer)) != -1) {
            output.write(buffer, 0, n);
        }
    }

    public static void toFile(byte[] content, String filename) throws IOException {
        try (FileOutputStream out = new FileOutputStream(filename)) {
            out.write(content);
        }
    }

    /**
     * Works not correctly with files larger than 2Gb
     *
     * @param filepath
     * @return
     */
    public static byte[] fileToBytes(String filepath) throws IOException {
        return fileToBytes(new File(filepath));
    }

    /**
     * Works not correctly with files larger than 2Gb
     *
     * @param file
     * @return
     */
    public static byte[] fileToBytes(File file) throws IOException {
        int fsize = (int) file.length();
        int fsize2 = fsize + 10 * 1024;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(fsize2);
        byte[] buf = new byte[fsize2];
        try (FileInputStream input = new FileInputStream(file)) {
            int n;
            while ((n = input.read(buf)) != -1) {
                baos.write(buf, 0, n);
            }
            return baos.toByteArray();
        }
    }

    /**
     * Works not correctly with files larger than 2Gb
     *
     * @param filepath
     * @return
     */
    public static String fileToString(String filepath) throws IOException {
        byte[] bytes = fileToBytes(filepath);
        return new String(bytes);
    }


    /**
     * Works not correctly with files larger than 2Gb
     *
     * @param file
     * @return
     */
    public static String fileToString(File file) throws IOException {
        byte[] bytes = fileToBytes(file);
        return new String(bytes);
    }
    /**
     * Works not correctly with files larger than 2Gb
     *
     * @param filepath
     * @return
     */
    public static String fileToString(String filepath, String charsetName) throws IOException {
        byte[] bytes = fileToBytes(filepath);
        return new String(bytes, charsetName);
    }

    public static boolean fileExists(String filepath) {
        File f = new File(filepath);
        return f.exists();
    }

    public static String fetchFromUrlByRegex(String url, int timeoutMillis, Pattern pattern) throws IOException {
        String content = urlToString(url, timeoutMillis);
        Matcher m = pattern.matcher(content);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    public static void writeLinesToFile(String filename, Collection<String> lines) throws FileNotFoundException {
        writeLinesToStream(new FileOutputStream(filename), lines);
    }

    public static void writeLinesToStream(OutputStream output, Collection<String> lines) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(output)) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }

}
