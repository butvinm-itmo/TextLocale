package textlocale.loader.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

import lombok.RequiredArgsConstructor;
import textlocale.loader.FilesLoader;
import textlocale.loader.FilesNode;
import textlocale.loader.exceptions.LoadingFailed;

/**
 * Implementation of {@link FilesLoader} which loads files from project
 * resources.
 */
@RequiredArgsConstructor
public class ResourcesLoader extends FilesLoader {
    /**
     * Project source url.
     */
    private final URL codeSourceUrl;

    /**
     * Extension of files to load.
     */
    private final String searchExtension;

    /**
     * Load files from resources of given package.
     *
     * @param packagePath package name
     * @return {@link FilesNode} with loaded files
     */
    @Override
    public FilesNode loadPackage(String packagePath) throws LoadingFailed {
        try {
            FilesLoader loader;
            if (codeSourceUrl.getPath().endsWith(".jar")) {
                JarFile jarFile = new JarFile(codeSourceUrl.getPath());
                loader = new JarLoader(jarFile, searchExtension);
            } else {
                File file = new File(codeSourceUrl.getPath());
                loader = new LocalLoader(file, searchExtension);
            }
            return loader.loadPackage(packagePath);
        } catch (IOException e) {
            throw new LoadingFailed("Failed to load package", e);
        }
    }
}
