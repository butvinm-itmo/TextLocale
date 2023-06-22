package textlocale.text;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

/**
 * Provides methods for parsing nested maps that represents packages with
 * {@link Text} data to {@link TextNode}.
 *
 * Valid map structure:
 *
 * <pre>
 * "root": {
 *      "package1": {
 *          "subpackage1": {
 *              "text1": {
 *                   "en": "text1",
 *                      "cs": "text1"
 *             },
 *             "text2": {
 *                  "en": "text2",
 *                 "cs": "text2"
 *            }
 *          },
 *       },
 *       ...
 * }
 * </pre>
 *
 * Will be parsed to:
 *
 * <pre>
 * Map<String, Object> "root": {
 *  "package1": Map<String, Object> {
 *      "subpackage1": Map<String, Object> {
 *          "text1": Text[key="text1", locale="en", values={en=text1, cs=text1}],
 *          "text2": Text[key="text2", locale="en", values={en=text2, cs=text2}]
 *     }
 *  }
 * }
 * </pre>
 */
@RequiredArgsConstructor
public class TextNodeBuilder {
    /**
     * Supplier of current locale for builded {@link Text}.
     */
    private final Supplier<String> locale;

    /**
     * Build {@link TextNode} for package from map of localized texts.
     * Map should have one root key that is name of package.
     *
     * @param map Map of localized texts.
     * @return Node for package.
     * @throws IllegalArgumentException If map has invalid structure.
     */
    public TextNode build(Map<String, Object> map) {
        if (map.size() != 1) {
            throw new IllegalArgumentException("Map should have one root key");
        }
        Entry<String, Object> rootEntry = map.entrySet().iterator().next();

        TextNode rootNode = new TextNode(rootEntry.getKey(), new HashMap<>());
        for (Entry<String, Object> entry : ((Map<String, Object>) rootEntry.getValue()).entrySet()) {
            rootNode.addChild(buildNode(entry));
        }
        return rootNode;
    }

    private TextNode buildNode(Entry<String, Object> entry) {
        String key = entry.getKey();
        Map<String, Object> children = (Map<String, Object>) entry.getValue();
        if (isTerminalNode(children)) {
            Text text = new Text(key, locale, (Map<String, String>) entry.getValue());
            return new TextNode(key, text);
        } else {
            TextNode node = new TextNode(key, new HashMap<>());
            for (Entry<String, Object> childEntry : (children).entrySet()) {
                node.addChild(buildNode(childEntry));
            }
            return node;
        }
    }

    private Boolean isTerminalNode(Map<String, Object> map) {
        return map.entrySet().stream().allMatch(entry -> entry.getValue() instanceof String);
    }
}
