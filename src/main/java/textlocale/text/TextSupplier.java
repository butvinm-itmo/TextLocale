package textlocale.text;

/**
 * Interface for text suppliers.
 * Can be used to simple access to text in class.
 * For example:
 *
 * <pre>
 * public class MyClass {
 *     private static TextSupplier t = TextLocale.getRootPackage::getText;
 * }
 * </pre>
 *
 * @version 1.0
 */
@FunctionalInterface
public interface TextSupplier {
    /**
     * Returns text by key.
     *
     * @param key key of text
     * @return text
     */
    String getText(String key);

    /**
     * Returns text by key with formatting.
     *
     * @param key  key of text
     * @param args arguments for formatting
     * @return text
     */
    default String getText(String key, Object... args) {
        return String.format(getText(key), args);
    }

    /**
     * Alias for {@link #getText(String)}.
     */
    default String t(String key) {
        return getText(key);
    }

    /**
     * Alias for {@link #getText(String, Object...)}.
     */
    default String t(String key, Object... args) {
        return getText(key, args);
    }
}
