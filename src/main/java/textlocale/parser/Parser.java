package textlocale.parser;

import java.util.Map;

import textlocale.loader.FilesNode;
import textlocale.text.TextNode;

/**
 * Provides methods for parsing {@link FilesNode} to {@code Map<String, Object}
 * (each value is same map or string).
 */
public abstract class Parser {
    /**
     * Parse {@link FilesNode} to {@code Map<String, Object}
     * of packages and texts string.
     *
     * @param node {@link FilesNode} to parse
     * @return {@link TextNode} with parsed data
     */
    public abstract Map<String, Object> parse(FilesNode node);
}
