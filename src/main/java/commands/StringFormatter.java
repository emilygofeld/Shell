package main.java.commands;

public class StringFormatter {

    private static final StringBuilder output = new StringBuilder();
    private static boolean insideSingleQuotes = false;
    private static boolean insideDoubleQuotes = false;

//    public static String format(String data) {
//        output.setLength(0);
//
//        for (int i = 0; i < data.length(); i++) {
//            char current = data.charAt(i);
//
//            if (isSingleQuote(current) && !insideDoubleQuotes) {
//                toggleSingleQuoteState();
//            } else if (isDoubleQuote(current) && !insideSingleQuotes) {
//                toggleDoubleQuoteState();
//            } else if (isBackslash(current) && insideDoubleQuotes) {
//                handleBackslashesInsideQuotes(data, i);
//                i++;
//            } else if (isBackslash(current) && !insideSingleQuotes) {
//                handleBackslashesOutsideQuotes(data, i);
//                i++;
//            } else {
//                output.append(current);
//            }
//        }
//
//        return output.toString();
//    }


    public static String format(String data) {
        StringBuilder output = new StringBuilder();
        boolean insideSingleQuotes = false;
        boolean insideDoubleQuotes = false;

        for (int i = 0; i < data.length(); i++) {
            char current = data.charAt(i);

            if (current == '\'' && !insideDoubleQuotes) {
                insideSingleQuotes = !insideSingleQuotes;
            } else if (current == '"' && !insideSingleQuotes) {
                insideDoubleQuotes = !insideDoubleQuotes;
            } else if (current == '\\' && insideDoubleQuotes && i + 1 < data.length()) {
                char next = data.charAt(i + 1);
                if (next == '\\' || next == '$' || next == '"' || next == '`' || next == '\n') {
                    output.append(next);
                    i++; // Skip the escaped character
                } else {
                    output.append('\\'); // Append the backslash as-is
                }
            } else {
                output.append(current);
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

    private static void toggleSingleQuoteState() {
        insideSingleQuotes = !insideSingleQuotes;
    }

    private static void toggleDoubleQuoteState() {
        insideDoubleQuotes = !insideDoubleQuotes;
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
}
