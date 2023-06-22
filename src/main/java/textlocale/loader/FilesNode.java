package textlocale.loader;

import java.util.Map;

import textlocale.utils.Node;

/**
 * FilesNode represents tree structure of packages.
 *
 * Each node contains key, file content or children nodes.
 */
public class FilesNode extends Node<String, FilesNode> {
    /**
     * Fucking uninherited constructor.
     *
     * @param key   key
     * @param value value
     */
    public FilesNode(String key, String value) {
        super(key, value);
    }

    /**
     * Fucking uninherited constructor.
     *
     * @param key      key
     * @param children children nodes
     */
    public FilesNode(String key, Map<String, FilesNode> children) {
        super(key, children);
    }
}
