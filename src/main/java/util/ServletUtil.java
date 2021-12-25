package util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ServletUtil {
    public static String getBody(HttpServletRequest request) throws IOException {
        String body;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    public static Long getIdFromRequest(HttpServletRequest request) {
        final String pathInfo = request.getPathInfo();
        final List<String> pathParts = Arrays.stream(pathInfo.split("/"))
            .filter(el -> !el.isEmpty())
            .collect(Collectors.toList());

        return Long.valueOf(pathParts.get(0));
    }

    public static boolean checkIfPathContainsId(String pathInfo) {
        final List<String> pathParts = Arrays.stream(pathInfo.split("/"))
            .filter(el -> !el.isEmpty())
            .collect(Collectors.toList());

        if (pathParts.size() == 1) {
            try {
                Long.valueOf(pathParts.get(0));
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        return false;
    }

    private ServletUtil() {}
}
