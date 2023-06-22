package textlocale.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.Test;

public class TextNodeBuilderTest {

    @Test
    public void testBuildTextNode() {
        // Create a sample map representing the text structure
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> subMap = new HashMap<>();
        Map<String, String> text1Values = new HashMap<>();
        text1Values.put("en", "text1");
        text1Values.put("cs", "text1");
        Map<String, String> text2Values = new HashMap<>();
        text2Values.put("en", "text2");
        text2Values.put("cs", "text2");
        subMap.put("text1", text1Values);
        subMap.put("text2", text2Values);
        map.put("root_package", subMap);

        // Create a mock Supplier for the locale
        Supplier<String> localeSupplier = () -> "en";

        // Create an instance of TextsNodeBuilder
        TextNodeBuilder builder = new TextNodeBuilder(localeSupplier);

        // Call the build method
        TextNode result = builder.build(map);

        // Assert the result
        assertNotNull(result);
        assertEquals("root_package", result.getKey());

        Map<String, TextNode> packageChildren = result.getChildren();
        assertNotNull(packageChildren);
        assertEquals(2, packageChildren.size());
        assertTrue(packageChildren.containsKey("text1"));
        assertTrue(packageChildren.containsKey("text2"));

        TextNode text1Node = packageChildren.get("text1");
        assertNotNull(text1Node);
        assertEquals("text1", text1Node.getKey());

        Text text1 = text1Node.getValue();
        assertNotNull(text1);
        assertEquals("text1", text1.getKey());
        assertEquals("en", text1.getLocale().get());
        Map<String, String> text1ValuesResult = text1.getLocalizedStrings();
        assertNotNull(text1ValuesResult);
        assertEquals(2, text1ValuesResult.size());
        assertEquals("text1", text1ValuesResult.get("en"));
        assertEquals("text1", text1ValuesResult.get("cs"));

        TextNode text2Node = packageChildren.get("text2");
        assertNotNull(text2Node);
        assertEquals("text2", text2Node.getKey());

        Text text2 = text2Node.getValue();
        assertNotNull(text2);
        assertEquals("text2", text2.getKey());
        assertEquals("en", text2.getLocale().get());
        Map<String, String> text2ValuesResult = text2.getLocalizedStrings();
        assertNotNull(text2ValuesResult);
        assertEquals(2, text2ValuesResult.size());
        assertEquals("text2", text2ValuesResult.get("en"));
        assertEquals("text2", text2ValuesResult.get("cs"));
    }
}
