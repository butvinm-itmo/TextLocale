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
    Text getText(String key);

    /**
     * Returns string by key.
     *
     * @param key  key of text
     * @return string
     */
    default String getString(String key) {
        return getText(key).toString();
    }

    /**
     * Returns string by key with formatting.
     *
     * @param key  key of text
     * @param args arguments for formatting
     * @return string
     */
    default String getString(String key, Object... args) {
        return String.format(getString(key), args);
    }

    /**
     * Alias for {@link #getText(String)}.
     */
    default Text t(String key) {
        return getText(key);
    }

    /**
     * Alias for {@link #getText(String, Object...)}.
     */
    default String s(String key) {
        return getString(key);
    }

    /**
     * Alias for {@link #getText(String, Object...)}.
     */
    default String s(String key, Object... args) {
        return getString(key, args);
    }
}
