package textlocale;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import textlocale.loader.FilesLoader;
import textlocale.loader.common.LocalLoader;
import textlocale.text.TextPackage;

public class TextLocaleTest {
    static String locale = "en";

    static TextPackage rootPackage;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String resourcesPath = classLoader.getResource(".").getPath();
        FilesLoader loader = new LocalLoader(new File(resourcesPath), ".tl.json");
        rootPackage = TextLocale.loadPackage("textlocale", () -> TextLocaleTest.locale, loader);
    }

    @Test
    public void testLoadPackage() throws IOException {
        locale = "en";
        String text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("Hello, world!", text.toString());
        locale = "es";
        text = rootPackage.getText("subdir.subdir2.text4.greet");
        assertEquals("?Hola, mundo!", text.toString());

        text = rootPackage.getText("text1.Hi");
        assertEquals("?Hola, mundo!", text.toString());
    }

    @Test
    public void testSubPackaging() {
        locale = "en";
        TextPackage subPackage = rootPackage.getPackage("subdir.subdir2");

        String text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text.toString());

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text.toString());

        text = subPackage.getText("text4.greet");
        assertEquals("Hello, world!", text.toString());

        locale = "es";

        text = subPackage.getText("text4.greet");
        assertEquals("?Hola, mundo!", text.toString());
    }
}