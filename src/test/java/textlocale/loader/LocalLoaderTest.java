package textlocale.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import textlocale.loader.common.LocalLoader;
import textlocale.loader.exceptions.LoadingFailed;

public class LocalLoaderTest {

    @Test
    public void testLoadPackage() throws LoadingFailed {
        ClassLoader classLoader = getClass().getClassLoader();
        String packagePath = classLoader.getResource("").getPath();
        LocalLoader localLoader = new LocalLoader(new File(packagePath), ".tl.json");

        FilesNode filesNode = localLoader.loadPackage("textlocale");

        // Assert the root node
        assertEquals("textlocale", filesNode.getKey());
        assertTrue(!filesNode.isTerminal());

        // Assert the root
        Map<String, FilesNode> children = filesNode.getChildren();
        assertTrue(children.containsKey("subdir"));
        assertTrue(children.containsKey("subdir3"));
        assertTrue(children.containsKey("text1"));

        // Assert text1
        FilesNode text1 = children.get("text1");
        assertEquals("text1", text1.getKey());
        assertTrue(text1.isTerminal());
        String text1Content = text1.getValue();
        assertTrue(text1Content.contains("\"en\": \"Hello, world!\""));
        assertTrue(text1Content.contains("\"es\": \"?Hola, mundo!\""));

        // Assert subdir
        FilesNode subdir = children.get("subdir");
        assertEquals("subdir", subdir.getKey());
        assertTrue(!subdir.isTerminal());

        Map<String, FilesNode> subdirChildren = subdir.getChildren();
        assertTrue(subdirChildren.containsKey("subdir2"));
        assertTrue(subdirChildren.containsKey("text2"));

        // Assert subdir2
        FilesNode subdir2 = subdirChildren.get("subdir2");
        assertEquals("subdir2", subdir2.getKey());
        assertTrue(!subdir2.isTerminal());
    }

    @Test(expected = LoadingFailed.class)
    public void testInvalidPackagePath() throws LoadingFailed {
        ClassLoader classLoader = getClass().getClassLoader();
        String packagePath = classLoader.getResource("").getPath();
        LocalLoader localLoader = new LocalLoader(new File(packagePath), ".tl.json");
        String invalidPackagePath = "nonexistent/package";
        FilesNode filesNode = localLoader.loadPackage(invalidPackagePath);
    }
}
