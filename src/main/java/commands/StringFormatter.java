package main.java.commands;

public class StringFormatter {

    private static final StringBuilder output = new StringBuilder();
    private static boolean insideSingleQuotes = false;
    private static boolean insideDoubleQuotes = false;

    public static String format(String data) {
        output.setLength(0);

        for (int i = 0; i < data.length(); i++) {
            char current = data.charAt(i);

            if (isSingleQuote(current) && !insideDoubleQuotes) {
                boolean isNextSpace = i + 1 < data.length() - 1 && data.charAt(i + 1) == ' ';
                toggleSingleQuoteState(i, isNextSpace);
            } else if (isDoubleQuote(current) && !insideSingleQuotes) {
                boolean isNextSpace = i + 1 < data.length() - 1 && data.charAt(i + 1) == ' ';
                toggleDoubleQuoteState(i, isNextSpace);
            } else if (isBackslash(current) && insideDoubleQuotes) {
                handleBackslashesInsideQuotes(data, i);
                i++;
            } else if (isBackslash(current) && !insideSingleQuotes) {
                handleBackslashesOutsideQuotes(data, i);
                i++;
            } else if (!insideDoubleQuotes && !insideSingleQuotes) {
                handleDataOutsideQuotes(data.charAt(i));
            }
            else {
                output.append(data.charAt(i));
            }
        }

        return output.toString();
    }

    private static boolean isSingleQuote(char current) {
        return current == '\'';
    }

    private static boolean isDoubleQuote(char current) {
        return current == '"';
    }

    private static boolean isBackslash(char current) {
        return current == '\\';
    }

    private static void toggleSingleQuoteState(int index, boolean isNextSpace) {
        insideSingleQuotes = !insideSingleQuotes;

        if (index != 0 && isNextSpace)
            output.append(" ");
    }

    private static void toggleDoubleQuoteState(int index, boolean isNextSpace) {
        insideDoubleQuotes = !insideDoubleQuotes;

        if (index != 0  && isNextSpace)
            output.append(" ");
    }

    private static void handleBackslashesInsideQuotes(String data, int i) {
        if (i + 1 < data.length()) {
            char next = data.charAt(i + 1);
            if (next == '\\' || next == '$' || next == '"' || next == '`' || next == '\n') {
                output.append(next);
            } else {
                output.append('\\');
                output.append(next);
            }
        } else {
            output.append('\\');
        }
    }

    private static void handleBackslashesOutsideQuotes(String data, int i) {
        if (i + 1 < data.length()) {
            char next = data.charAt(i + 1);
            if (next == '\\' || next == '$' || next == '"' || next == '`' || next == '\n') {
                output.append(next);
            } else {
                output.append('\\');
                output.append(next);
            }
        } else {
            output.append('\\');
        }
    }

    private static void handleDataOutsideQuotes(char data) {
        if (data != ' ') output.append(data);
    }
}
