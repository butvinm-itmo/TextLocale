package textlocale.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.jar.JarFile;

import org.junit.Test;

import lombok.Cleanup;
import textlocale.loader.common.JarLoader;
import textlocale.loader.exceptions.LoadingFailed;

public class JarLoaderTest {

    @Test
    public void testLoadPackage() throws LoadingFailed, URISyntaxException, IOException {
        // Get the path to the test JAR file
        String jarFilePath = getTestJarFilePath();
        @Cleanup
        JarFile jarFile = new JarFile(jarFilePath);

        JarLoader jarLoader = new JarLoader(jarFile, ".tl.json");
        FilesNode filesNode = jarLoader.loadPackage("resources/textlocale");

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

    private String getTestJarFilePath() throws URISyntaxException {
        File jarFile = new File(getClass().getResource("/test.jar").toURI());
        return jarFile.getAbsolutePath();
    }
}
