package textlocale.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

public class TextTest {
    @Test
    public void testToString() {
        // Create a sample map of localized strings
        Map<String, String> localizedStrings = new HashMap<>();
        localizedStrings.put("en", "Hello");
        localizedStrings.put("es", "Hola");

        // Create a mock Supplier for the locale
        Supplier<String> localeSupplier = () -> "en";

        // Create an instance of Text
        Text text = new Text("greeting", localeSupplier, localizedStrings);

        // Call the toString() method
        String result = text.toString();

        // Assert the result
        assertNotNull(result);
        assertEquals("Hello", result);
    }

    @Test
    public void testChangingLocale() {
        // Create a sample map of localized strings
        Map<String, String> localizedStrings = new HashMap<>();
        localizedStrings.put("en", "Hello");
        localizedStrings.put("es", "Hola");

        // Create a Supplier that tracks the changing locale
        class LocaleSupplier implements Supplier<String> {
            private String locale;

            @Override
            public String get() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }
        }
        LocaleSupplier localeSupplier = new LocaleSupplier();
        localeSupplier.setLocale("en");

        // Create an instance of Text
        Text text = new Text("greeting", localeSupplier, localizedStrings);

        // Initial locale is English
        String result = text.toString();
        assertEquals("Hello", result);

        // Switch to Spanish locale
        localeSupplier.setLocale("es");
        result = text.toString();
        assertEquals("Hola", result);
    }
}
