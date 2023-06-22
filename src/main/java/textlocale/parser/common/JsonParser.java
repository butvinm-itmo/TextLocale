package textlocale.parser.common;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import textlocale.loader.FilesNode;
import textlocale.parser.Parser;

public class JsonParser extends Parser {
    /**
     * Gson instance.
     */
    private static final Gson gson = new Gson();

    @Override
    public Map<String, Object> parse(FilesNode node) {
        Map<String, Object> jsonMap = new HashMap<>();
        addNodeToMap(node, jsonMap);
        return jsonMap;
    }

    /**
     * Convert {@link FilesNode} to nested map of strings.
     *
     * @param node {@link FilesNode} to convert
     * @return map with json data
     * @throws JsonSyntaxException
     *
     * @see FilesNode
     * @see textlocale.text.TextNodeBuilder
     */
    private void addNodeToMap(FilesNode node, Map<String, Object> targetMap) throws JsonSyntaxException {
        if (node.isTerminal()) {
            Type mapType = new TypeToken<HashMap<String, Object>>() {
            }.getType();
            Map<String, Object> textJson = gson.fromJson(node.getValue(), mapType);
            if (textJson == null) {
                throw new JsonSyntaxException("Json file is empty");
            }
            targetMap.put(node.getKey(), textJson);
        } else {
            Map<String, Object> packageJson = new HashMap<>();
            targetMap.put(node.getKey(), packageJson);
            for (FilesNode child : node.getChildren().values()) {
                addNodeToMap(child, packageJson);
            }
        }
    }
}
