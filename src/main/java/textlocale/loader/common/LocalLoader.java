package textlocale.loader.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import textlocale.loader.FilesLoader;
import textlocale.loader.FilesNode;
import textlocale.loader.exceptions.LoadingFailed;

/**
 * Implementation of {@link FilesLoader} which loads files from local directory.
 */
@RequiredArgsConstructor
public class LocalLoader extends FilesLoader {
    /**
     * Root directory of files.
     */
    private final File rootDirectory;

    /**
     * Extension of files to load.
     */
    private final String searchExtension;

    /**
     * Load files from given package.
     *
     * @param packagePath package name
     * @return {@link FilesNode} with loaded files
     */
    @Override
    public FilesNode loadPackage(String packagePath) throws LoadingFailed {
        File packageDir = new File(rootDirectory, packagePath);

        if (!packageDir.exists() || !packageDir.isDirectory()) {
            throw new LoadingFailed("Invalid package name or package does not exist");
        }

        FilesNode rootNode = new FilesNode(packagePath, new HashMap<>());
        try {
            processDirectory(packageDir, rootNode);
        } catch (IOException e) {
            throw new LoadingFailed("Failed to load package", e);
        }
        return rootNode;
    }

    /**
     * Process directory and add files to given map.
     *
     * @param directory directory to process
     * @param children  map to add files to
     * @throws IOException
     */
    private void processDirectory(File directory, FilesNode parentNode) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(searchExtension)) {
                    String fileName = file.getName();
                    fileName = fileName.substring(0, fileName.length() - searchExtension.length());
                    String fileContent = Files.readString(file.toPath());
                    FilesNode fileNode = new FilesNode(fileName, fileContent);
                    parentNode.addChild(fileNode);
                } else if (file.isDirectory()) {
                    String subPackageName = file.getName();
                    FilesNode packageNode = new FilesNode(subPackageName, new HashMap<>());
                    parentNode.addChild(packageNode);
                    processDirectory(file, packageNode);
                }
            }
        }
    }
}
