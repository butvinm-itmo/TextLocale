package textlocale.parser;

import org.junit.Test;
import textlocale.loader.FilesNode;
import textlocale.parser.common.JsonParser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JsonParserTest {

    @Test
    public void testParseJsonFile() {
        // Create a FilesNode representing a JSON file
        FilesNode node = new FilesNode("file.json", "{\"key\": \"value\"}");

        // Create an instance of JsonParser
        JsonParser jsonParser = new JsonParser();

        // Call the parse method
        Map<String, Object> result = jsonParser.parse(node);

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("file.json"));

        Object value = result.get("file.json");
        assertTrue(value instanceof Map);

        Map<String, Object> jsonMap = (Map<String, Object>) value;
        assertEquals("value", jsonMap.get("key"));
    }

    @Test
    public void testParseDirectory() {
        // Create a FilesNode representing a directory with nested files
        FilesNode child1 = new FilesNode("file1.json", "{\"key1\": {\"en\": \"value\"}}");
        FilesNode child2 = new FilesNode("file2.json", "{\"key2\": {\"en\": \"value\"}}");
        FilesNode child3 = new FilesNode("file3.json", "{\"key3\": {\"en\": \"value\"}}");

        Map<String, FilesNode> children = new HashMap<>();
        FilesNode parentNode = new FilesNode("directory", children);
        children.put(child1.getKey(), child1);
        children.put(child2.getKey(), child2);
        children.put(child3.getKey(), child3);

        // Create an instance of JsonParser
        JsonParser jsonParser = new JsonParser();

        // Call the parse method
        Map<String, Object> result = jsonParser.parse(parentNode);

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.containsKey("directory"));

        Object value = result.get("directory");
        assertTrue(value instanceof Map);

        Map<String, Object> jsonMap = (Map<String, Object>) value;
        assertEquals(3, jsonMap.size());
        assertTrue(jsonMap.containsKey("file1.json"));
        assertTrue(jsonMap.containsKey("file2.json"));
        assertTrue(jsonMap.containsKey("file3.json"));
    }
}
