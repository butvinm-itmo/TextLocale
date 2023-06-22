package textlocale.text;

import java.util.List;

import lombok.Data;
import textlocale.TextLocale;

/**
 * Container for localized texts.
 *
 * @see TextLocale
 */
@Data
public class TextPackage implements TextSupplier {
    /**
     * Node with package texts.
     */
    private final TextNode textNode;

    /**
     * Return node key.
     */
    public String getName() {
        return this.textNode.getKey();
    }

    /**
     * Get text by key.
     *
     * Key format:
     * package.subpackage.key.subkey
     *
     * @param key Text key.
     * @return Text value.
     */
    public Text getText(String key) {
        List<String> keys = List.of(key.split("\\."));
        return this.textNode.getValueByPath(keys);
    }

    /**
     * Get string by key.
     *
     * @param key Text key.
     * @return Text value.
     *
     * @see #getText(String)
     */
    public String getString(String key) {
        return getText(key).toString();
    }

    /**
     * Get instance of subpackage by key
     *
     * @param key Subpackage key.
     * @return Subpackage instance.
     * @throws IllegalArgumentException If subpackage not found.
     */
    public TextPackage getPackage(String key) {
        List<String> keys = List.of(key.split("\\."));
        TextNode node = this.textNode.getNodeByPath(keys);
        return new TextPackage(node);
    }
}
