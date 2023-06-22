package textlocale.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;

/**
 * TNode represents tree structure.
 *
 * Each node contains key, value or children nodes.
 *
 * @param <K> type of value
 * @param <T> same type as current node
 */
public class Node<K, T extends Node<K, T>> {
    /**
     * Key of node (text or package name).
     */
    @Getter
    private final String key;

    /**
     * Text if node is terminal node.
     */
    private final Optional<K> value;

    /**
     * Children nodes if node is package.
     */
    private final Optional<Map<String, T>> children;

    /**
     * Create new node with given key and value
     *
     * @param key   key
     * @param value value
     */
    public Node(String key, K value) {
        this.key = key;
        this.value = Optional.of(value);
        this.children = Optional.empty();
    }

    /**
     * Create new node with given key and children nodes.
     *
     * @param key      key
     * @param children children nodes
     */
    public Node(String key, Map<String, T> children) {
        this.key = key;
        this.value = Optional.empty();
        this.children = Optional.of(children);
    }

    /**
     * Return true if node is terminal node,
     * otherwise false.
     *
     * @return true if node is terminal node, otherwise false
     */
    public boolean isTerminal() {
        return value.isPresent();
    }

    /**
     * Return value if node is terminal node,
     * otherwise throw exception.
     *
     * @return value
     * @throws IllegalStateException if node is not terminal node
     */
    public K getValue() {
        if (value.isPresent()) {
            return value.get();
        } else {
            throw new IllegalStateException("Node is not terminal node");
        }
    }

    /**
     * Return value by given path.
     *
     * @param path path. Path is list of keys.
     * @return text
     * @throws IllegalArgumentException if value
     */
    public K getValueByPath(List<String> path) {
        T subNode = getChildByPath(path);
        if (subNode.isTerminal()) {
            return subNode.getValue();
        } else {
            throw new IllegalArgumentException("Path is not valid: %s".formatted(path));
        }
    }

    /**
     * Return children node by given path.
     *
     * @param path path. Path is list of keys.
     * @return children node
     * @throws IllegalArgumentException if path is not valid
     */
    public T getNodeByPath(List<String> path) {
        T subNode = getChildByPath(path);
        if (!subNode.isTerminal()) {
            return subNode;
        } else {
            throw new IllegalArgumentException("Path is not valid: %s".formatted(path));
        }
    }

    /**
     * Return children nodes if node is package,
     * otherwise throw exception.
     *
     * @return children nodes
     * @throws IllegalStateException if node is not package
     */
    public Map<String, T> getChildren() {
        if (children.isPresent()) {
            return children.get();
        } else {
            throw new IllegalStateException("Node is not package");
        }
    }

    /**
     * Return children node by given path.
     *
     * @param path path. Path is list of keys.
     * @return children node
     * @throws IllegalArgumentException if path is not valid
     */
    protected T getChildByPath(List<String> path) {
        // Copy path to modify it
        path = new ArrayList<>(path);
        if (path.isEmpty()) {
            return (T) this;
        } else {
            String key = path.remove(0);
            Map<String, T> children = getChildren();
            T child = children.get(key);
            if (child != null) {
                return child.getChildByPath(path);
            } else {
                throw new IllegalArgumentException("Path is not valid: %s".formatted(path));
            }
        }
    }

    /**
     * Add child node to current node.
     *
     * @param child child node.
     */
    public void addChild(T child) {
        if (children.isPresent()) {
            children.get().put(child.getKey(), child);
        } else {
            throw new IllegalStateException("Node is not package");
        }
    }

    /**
     * Remove child node from current node.
     *
     * @param child child node
     */
    public void removeChild(T child) {
        if (children.isPresent()) {
            children.get().remove(child.getKey());
        } else {
            throw new IllegalStateException("Node is not package");
        }
    }

    /**
     * Return string representation of node.
     *
     * If node is terminal node, return key and value.
     * If node is package, return key and children nodes.
     *
     * @return string representation of node
     */
    @Override
    public String toString() {
        if (isTerminal()) {
            return key + " " + value.get();
        } else {
            return key + " " + children.get();
        }
    }
}