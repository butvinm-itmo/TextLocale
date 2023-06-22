package textlocale;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

import textlocale.loader.FilesLoader;
import textlocale.loader.FilesNode;
import textlocale.loader.exceptions.LoadingFailed;
import textlocale.parser.Parser;
import textlocale.parser.common.JsonParser;
import textlocale.text.TextNode;
import textlocale.text.TextNodeBuilder;
import textlocale.text.TextPackage;

/**
 * Provides methods for working with text localization.
 *
 * Localization is stored in tl.json files.
 * Format of tl.json files:
 *
 * <pre>
 * {
 *      "package1": {
 *          "subpackage1": {
 *              "text1": {
 *                   "en": "text1",
 *                      "cs": "text1"
 *             },
 *             "text2": {
 *                  "en": "text2",
 *                 "cs": "text2"
 *            }
 *          },
 *       },
 *       ...
 * }
 * </pre>
 */
public class TextLocale {
    /**
     * Loads texts from specified package.
     *
     * @param packagePath    path to package
     * @param localeSupplier supplier of current locale
     * @param loader         loader for loading files
     * @return text node for package
     * @throws IOException              if an I/O error occurs
     * @throws IllegalArgumentException if texts are invalid
     * @see TextNode
     */
    public static TextPackage loadPackage(String packagePath, Supplier<String> localeSupplier, FilesLoader loader)
            throws LoadingFailed {
        FilesNode filesNode = loader.loadPackage(packagePath);
        Parser parser = new JsonParser();
        Map<String, Object> texts = parser.parse(filesNode);
        TextNodeBuilder builder = new TextNodeBuilder(localeSupplier);
        TextNode textsNode = builder.build(texts);
        return new TextPackage(textsNode);
    }
}
