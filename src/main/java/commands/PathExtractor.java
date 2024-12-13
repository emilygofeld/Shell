package main.java.commands;

import java.util.ArrayList;
import java.util.List;

public class PathExtractor {

    public static List<String> extractFilePaths(final String data) {
        List<String> filePaths = new ArrayList<>();
        StringBuilder currentPath = new StringBuilder();
        boolean insideSingleQuotes = false;
        boolean insideDoubleQuotes = false;

        for (int i = 0; i < data.length(); i++) {
            char current = data.charAt(i);

            if (isSingleQuote(current, insideDoubleQuotes)) {
                insideSingleQuotes = !insideSingleQuotes;
                currentPath.append(current);
            } else if (isDoubleQuote(current, insideSingleQuotes)) {
                insideDoubleQuotes = !insideDoubleQuotes;
                currentPath.append(current);
            } else if (isEscapedCharacter(current, insideDoubleQuotes)) {
                handleEscapedCharacter(data, currentPath, i);
                i++;
            } else if (isWhitespaceOutsideQuotes(current, insideSingleQuotes, insideDoubleQuotes)) {
                addCurrentPathToList(filePaths, currentPath);
            } else {
                currentPath.append(current);
            }
        }

        if (!currentPath.isEmpty()) {
            filePaths.add(currentPath.toString());
        }

        return filePaths;
    }

    private static boolean isSingleQuote(char current, boolean insideDoubleQuotes) {
        return current == '\'' && !insideDoubleQuotes;
    }

    private static boolean isDoubleQuote(char current, boolean insideSingleQuotes) {
        return current == '"' && !insideSingleQuotes;
    }

    private static boolean isEscapedCharacter(char current, boolean insideDoubleQuotes) {
        return current == '\\' && insideDoubleQuotes;
    }

    private static void handleEscapedCharacter(final String data, StringBuilder currentPath, int i) {
        currentPath.append(data.charAt(i));
        if (i + 1 < data.length()) {
            currentPath.append(data.charAt(i + 1));
        }
    }

    private static boolean isWhitespaceOutsideQuotes(char current, boolean insideSingleQuotes, boolean insideDoubleQuotes) {
        return Character.isWhitespace(current) && !insideSingleQuotes && !insideDoubleQuotes;
    }

    private static void addCurrentPathToList(List<String> filePaths, StringBuilder currentPath) {
        if (!currentPath.isEmpty()) {
            filePaths.add(currentPath.toString());
            currentPath.setLength(0);
        }
    }
}
