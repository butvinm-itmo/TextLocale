package textlocale.loader;

import lombok.RequiredArgsConstructor;
import textlocale.loader.exceptions.LoadingFailed;

/**
 * Provides methods for loading files from given package.
 *
 * Return value is {@link FilesNode} which represents tree structure of files.
 */
@RequiredArgsConstructor
public abstract class FilesLoader {
    /**
     * Load files from given package.
     *
     * @param packagePath package name
     * @return {@link FilesNode} with loaded files
     */
    public abstract FilesNode loadPackage(String packagePath) throws LoadingFailed;
}
