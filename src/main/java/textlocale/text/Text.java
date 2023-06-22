package textlocale.text;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import lombok.Data;

/**
 * Dynamic container for localized text.
 */
@Data
public class Text {
    /**
     * Key of text.
     */
    private final String key;

    /**
     * Supplier of current locale.
     */
    private final Supplier<String> locale;

    /**
     * Map of localized strings.
     */
    private final Map<String, String> localizedStrings;

    /**
     * Create new text with given key, locale and map of localized strings.
     *
     * @param key    key
     * @param locale locale
     * @param localizedStrings values
     */
    @Override
    public String toString() {
        return this.localizedStrings.get(this.locale.get());
    }
}
