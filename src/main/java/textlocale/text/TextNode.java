package textlocale.text;

import java.util.Map;

import textlocale.utils.Node;

/**
 * TextNode represents tree structure of texts in packages.
 *
 * Each node contains key, text or children nodes.
 */
public class TextNode extends Node<Text, TextNode> {
    /**
     * Fucking uninherited constructor.
     *
     * @param key   key
     * @param value value
     */
    public TextNode(String key, Text value) {
        super(key, value);
    }

    /**
     * Fucking uninherited constructor.
     *
     * @param key      key
     * @param children children nodes
     */
    public TextNode(String key, Map<String, TextNode> children) {
        super(key, children);
    }
}
