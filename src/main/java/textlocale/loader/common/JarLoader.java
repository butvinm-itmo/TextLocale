package textlocale.loader.common;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import lombok.RequiredArgsConstructor;
import textlocale.loader.FilesLoader;
import textlocale.loader.FilesNode;
import textlocale.loader.exceptions.LoadingFailed;

/**
 * Implementation of {@link FilesLoader} which loads files from JAR archives.
 */
@RequiredArgsConstructor
public class JarLoader extends FilesLoader {
    /**
     * Root directory of files.
     */
    private final JarFile rootDirectory;

    /**
     * Extension of files to load.
     */
    private final String searchExtension;

    /**
     * Load files from given jar package.
     *
     * @param packagePath package name
     * @return {@link FilesNode} with loaded files
     */
    @Override
    public FilesNode loadPackage(String packagePath) throws LoadingFailed {
        try {
            String[] pathParts = packagePath.split("/");
            String rootNodeName = pathParts[pathParts.length - 1];
            FilesNode rootNode = new FilesNode(rootNodeName, new HashMap<>());
            Enumeration<JarEntry> entries = rootDirectory.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().startsWith(packagePath) && entry.getName().endsWith(searchExtension)) {
                    String entryPath = entry.getName().substring(packagePath.length() + 1);
                    entryPath = entryPath.substring(0, entryPath.length() - searchExtension.length());
                    processFile(entryPath, rootDirectory, entry, rootNode);
                }
            }
            return rootNode;
        } catch (IOException e) {
            throw new LoadingFailed("Failed to load package", e);
        }
    }

    /**
     * Add file to given node.
     *
     * For each subdirectory, create a new node (if not exist) and recursively
     * process it.
     *
     * @param filePath  path of file to add
     * @param fileEntry file entry
     * @param rootNode  root node to add file to
     */
    private void processFile(String filePath, JarFile jarFile, JarEntry fileEntry, FilesNode rootNode)
            throws IOException {
        String[] pathParts = filePath.split("/");
        if (pathParts.length == 1) {
            rootNode.addChild(new FilesNode(pathParts[0], readFileContent(jarFile, fileEntry)));
        } else {
            String subNodeName = pathParts[0];
            if (!rootNode.getChildren().containsKey(subNodeName)) {
                rootNode.addChild(new FilesNode(subNodeName, new HashMap<>()));
            }
            FilesNode subNode = rootNode.getChildren().get(subNodeName);
            processFile(filePath.substring(subNodeName.length() + 1), jarFile, fileEntry, subNode);
        }
    }

    private String readFileContent(JarFile jarFile, JarEntry entry) throws IOException {
        byte[] contentBytes = new byte[(int) entry.getSize()];
        jarFile.getInputStream(entry).read(contentBytes);
        return new String(contentBytes);
    }
}