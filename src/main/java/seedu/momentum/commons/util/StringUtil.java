//@@author

package seedu.momentum.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.momentum.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null.
     * @param word     cannot be null, cannot be empty, must be a single word.
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String[] wordsInPreppedSentence = sentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    //@@author pr4aveen

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a partial match is sufficient.
     * <br>examples:<pre>
     *       containsPartialIgnoreCase("ABc def", "abc") == true
     *       containsPartialIgnoreCase("ABc def", "DEF") == true
     *       containsPartialIgnoreCase("ABc def", "AB") == true //partial word match
     *       </pre>
     *
     * @param sentence Sentence to check.
     * @param word     Word to check sentence for.
     */
    public static boolean containsPartialIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedKeyword = word.trim().toLowerCase();
        String preppedSentence = sentence.toLowerCase();

        checkArgument(!preppedKeyword.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedKeyword.split("\\s+").length == 1, "Word parameter should be a single word");

        return preppedSentence.contains(preppedKeyword);
    }
    //@@author

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //@@author boundtotheearth

    /**
     * Formats a value in minutes into hr:min format.
     *
     * @param value An amount of time in minutes.
     * @return The formatted string.
     */
    public static String formatMinutesToString(double value) {
        String output = "";
        int hours = (int) Math.floor(value / 60);
        int minutes = (int) value % 60;

        if (hours > 0) {
            output += String.format("%d hr ", hours);
        }

        if (minutes > 0) {
            output += String.format("%d min", minutes);
        }

        return output;
    }
}
