/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2013, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.thymeleaf.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

import org.unbescape.html.HtmlEscape;
import org.unbescape.java.JavaEscape;
import org.unbescape.javascript.JavaScriptEscape;


/**
 * <p>
 * Utility methods for String objects.
 * </p>
 * <p>
 * This class is used as a basis for the methods offered by
 * {@link org.thymeleaf.expression.Strings}, which in turn are the
 * methods offered by the <tt>#strings</tt> utility object in variable
 * expressions.
 * </p>
 *
 * @author Daniel Fern&aacute;ndez
 * @author Le Roux Bernard
 * @author Soraya S&aacute;nchez Labandeira
 * @since 1.0
 */
public final class StringUtils {

    private static final String ALPHA_NUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();



    /**
     * <p>
     * Performs a null-safe <tt>toString()</tt> operation.
     * </p>
     *
     * @param target the object on which toString will be executed
     * @return the result of calling <tt>target.toString()</tt> if target is not null,
     *         <tt>null</tt> if target is null.
     * @since 2.0.12
     */
    public static String toString(final Object target) {
        if (target == null) {
            return null;
        }
        return target.toString();
    }



    public static String abbreviate(final Object target, final int maxSize) {

        Validate.isTrue(maxSize >= 3, "Maximum size must be greater or equal to 3");

        if (target == null) {
            return null;
        }

        final String str = target.toString();
        if (str.length() <= maxSize) {
            return str;
        }

        return str.substring(0, maxSize - 3) + "...";

    }



    /**
     * @since 2.0.16
     */
    public static Boolean equals(final Object first, final Object second) {

        if (first == null && second == null) {
            return Boolean.TRUE;
        }
        if (first == null || second == null) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(first.toString().equals(second.toString()));

    }

    /**
     * @since 2.0.16
     */
    public static Boolean equalsIgnoreCase(final Object first, final Object second) {
        if (first == null && second == null) {
            return Boolean.TRUE;
        }
        if (first == null || second == null) {
            return Boolean.FALSE;
        }
        return Boolean.valueOf(first.toString().equalsIgnoreCase(second.toString()));
    }

    public static Boolean contains(final Object target, final String fragment) {

        Validate.notNull(target, "Cannot apply contains on null");
        Validate.notNull(fragment, "Fragment cannot be null");

        return Boolean.valueOf(target.toString().contains(fragment));

    }



    public static Boolean containsIgnoreCase(final Object target, final String fragment, final Locale locale) {

        Validate.notNull(target, "Cannot apply containsIgnoreCase on null");
        Validate.notNull(fragment, "Fragment cannot be null");
        Validate.notNull(locale, "Locale cannot be null");

        return Boolean.valueOf(target.toString().toUpperCase(locale).contains(fragment.toUpperCase(locale)));

    }



    public static Boolean startsWith(final Object target, final String prefix) {

        Validate.notNull(target, "Cannot apply startsWith on null");
        Validate.notNull(prefix, "Prefix cannot be null");

        return Boolean.valueOf(target.toString().startsWith(prefix));

    }



    public static Boolean endsWith(final Object target, final String suffix) {

        Validate.notNull(target, "Cannot apply endsWith on null");
        Validate.notNull(suffix, "Suffix cannot be null");

        return Boolean.valueOf(target.toString().endsWith(suffix));

    }



    public static String substring(final Object target, final int beginIndex, final int endIndex) {

        Validate.notNull(target, "Cannot apply substring on null");
        Validate.isTrue(beginIndex >= 0, "Begin index must be >= 0");

        // The String constructor is called on purpose to avoid problems from
        // creating substrings out of large amounts of long Strings (creating
        // a substring does not free the memory occupied by the original String).
        return new String(target.toString().substring(beginIndex, endIndex));

    }


    /**
     * <p>
     * copy a part of target start beginIndex to the end of target.
     * If non-String object, toString() will be called.
     * </p>
     *
     * @param target source of the copy.
     * @param beginIndex index where the copy start.
     * @return part of target.
     * @since 1.1.2
     */
    public static String substring(final Object target, final int beginIndex) {

        Validate.notNull(target, "Cannot apply substring on null");
        final String str = target.toString();
        final int len = str.length();
        Validate.isTrue(beginIndex >= 0 && beginIndex < len, "beginIndex must be >= 0 and < " + len);

        // The String constructor is called on purpose to avoid problems from
        // creating substrings out of large amounts of long Strings (creating
        // a substring does not free the memory occupied by the original String).
        return new String(str.substring(beginIndex));
    }



    public static String substringAfter(final Object target, final String substr) {

        Validate.notNull(target, "Cannot apply substringAfter on null");
        Validate.notNull(substr, "Parameter substring cannot be null");

        final String str = target.toString();
        final int index = str.indexOf(substr);
        if (index < 0) {
            return null;
        }

        // The String constructor is called on purpose to avoid problems from
        // creating substrings out of large amounts of long Strings (creating
        // a substring does not free the memory occupied by the original String).
        return new String(str.substring(index + substr.length()));

    }



    public static String substringBefore(final Object target, final String substr) {

        Validate.notNull(target, "Cannot apply substringBefore on null");
        Validate.notNull(substr, "Parameter substring cannot be null");

        final String str = target.toString();
        final int index = str.indexOf(substr);
        if (index < 0) {
            return null;
        }

        // The String constructor is called on purpose to avoid problems from
        // creating substrings out of large amounts of long Strings (creating
        // a substring does not free the memory occupied by the original String).
        return new String(str.substring(0, index));

    }



    public static String prepend(final Object target, final String prefix) {
        Validate.notNull(target, "Cannot apply prepend on null");
        Validate.notNull(prefix, "Prefix cannot be null");
        return prefix + target;
    }



    public static String append(final Object target, final String suffix) {
        Validate.notNull(target, "Cannot apply append on null");
        Validate.notNull(suffix, "Suffix cannot be null");
        return target + suffix;
    }


    /**
     * @since 2.1.0
     */
    public static String repeat(final Object target, final int times) {
        if (target == null) {
            return null;
        }
        final String str = target.toString();
        final StringBuilder strBuilder = new StringBuilder(str.length() * times + 10);
        for (int i = 0; i < times; i++) {
            strBuilder.append(str);
        }
        return strBuilder.toString();
    }


    /**
     * @since 2.0.16
     */
    public static String concat(final Object... values) {
        return concatReplaceNulls("", values);
    }

    /**
     * @since 2.0.16
     */
    public static String concatReplaceNulls(final String nullValue, final Object... values) {

        if (values == null) {
            return "";
        }

        final StringBuilder sb = new StringBuilder();
        for (final Object value : values) {
            if (value == null) {
                sb.append(nullValue);
            } else {
                sb.append(value.toString());
            }
        }

        return sb.toString();

    }

    public static Integer indexOf(final Object target, final String fragment) {

        Validate.notNull(target, "Cannot apply indexOf on null");
        Validate.notNull(fragment, "Fragment cannot be null");

        return Integer.valueOf(target.toString().indexOf(fragment));

    }



    /**
     *
     * @since 2.1.0
     */
    public static boolean isEmpty(final String target) {
        return target == null || target.length() == 0;
    }



    public static boolean isEmptyOrWhitespace(final String target) {
        if (target == null) {
            return true;
        }
        final int targetLen = target.length();
        if (targetLen == 0) {
            return true;
        }
        final char c0 = target.charAt(0);
        if ((c0 >= 'a' && c0 <= 'z') || (c0 >= 'A' && c0 <= 'Z')) {
            // Fail fast, by quickly checking first char without executing Character.isWhitespace(...)
            return false;
        }
        for (int i = 0; i < targetLen; i++) {
            final char c = target.charAt(i);
            if (c != ' ' && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }



    public static String join(final Object[] target, final String separator) {

        Validate.notNull(target, "Cannot apply join on null");
        Validate.notNull(separator, "Separator cannot be null");

        final StringBuilder sb = new StringBuilder();
        if (target.length > 0) {
            sb.append(target[0]);
            for (int i = 1; i < target.length; i++) {
                sb.append(separator);
                sb.append(target[i]);
            }
        }
        return sb.toString();
    }



    public static String join(final Iterable<?> target, final String separator) {

        Validate.notNull(target, "Cannot apply join on null");
        Validate.notNull(separator, "Separator cannot be null");

        final StringBuilder sb = new StringBuilder();
        final Iterator<?> it = target.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next());
            }
        }
        return sb.toString();

    }


    public static String join(final Iterable<?> target, final char separator) {

        Validate.notNull(target, "Cannot apply join on null");

        final StringBuilder sb = new StringBuilder();
        final Iterator<?> it = target.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(separator);
                sb.append(it.next());
            }
        }
        return sb.toString();

    }



    public static String[] split(final Object target, final String separator) {

        Validate.notNull(target, "Cannot apply split on null");
        Validate.notNull(separator, "Separator cannot be null");

        final StringTokenizer strTok = new StringTokenizer(target.toString(), separator);
        final int size = strTok.countTokens();
        final String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = strTok.nextToken();
        }
        return array;
    }



    public static Integer length(final Object target) {

        Validate.notNull(target, "Cannot apply length on null");

        return Integer.valueOf(target.toString().length());

    }



    public static String replace(final Object target, final String before, final String after) {

        Validate.notNull(target, "Cannot apply replace on null");
        Validate.notNull(before, "Parameter \"before\" cannot be null");
        Validate.notNull(after, "Parameter \"after\" cannot be null");

        return target.toString().replace(before, after);

    }



    public static String toUpperCase(final Object target, final Locale locale) {

        Validate.notNull(locale, "Locale cannot be null");

        if (target == null) {
            return null;
        }
        return target.toString().toUpperCase(locale);

    }



    public static String toLowerCase(final Object target, final Locale locale) {

        Validate.notNull(locale, "Locale cannot be null");

        if (target == null) {
            return null;
        }
        return target.toString().toLowerCase(locale);

    }



    public static String trim(final Object target) {
        if (target == null) {
            return null;
        }
        return target.toString().trim();
    }


    /**
     * <p>
     * Convert the first letter of target to uppercase (title-case, in fact).
     * </p>
     *
     * @param target the String to be capitalized. If non-String object, toString()
     * will be called.
     * @return String the result of capitalizing the target.
     * @since 1.1.2
     */
    public static String capitalize(final Object target) {

        if (target == null) {
            return null;
        }
        final StringBuilder result = new StringBuilder(target.toString());
        if (result.length() > 0) {
            result.setCharAt(0, Character.toTitleCase(result.charAt(0)));
        }
        return result.toString();

    }



    /**
     * <p>
     * Convert the first letter of target to lowercase.
     * </p>
     *
     * @param target the String to be uncapitalized. If non-String object, toString()
     * will be called.
     * @return String the result of uncapitalizing the target.
     * @since 1.1.2
     */
    public static String unCapitalize(final Object target) {

        if (target == null) {
            return null;
        }
        final StringBuilder result = new StringBuilder(target.toString());

        if (result.length() > 0) {
            result.setCharAt(0, Character.toLowerCase(result.charAt(0)));
        }

        return result.toString();
    }



    private static int findNextWord(final char[] buffer, final int idx, final char[] delimiterChars) {

        final int len = buffer.length;

        if (idx < 0 || idx >= len) {
            return -1;
        }

        boolean foundDelimiters = (idx == 0);
        int i = idx;
        while (i < len) {
            final char ch = buffer[i];
            final boolean isDelimiter =
                    (delimiterChars == null ?
                            (Character.isWhitespace(ch)) :
                            (Arrays.binarySearch(delimiterChars, ch) >= 0));
            if (isDelimiter) {
                foundDelimiters = true;
            } else {
                if (foundDelimiters) {
                    return i;
                }
            }
            i++;
        }

        return -1;

    }



    /**
     * <p>
     * Convert all the first letter of the words of target
     * to uppercase (title-case, in fact).
     * The default delimiter characters between the words
     * are the whitespace characters
     * (see Characters.IsWhiteSpace method in the Java doc).
     * </p>
     *
     * @param target the String to be capitalized. If non-String object, toString()
     * will be called.
     * @return String the result of capitalizing the target.
     * @since 1.1.2
     */
    public static String capitalizeWords(final Object target) {
        return capitalizeWords(target, null);
    }



    /**
     * <p>
     * Convert all the first letter of the words of target to uppercase
     * (title-case, in fact), using the specified delimiter chars for determining
     * word ends/starts.
     * </p>
     *
     * @param target the String to be capitalized. If non-String object, toString()
     * will be called.
     * @param delimiters delimiters of the words. If non-String object, toString()
     * will be called.
     * @return String the result of capitalizing the target.
     * @since 1.1.2
     */
    public static String capitalizeWords(final Object target, final Object delimiters) {

        if (target == null) {
            return null;
        }

        final char[] buffer = target.toString().toCharArray();
        final char[] delimiterChars =
                (delimiters == null ? null : delimiters.toString().toCharArray());
        if (delimiterChars != null) {
            // needed in order to use binarySearch
            Arrays.sort(delimiterChars);
        }

        int idx = 0;

        idx = findNextWord(buffer, idx, delimiterChars);
        while (idx != -1) {
            buffer[idx] = Character.toTitleCase(buffer[idx]);
            idx++;
            idx = findNextWord(buffer, idx, delimiterChars);
        }

        return new String(buffer);

    }



    /**
     * <p>
     * XML-escapes the specified text.
     * </p>
     *
     * @param target the text to be escaped
     * @return the escaped text.
     * @since 2.0.9
     */
    public static String escapeXml(final Object target) {
        if (target == null) {
            return null;
        }
        return HtmlEscape.escapeHtml4Xml(target.toString());
    }



    /**
     * <p>
     * Escapes the specified target text as required for JavaScript code.
     * </p>
     *
     * @param target the text to be escaped
     * @return the escaped text.
     * @since 2.0.11
     */
    public static String escapeJavaScript(final Object target) {
        if (target == null) {
            return null;
        }
        return JavaScriptEscape.escapeJavaScript(target.toString());
    }



    /**
     * <p>
     * Escapes the specified target text as required for Java code.
     * </p>
     *
     * @param target the text to be escaped
     * @return the escaped text.
     * @since 2.0.11
     */
    public static String escapeJava(final Object target) {
        if (target == null) {
            return null;
        }
        return JavaEscape.escapeJava(target.toString());
    }



    /**
     * <p>
     * Un-escapes the specified JavaScript-escaped target text back to normal form.
     * </p>
     *
     * @param target the text to be unescaped
     * @return the unescaped text.
     * @since 2.0.11
     */
    public static String unescapeJavaScript(final Object target) {
        if (target == null) {
            return null;
        }
        return JavaScriptEscape.unescapeJavaScript(target.toString());
    }



    /**
     * <p>
     * Un-escapes the specified Java-escaped target text back to normal form.
     * </p>
     *
     * @param target the text to be unescaped
     * @return the unescaped text.
     * @since 2.0.11
     */
    public static String unescapeJava(final Object target) {
        if (target == null) {
            return null;
        }
        return JavaEscape.unescapeJava(target.toString());
    }



    public static String randomAlphanumeric(final int count) {
        final StringBuilder strBuilder = new StringBuilder(count);
        final int anLen = ALPHA_NUMERIC.length();
        synchronized (RANDOM) {
            for (int i = 0; i < count; i++) {
                strBuilder.append(ALPHA_NUMERIC.charAt(RANDOM.nextInt(anLen)));
            }
        }
        return strBuilder.toString();
    }



    private StringUtils() {
        super();
    }


}
